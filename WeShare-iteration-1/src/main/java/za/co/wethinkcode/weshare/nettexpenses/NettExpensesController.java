package za.co.wethinkcode.weshare.nettexpenses;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.List;
import java.util.Map;

public class NettExpensesController {
    public static final String PATH = "/home";

    public static void renderHomePage(Context context){
        DataRepository database = DataRepository.getInstance();

        Person person = context.sessionAttribute("user");
        List<Expense> expenses = database.getExpenses(person);
        List<Claim> creditorExpenses = database.getClaimsFrom(person, true);
        List<Claim> debtorExpenses = database.getClaimsBy(person, true);
        double totalExpenses = database.getTotalExpensesFor(person);
        double totalCreditorsExpense = database.getTotalUnsettledClaimsClaimedFrom(person);
        double totalDebtorExpense = database.getTotalUnsettledClaimsClaimedBy(person);
        double nettExpenses = database.getNettExpensesFor(person);


        Map<String, Object> viewModel = Map.of(
                "expenses", expenses,
                "totalExpenses", totalExpenses,
                "totalCreditorExpense", totalCreditorsExpense,
                "totalDebtorExpense", totalDebtorExpense,
                "creditorExpenses", creditorExpenses,
                "debtorExpenses", debtorExpenses,
                "nettExpenses", nettExpenses
        );

        context.render("home.html", viewModel);
    }
}