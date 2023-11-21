package za.co.wethinkcode.weshare.expense;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.json.JavalinJackson;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.NettValues;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.rating.RatingViewController;

import javax.servlet.ServletOutputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    public static final String PATH = "/expenses";

    public static void createExpense(Context context){
        Person currentPerson = context.sessionAttribute("user");


//        //TODO proper server-side validation of form params
        String description = context.formParam("description");
        double amount;
        try {
            amount = Double.parseDouble(Objects.requireNonNull(context.formParam("amount")));
        } catch (NumberFormatException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid amount specified");
            return;
        }
        LocalDate date;
        try {
            date = LocalDate.parse(Objects.requireNonNull(context.formParam("date")));
        } catch (DateTimeException e){
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid due date specified");
            return;
        }

        Expense expenseObject = new Expense( amount, date, description, currentPerson );
        JSONObject expenseBody = new JSONObject();
        expenseBody.put("amount",expenseObject.getAmount());
        expenseBody.put("description",expenseObject.getDescription());
        expenseBody.put("date",expenseObject.getDate());
        expenseBody.put("person",currentPerson.getEmail());

        JSONObject expense = new JSONObject();
        expense.put("expense",expenseBody);
        Unirest.post(WeShareWebServer.EXPENSES_SERVER + "/expenses")
                .header("Content-Type","application/json")
                .body(expense)
                .asJson();
        RatingViewController.sendExpense(expenseObject);
//        DataRepository.getInstance().addExpense(expense);

        context.redirect("/home");
    }

    public static void updateExpense(Expense expense) {
        Unirest.post(WeShareWebServer.EXPENSES_SERVER+"/expenses")
                .body(expense);
        RatingViewController.sendExpense(expense);
    }

    public static List<Expense> getExpenses(Person currentPerson) throws JsonProcessingException, NoSuchFieldException {

        String JSONExpense = Unirest.get(WeShareWebServer.EXPENSES_SERVER+"/expenses?email="
                        + currentPerson.getEmail())
                .asString()
                .getBody();
        if (JSONExpense.contains("Email not found:")){
            List<Expense> expenses = new ArrayList<Expense>();
            return expenses;
        }else{

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Javalin.create(javalinConfig -> javalinConfig.jsonMapper(new JavalinJackson(mapper)));

            List<Expense> expenses = mapper.readValue(JSONExpense, new TypeReference<List<Expense>>(){});
            return expenses;
        }
    }

    public static Expense getExpenseById(UUID expenseId) throws JsonProcessingException {
        String JSONExpense = Unirest.get(WeShareWebServer.EXPENSES_SERVER+"/expenses/" + expenseId.toString())
                .asString()
                .getBody();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Javalin.create(javalinConfig -> javalinConfig.jsonMapper(new JavalinJackson(mapper)));
        Expense expenses = mapper.readValue(JSONExpense, new TypeReference<Expense>(){});

        return expenses;
    }


    public static Map<String, Double> getNettAmounts(Person currentPerson) throws JsonProcessingException {
        String jsonResponse = Unirest.get(WeShareWebServer.EXPENSES_SERVER + "/person/" + currentPerson.getEmail())
                .asString()
                .getBody();

        if (jsonResponse.equals(null)){
            Map<String,Double> nettAmount = new HashMap<String,Double>();
            return nettAmount;
        }else {
            Map<String, Double> nettAmount = Unirest.get(WeShareWebServer.EXPENSES_SERVER + "/person/" + currentPerson.getEmail())
                .asObject(new GenericType<Map<String, Double>>() {
                })
                .getBody();
            return nettAmount;
        }
    }
}