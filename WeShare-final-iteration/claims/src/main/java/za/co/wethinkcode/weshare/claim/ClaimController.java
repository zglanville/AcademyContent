package za.co.wethinkcode.weshare.claim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.json.JavalinJackson;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.eclipse.jetty.util.ajax.JSON;
import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.WeShareClaimsServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static za.co.wethinkcode.weshare.WeShareClaimsServer.EXPENSES_SERVER;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimController {

    static DataRepository dbase = DataRepository.getInstance();

    public static void getClaim(Context context){
        try{
            Optional<Claim> maybeClaim = dbase.getClaim(UUID.fromString(context.pathParam("id")));
            if (maybeClaim.isEmpty()){
                context.status(404);
            }else{
                Claim claim = maybeClaim.get();
                context.json(claim);
            }
        }catch(Error e){
            context.status(400);
        }
    }

    public static void updateClaim(Context context){
        try {
            Claim updatedClaim = context.sessionAttribute("claim");
            dbase.updateClaim(updatedClaim);
            context.status(200);
        }catch(Error e){
            context.status(400);
        }
    }

    public static void getClaimsFrom(Context context){
        String settledString =context.queryParam("settled");
        boolean settled = parseBoolean(settledString);
        List<Claim> claims = dbase.getClaimsFrom(new Person(context.pathParam("email")),settled);
        if (claims.isEmpty()){
            context.json("[]");
        }else{
            context.json(claims);
        }
        context.status(200);
    }

    public static void getClaimsBy(Context context){
        String settledString =context.queryParam("settled");
        boolean settled = parseBoolean(settledString);
        List<Claim> claims = dbase.getClaimsBy(new Person(context.pathParam("email")),settled);
        if (claims.isEmpty()){
            context.json("[]");
        }else{
            context.json(claims);
        }
        context.status(200);
    }

    public static void getTotalClaimsFrom(Context context){
        double amount = dbase.getTotalUnsettledClaimsClaimedFrom(new Person(context.pathParam("email")));
        context.json(amount);
    }

    public static void getTotalClaimsBy(Context context){
        double amount = dbase.getTotalUnsettledClaimsClaimedBy(new Person(context.pathParam("email")));
        context.json(amount);
    }

    private static void postExpenseClaim(String id, double amount) throws IOException {
        JSONObject addClaim = new JSONObject();
        JSONObject add = new JSONObject();
        add.put("id",id);
        add.put("claimAmount",amount);
        addClaim.put("addClaim",add);
        Unirest.post(WeShareClaimsServer.EXPENSES_SERVER+"/expenses").body(addClaim).asJson();
    }

    private static void postExpense(Expense expense) throws IOException {
        Unirest.post(WeShareClaimsServer.EXPENSES_SERVER+"/expenses").body(expense.toString()).asJson();
    }

    private static Expense getExpense(String expenseID) throws JsonProcessingException {
        JsonNode expenseString = Unirest.get(WeShareClaimsServer.EXPENSES_SERVER+"/expenses/"+expenseID)
                .asJson().getBody();

        JSONObject expenseObject = new JSONObject(expenseString.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        JSONArray claimDate = expenseObject.getJSONArray("date");
        String Date;
        if (claimDate.get(2).toString().length() == 1){
            Date = "0"+claimDate.get(2).toString()+"-";
        }else{
            Date = claimDate.get(2).toString()+"-";
        }
        if (claimDate.get(1).toString().length() == 1){
            Date = Date+"0"+claimDate.get(1).toString()+"-";
        }else{
            Date = Date+claimDate.get(1).toString()+"-";
        }
        Date = Date+claimDate.get(0).toString();
        System.out.println(Date+" Date string Print check");

        Expense expense = new Expense(
                (double) expenseObject.get("amount"),
                LocalDate.parse(Date,formatter),
                expenseObject.getString("description"),
                new Person(expenseObject.getJSONObject("paidBy").getString("email"))
        );
        return expense;
    }

    public static void create(@NotNull Context context) throws IOException {
        context.header("Access-Control-Allow-Origin", "*");
        try{
            String body = context.body();
            JSONObject contextObject = new JSONObject(body);

            Expense expense = getExpense(contextObject.getString("expenseId"));

            String claimFromEmail = contextObject.getJSONObject("claim").getString("claimFromWho");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            String[] claimDate = contextObject.getJSONObject("claim").getString("dueDate").split("-");
            String Date = claimDate[2]+"-"+claimDate[1]+"-"+claimDate[0];

            Person p = new Person(claimFromEmail);
            System.out.println(p+" print Check");
            Claim c = expense.createClaim(p,
                    Double.parseDouble(contextObject.getJSONObject("claim").getString("claimAmount")),
                    LocalDate.parse(Date,formatter));
            System.out.println(c+ " print Check");
            dbase.addClaim(c);
            System.out.println("before posting Expense print Check");
            postExpenseClaim(contextObject.getString("expenseId"),
                    Double.parseDouble(contextObject.getJSONObject("claim").getString("claimAmount")));
            System.out.println("posted Expense print Check");

            context.status(201);
            context.json(c);
        }catch(Error e){
            context.status(400);
        }
    }

    public static void setOptions(Context context) {
        context.header("Access-Control-Allow-Origin", "*");
        context.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        context.header("Access-Control-Allow-Headers", "*");
        context.header("Accept", "*");
        context.status(200);
    }

    public static void submitSettlement(Context context) {
        UUID claimId = UUID.fromString(Objects.requireNonNull(context.formParam("id")));
        Optional<Claim> maybeClaim = DataRepository.getInstance().getClaim(claimId);
        if (maybeClaim.isEmpty()) {
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid claim");
            return;
        }
        Claim claim = maybeClaim.get();
        Settlement settlement = claim.settleClaim(LocalDate.now());
        dbase.addSettlement(settlement);
        dbase.updateClaim(claim);
        Expense generatedExpense = settlement.getGeneratedExpense();
        try{
            postExpense(generatedExpense);
            context.status(200);
        }catch(IOException e){
            context.status(400);
        }
    }
}