package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    public static String PATH = ClaimExpenseController.PATH;

    public static void create(Context context) {
        DataRepository database = DataRepository.getInstance();

        String expenseUuid = ClaimExpenseController.ExpenseUuid;
        Optional<Expense> originalOptionalExpense = database.getExpense(UUID.fromString(expenseUuid));
        Expense originalExpense = originalOptionalExpense.get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate dueDate = LocalDate.parse(context.formParam("due_date"), formatter);
        Double amount = Double.parseDouble(context.formParam("claim_amount"));
        Person claimedFrom;
        Optional<Person> claimedFromOptional = database.findPerson(context.formParam("email"));
        if (claimedFromOptional.isEmpty()){
            claimedFrom = database.addPerson(new Person(context.formParam("email")));
        }else{
            claimedFrom = claimedFromOptional.get();
        }
        Claim claim = originalExpense.createClaim(claimedFrom,amount,dueDate);

        String amountString = originalExpense.getFormattedAmount();
        LocalDate date = originalExpense.getDate();
        String description = originalExpense.getDescription();
        Set<Claim> claims = originalExpense.getClaims();
        double totalClaims = originalExpense.getTotalClaims();
        double unclaimedAmount = originalExpense.getUnclaimedAmount();
        boolean hasClaims = !claims.isEmpty();

        Map<String, Object> viewModel = Map.of(
                "hasClaims", hasClaims,
                "expenseUuid", expenseUuid,
                "totalClaims", totalClaims,
                "claims", claims,
                "unclaimedAmount",unclaimedAmount,
                "originalExpense", originalExpense,
                "amountString", amountString,
                "date", date,
                "description", description
        );

        DataRepository.getInstance().addClaim(claim);
        context.render("claimexpense.html",viewModel);
    }
}