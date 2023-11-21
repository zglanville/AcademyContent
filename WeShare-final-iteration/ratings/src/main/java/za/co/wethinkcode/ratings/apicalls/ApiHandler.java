package za.co.wethinkcode.ratings.apicalls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import za.co.wethinkcode.ratings.app.db.DataRepository;
import za.co.wethinkcode.ratings.app.model.Person;

public class ApiHandler {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void update(Context context) throws JsonProcessingException {
        String event = context.queryParam("event");
        JsonNode body = objectMapper.readTree(context.body());
        String email;
        Double amount;
        switch (event){
            case "expense":
                email = body.get("email").asText();
                amount = body.get("email").asDouble();
//                Person person = DataRepository.getInstance().getPersonByEMail(email);
                //person.createExpense(amount);
                //update expense amount using email and amount
                System.out.println(email + amount);
            case "claims":
                email = body.get("email").asText();
                amount = body.get("email").asDouble();
            case "unsettled":
                email = body.get("email").asText();
                amount = body.get("email").asDouble();
                //update expense amount using email and amount
                System.out.println(email + amount);
            case "settled":
                email = body.get("email").asText();
                amount = body.get("email").asDouble();
                //update expense amount using email and amount
                System.out.println(email + amount);
            default:
                System.out.println("Hello");
        }
    }


    public static void getRatings(Context context){
        String ratings = context.queryParam("by");
        System.out.println(ratings);
        context.status(200);
        if(ratings == "expenses"){
            //returns here
        }

    }
}
