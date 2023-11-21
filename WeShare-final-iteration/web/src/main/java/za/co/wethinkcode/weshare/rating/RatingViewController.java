package za.co.wethinkcode.weshare.rating;

import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;

import java.util.Map;

public class RatingViewController {
    public static final String PATH = "/ratings";


    public static void renderRatingPage(Context context) {
        String ratingType = context.queryParam("by");
//        Map<String, Object> viewModel = Map.of();
        ImmutableList<Person> getRatings = getRatings(ratingType);
        Map<String, Object> viewModel =
                Map.of("ratings", getRatings);

        context.sessionAttribute("by", ratingType);
        context.render("rating.html", viewModel);


    }

    public static ImmutableList<Person> getRatings(String ratingType) {
        ImmutableList<Person> list = Unirest.get(WeShareWebServer.RATINGS_SERVER + "/ratings?by=" + ratingType)
                .asObject(new GenericType<ImmutableList<Person>>() {
                })
                .getBody();
        return list;
    }

    public static void sendExpense(Expense expense) {
        Unirest.post(WeShareWebServer.RATINGS_SERVER + "/ratings/expenses")
                .body(expense);
    }

    public static void sendSettlement(Settlement settlement) {
        Unirest.post(WeShareWebServer.RATINGS_SERVER + "/ratings/settlement")
                .body(settlement);
    }

    public static void sendClaim(Claim claim) {
        Unirest.post(WeShareWebServer.RATINGS_SERVER + "/ratings/claim")
                .body(claim);
    }


}
