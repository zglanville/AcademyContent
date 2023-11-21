package za.co.wethinkcode.weshare.settle;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {

    public static final String PATH = "/settleclaim";

    public static void renderSettleClaimForm(Context context){


        UUID id = UUID.fromString(context.queryParam("claimId"));

        String email = DataRepository.getInstance().getClaim(id).get().getClaimedBy().getEmail();
        LocalDate date = DataRepository.getInstance().getClaim(id).get().getDueDate();
        String description = DataRepository.getInstance().getClaim(id).get().getDescription();
        double claim_amount = DataRepository.getInstance().getClaim(id).get().getAmount();

        context.sessionAttribute("id", id);
        Map<String, Object> viewModel = Map.of(
                "email", email,
                "due_date",date ,
                "description", description,
                "claim_amount", claim_amount
        );

        context.render("settleclaim.html", viewModel);

    }

    public static void submitSettlement(Context context) {
        UUID id = UUID.fromString(context.sessionAttributeMap().get("id").toString());
//
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate date = LocalDate.now();
        date.format(formatter);

        Claim claim = DataRepository.getInstance().getClaim(id).get();
        Person user = (Person)context.sessionAttributeMap().get("user");


        DataRepository.getInstance().addExpense(new Expense(
                claim.getAmount(), date, claim.getDescription(), user));
        claim.settleClaim(date);
        DataRepository.getInstance().removeClaim(claim);

        context.sessionAttributeMap().remove("id");
        context.redirect(NettExpensesController.PATH);
    }

}