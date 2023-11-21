package za.co.wethinkcode.weshare.expense.model;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.util.*;

/**
 * I record the details of an Expense, created when a person records an actual expense paid by them.
 * The person can then choose to (as a next step) lodge one or more claims from this expense against others.
 * <p>
 * I use Person instances to record who incurred the Expense.
 * <p>
 * Note that my instances are immutable.
 */

public class Expense extends AbstractTransactionModelBase {
    // the date the expense occurred
    private final LocalDate date;
    // a description of the expense
    private final String description;
    // the Person that paid the expense (the capturer of the expense)
    private final Person paidBy;
    // the sum of all for claims against the expense, regardless of whether the claims have been settled or not
    private double claimsMade;
    // the sum of settled claims against the expense
    private double claimsSettled;


    /**
     * Initialise an Expense instance with all it's needed data.
     *
     * @param amount      R value of the expense item
     * @param date        Date on which the expense was paid
     * @param description Some descriptive text, possibly null or empty.
     * @param paidBy      The Person who paid for the expense item.
     */
    public Expense(double amount, LocalDate date, String description, Person paidBy) {
        super(UUID.randomUUID(), amount);
        this.date = date;
        this.description = Strings.isNullOrEmpty(description) ? "Unspecified" : description;
        this.paidBy = paidBy;
        this.claimsMade = 0;
        this.claimsSettled = 0;

    }

    public void addClaim(double claimAmount) {
        if (this.claimsMade + claimAmount > this.getAmount()) {
            throw new RuntimeException("Total claims exceeds the amount of the expense");
        }

        this.claimsMade = this.claimsMade + claimAmount;
    }

    public void settleClaim(double amountToSettle) {
        if (this.claimsMade - this.claimsSettled < amountToSettle) {
            throw new RuntimeException("Amount to settle exceeds the claims made against the expense");
        }

        this.claimsSettled = this.claimsSettled + amountToSettle;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (!date.equals(expense.date)) return false;
        if (!description.equals(expense.description)) return false;
        return paidBy.equals(expense.paidBy);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + paidBy.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", paidBy=" + paidBy +
                ", id=" + id +
                ", amount=" + amount +
                '}';
    }

    public String getFormattedAmount() {
        return String.format("R %,.2f", this.amount);
    }


    public Double getUnclaimedAmount() {
        return this.amount - this.claimsMade;
    }

    public Double getNettAmount() {
        return this.amount - this.claimsSettled;
    }

    public Double getTotalSettledClaims() {
        return this.claimsSettled;
    }

    public Double getTotalUnsettledClaims() {
        return this.claimsMade - this.claimsSettled;
    }

    public Double getTotalClaimsMade() {
        return this.claimsMade;
    }

    public Double getNettAmountLessSettledClaims() {
        return getNettAmount() - this.claimsSettled;
    }
}