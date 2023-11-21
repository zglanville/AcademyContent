package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;
import java.util.*;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {

    public static String ExpenseUuid;

    public static String PATH= "/claimexpense";

    public static void renderClaimExpensePage(Context context){
        DataRepository database = DataRepository.getInstance();

        String expenseUuid = context.queryString();
        assert expenseUuid != null;
        expenseUuid = expenseUuid.replaceAll("expenseId=","");
        ExpenseUuid = expenseUuid;
        Optional<Expense> originalOptionalExpense = database.getExpense(UUID.fromString(expenseUuid));
        Expense originalExpense = originalOptionalExpense.get();

        double expenseAmount = originalExpense.getAmount();
        LocalDate date = originalExpense.getDate();
        String description = originalExpense.getDescription();
        Set<Claim> claims = originalExpense.getClaims();
        double totalClaims = originalExpense.getTotalClaims();
        double unclaimedAmount = originalExpense.getUnclaimedAmount();
        boolean hasClaims = !claims.isEmpty();
        PATH = PATH+context.queryString();

        Map<String, Object> viewModel = Map.of(
                "hasClaims", hasClaims,
                "expenseUuid", expenseUuid,
                "totalClaims", totalClaims,
                "claims", claims,
                "unclaimedAmount",unclaimedAmount,
                "originalExpense", originalExpense,
                "expenseAmount", expenseAmount,
                "date", date,
                "description", description
        );
        context.render("claimexpense.html", viewModel);
    }
}