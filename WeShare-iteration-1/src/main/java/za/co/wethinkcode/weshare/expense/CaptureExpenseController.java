package za.co.wethinkcode.weshare.expense;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    public static final String PATH = "/newexpense";

    public static void renderExpensePage(Context context) {
        context.render("expenseform.html");
    }

    public static void createExpense(Context context){
        String desc = context.formParam("description");
        Double amount = Double.parseDouble(context.formParam("amount"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate date = LocalDate.parse(context.formParam("date"), formatter);
        Person person = context.sessionAttribute("user");

        Expense expense = new Expense(amount, date, desc, person);
        DataRepository.getInstance().addExpense(expense);

        context.redirect(NettExpensesController.PATH);
    }
}