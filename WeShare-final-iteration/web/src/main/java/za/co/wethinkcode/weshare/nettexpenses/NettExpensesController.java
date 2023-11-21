package za.co.wethinkcode.weshare.nettexpenses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.*;
import za.co.wethinkcode.weshare.claim.ClaimsApiController;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;

import javax.annotation.concurrent.Immutable;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class NettExpensesController {
    public static final String PATH = "/home";


    public static void renderHomePage(Context context) throws JsonProcessingException, NoSuchFieldException {
        Person currentPerson = context.sessionAttribute("user");
        Map<String, Double> values = CaptureExpenseController.getNettAmounts(currentPerson);
        double totalExpense ;
        double nettExpenses;
        if (values == null){
            totalExpense = 0;
            nettExpenses = 0;
        }else {
            totalExpense = values.get("sumOfExpenses");
            nettExpenses = values.get("sumOfNettExpenses");
        }

        List<Expense> expenses = CaptureExpenseController.getExpenses(currentPerson);
        List<Claim>  owedToOthers  = ClaimsApiController.getClaimsFrom(currentPerson, true);
        double totalIOwe = ClaimsApiController.getTotalUnsettledClaimsClaimedFrom(currentPerson);
        List<Claim> owedToMe= ClaimsApiController.getClaimsBy(currentPerson, true);
        double totalOwedToMe =  ClaimsApiController.getTotalUnsettledClaimsClaimedBy(currentPerson);


        Map<String, Object> viewModel =
                Map.of(
                        "expenses", expenses,
                        "totalExpenses", totalExpense,
                       "owedToOthers", owedToOthers,
                       "totalIOwe", totalIOwe,
                       "owedToMe", owedToMe,
                       "totalOwedToMe", totalOwedToMe,
                       "nettExpenses", nettExpenses
                );

        context.render("home.html", viewModel);
    }

}