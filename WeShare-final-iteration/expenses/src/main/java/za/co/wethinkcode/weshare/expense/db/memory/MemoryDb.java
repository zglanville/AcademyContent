package za.co.wethinkcode.weshare.expense.db.memory;

import com.google.common.collect.ImmutableList;
import za.co.wethinkcode.weshare.expense.db.DataRepository;
import za.co.wethinkcode.weshare.expense.model.Expense;
import za.co.wethinkcode.weshare.expense.model.Person;

import java.time.LocalDate;
import java.util.*;

public class MemoryDb implements DataRepository {
    private final Set<Person> people = new HashSet<>();
    private final Set<Expense> expenses = new HashSet<>();

    volatile long lastPersonId = 0L;
    public MemoryDb() {
        setupTestData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Person> allPersons() {
        return ImmutableList.copyOf(people);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findPerson(String email) {
        return people.stream()
                .filter(Person -> Person.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person addPerson(Person person) {
        Optional<Person> alreadyExists = findPerson(person.getEmail());
        if (alreadyExists.isPresent()) {
            return alreadyExists.get();
        }
        person.setId(nextPersonId());
        people.add(person);
        return person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePerson(Person person) {
        people.remove(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePerson(Person updatedPerson) {
        Optional<Person> PersonOpt = people.stream().filter(Person -> Person.getEmail().equalsIgnoreCase(updatedPerson.getEmail())).findFirst();
        if (PersonOpt.isPresent()) {
            people.remove(PersonOpt.get());
            people.add(updatedPerson);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Expense> getExpenses(Person belongsTo) {
        return expenses.stream()
                .filter(expense -> expense.getPaidBy().equals(belongsTo))
                .sorted(Comparator.comparing(Expense::getDate))
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Expense> getExpense(UUID id) {
        return expenses.stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expense addExpense(Expense expense) {
        expenses.add(expense);
        return expense;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateExpense(Expense updatedExpense) {
        Optional<Expense> ExpenseOpt = expenses.stream().filter(expense -> expense.equals(updatedExpense)).findFirst();
        if (ExpenseOpt.isPresent()) {
            expenses.remove(ExpenseOpt.get());
            expenses.add(updatedExpense);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Expense> findExpensesPaidBy(Person person) {
        return expenses.stream()
                .filter(expense -> expense.getPaidBy().getEmail().equalsIgnoreCase(person.getEmail()))
                .sorted(Comparator.comparing(Expense::getDate))
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalExpensesAmountFor(Person person) {
        return findExpensesPaidBy(person).stream().mapToDouble(Expense::getAmount).sum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNettExpensesAmountFor(Person person) {
        return findExpensesPaidBy(person).stream().mapToDouble(Expense::getNettAmount).sum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalUnsettledClaimAmountFor(Person person) {
        return findExpensesPaidBy(person).stream().mapToDouble(Expense::getTotalUnsettledClaims).sum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalSettledClaimAmountFor(Person person) {
        return findExpensesPaidBy(person).stream().mapToDouble(Expense::getTotalSettledClaims).sum();
    }

    @Override
    public void dropExpenses() {
        expenses.clear();
    }

    /**
     * Answer the next available ID for a Person.
     * <p>
     * Incrementing a {@code long} value has to be synchronized because it is not an atomic
     * operation. See the Java Language Specification (§17.7 in Java SE17 Ed.) for details.
     *
     * @return The new ID.
     */
    private long nextPersonId() {
        synchronized (this) {
            return ++lastPersonId;
        }
    }

    private void setupTestData() {
        Person herman = addPerson(new Person("herman@wethinkcode.co.za"));
        Person mike = addPerson(new Person("mike@wethinkcode.co.za"));
        addPerson(new Person("sett@wethinkcode.co.za"));

        /// herman's expenses
        addExpense(new Expense(100.00, LocalDate.of(2021, 10, 12), "Airtime", herman));
        addExpense(new Expense(35.00, LocalDate.of(2021, 10, 15), "Uber", herman));

        Expense braai = addExpense(new Expense(400.00, LocalDate.of(2021, 9, 28), "Braai", herman));

        // herman claims from mike
        braai.addClaim(200.0);

        //mikes expenses
        Expense beers = addExpense(new Expense(420.00, LocalDate.of(2021, 9, 30), "Beers", mike));

        // mike claim from herman
        beers.addClaim(200.00);
    }
}