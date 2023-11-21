package za.co.wethinkcode.weshare.claim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJackson;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;
import za.co.wethinkcode.weshare.rating.RatingViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    public static final String PATH = "/api/claims";

    public static void create(@NotNull Context context) throws JsonProcessingException {
        ClaimViewModel claimViewModel = context.bodyAsClass(ClaimViewModel.class);
        Expense expense = context.sessionAttribute("expense");

        String claimFromEmail = claimViewModel.getClaimFromWho();

        Person person = DataRepository.getInstance().addPerson(new Person(claimFromEmail));
        Claim claim = expense.createClaim(person, claimViewModel.getClaimAmount(), claimViewModel.dueDateAsLocalDate());

        Unirest.post(WeShareWebServer.CLAIMS_SERVER + "/claim")
                .body(claim);
        RatingViewController.sendClaim(claim);
        CaptureExpenseController.updateExpense(expense);
        RatingViewController.sendExpense(expense);

        expense = CaptureExpenseController.getExpenseById(expense.getId());
        context.sessionAttribute("expense", expense);

        claimViewModel.setId(expense.getClaims().size());
        context.status(201);
        context.json(claimViewModel);
    }


    public static void updateClaim(Claim claim) {
        Unirest.put(WeShareWebServer.CLAIMS_SERVER + "/claim")
                .body(claim);
        RatingViewController.sendClaim(claim);
    }

    public static Claim getClaimById(UUID claimId) throws JsonProcessingException {
        String JSONClaims = Unirest.get(WeShareWebServer.CLAIMS_SERVER + "/claim/" + claimId.toString())
                .asString()
                .getBody();
        System.out.println(JSONClaims);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Javalin.create(javalinConfig -> javalinConfig.jsonMapper(new JavalinJackson(mapper)));
        Claim claims = mapper.readValue(JSONClaims, new TypeReference<Claim>(){});
        return claims;
    }

    public static List<Claim> getClaimsFrom(Person currentPerson, boolean onlyUnsettled) throws JsonProcessingException {
        String JSONClaims = Unirest.get(WeShareWebServer.CLAIMS_SERVER + "/claims/from/" + currentPerson.getEmail() + "?settled=" + onlyUnsettled)
                .asString()
                .getBody();
        if (JSONClaims.contains("Not found")){
            List<Claim> claims = new ArrayList<Claim>();
            return claims;
        }else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Javalin.create(javalinConfig -> javalinConfig.jsonMapper(new JavalinJackson(mapper)));
            List<Claim> claims = mapper.readValue(JSONClaims, new TypeReference<List<Claim>>(){});
            return claims;

        }
    }

    public static List<Claim> getClaimsBy(Person currentPerson, boolean onlyUnsettled) throws JsonProcessingException {
        String JSONClaims = Unirest.get(WeShareWebServer.CLAIMS_SERVER + "/claims/by/" + currentPerson.getEmail() + "?settled=" + onlyUnsettled)
                .asString()
                .getBody();

        if (JSONClaims.contains("Not found")){
            List<Claim> claims = new ArrayList<Claim>();
            return claims;
        }else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Javalin.create(javalinConfig -> javalinConfig.jsonMapper(new JavalinJackson(mapper)));
            List<Claim> claims = mapper.readValue(JSONClaims, new TypeReference<List<Claim>>() {
            });
            return claims;
        }
    }


    public static double getTotalUnsettledClaimsClaimedFrom(Person currentPerson) {

        double totalClaimsAmount = Unirest.get(WeShareWebServer.CLAIMS_SERVER + "/totalclaimvalue/from/" + currentPerson.getEmail())
                .asObject(Double.class)
                .getBody();
        return totalClaimsAmount;

    }

    public static double getTotalUnsettledClaimsClaimedBy(Person currentPerson) {

        double totalClaimsAmount = Unirest.get(WeShareWebServer.CLAIMS_SERVER + "/totalclaimvalue/by/" + currentPerson.getEmail())
                .asObject(Double.class)
                .getBody();
        return totalClaimsAmount;
    }

}