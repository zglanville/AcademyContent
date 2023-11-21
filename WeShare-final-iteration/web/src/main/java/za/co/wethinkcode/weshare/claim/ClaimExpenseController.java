package za.co.wethinkcode.weshare.claim;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;

import java.time.LocalDate;
import java.util.*;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimexpense";

    public static void renderClaimExpensePage(Context context) throws JsonProcessingException {
        UUID expenseId = UUID.fromString(context.queryParam("expenseId"));

        Expense expense = CaptureExpenseController.getExpenseById(expenseId);
        System.out.println(expense.getPaidBy());
        context.sessionAttribute("expense", expense);
        context.sessionAttribute("expenseId", expenseId);
        context.sessionAttribute("claimsServer", WeShareWebServer.CLAIMS_SERVER);
        context.render("claimexpense.html",
                Map.of("newClaim", new Claim( expense, new Person("email@domain.com"), 0.0, LocalDate.now() ),
                        "expense", expense));
    }
}