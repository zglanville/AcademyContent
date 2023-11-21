package za.co.wethinkcode.weshare;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.weshare.expense.server.WeShareExpenseService;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseTest {
    private static final int TEST_SERVER_PORT = 8081;
    private static final String BASE_URL = "http://localhost:" + TEST_SERVER_PORT;
    private static WeShareExpenseService server;

    @BeforeAll
    public static void startServer() {
        server = new WeShareExpenseService();
        server.start(TEST_SERVER_PORT);
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("Server status: GET /printID")
    void testPrintIDSSuccessfulReturn() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/printID").asString();
        assertThat(resp.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Server status: GET /expenses")
    void testGetListOfExpensesByFoundEmail() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses?email=herman@wethinkcode.co.za").asString();
        assertThat(resp.getStatus()).isEqualTo(200);
        assertThat(resp.getBody()).isNotEmpty();
    }

    @Test
    @DisplayName("Server status: GET /expenses")
    void testGetListOfExpensesByNotFoundEmail() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses?email=false@wethinkcode.co.za").asString();
        assertThat(resp.getStatus()).isEqualTo(400);
        assertThat(resp.getBody()).isEqualTo("Email not found: false@wethinkcode.co.za");
    }

    @Test
    @DisplayName("Server status: GET /expenses/{id}")
    void testExpensesByValidAndFoundID() {
        HttpResponse<String> getID = Unirest.get(BASE_URL + "/printID").asString();
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses/" + getID.getBody().replace("\"", "")).asString();
        assertThat(resp.getStatus()).isEqualTo(200);
        assertThat(resp.getBody()).isNotEmpty();
    }

    @Test
    @DisplayName("Server status: GET /expenses/{id}")
    void testExpensesByValidAndNotFoundID() {
        UUID id = UUID.randomUUID();
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses/" + id).asString();
        assertThat(resp.getStatus()).isEqualTo(404);
        assertThat(resp.getBody()).isEqualTo("Expense not found: " + id);
    }

    @Test
    @DisplayName("Server status: GET /expenses/{id}")
    void testExpensesByInvalidID() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses/4").asString();
        assertThat(resp.getStatus()).isEqualTo(400);
        assertThat(resp.getBody()).isEqualTo("Malformed ID: 4");
    }

    @Test
    @DisplayName("Server status: POST /expenses")
    void testAddNewExpense() {
        HttpResponse<JsonNode> resp = generateExpense();
        assertThat(resp.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Server status: POST /expenses")
    void testAddClaimFail() {
        Map<String, String> expenseDetails =
                Map.of("id", "164",
                        "claimAmount", "2022-6-12");

        Map<String, Map> expense =
                Map.of("addClaim", expenseDetails);

        HttpResponse<JsonNode> resp = Unirest.post(BASE_URL + "/expenses")
                .header("content-type", "application/json")
                .body(expense)
                .asJson();

        assertThat(resp.getStatus()).isEqualTo(400);
    }

    private HttpResponse<JsonNode> generateExpense() {
        Map<String, String> expenseDetails =
                Map.of("amount", "164",
                        "date", "2022-6-12",
                        "description", "Candy",
                        "person", "steve@wethinkcode.co.za");

        Map<String, Map> expense =
                Map.of("expense", expenseDetails);

        HttpResponse<JsonNode> resp = Unirest.post(BASE_URL + "/expenses")
                .header("content-type", "application/json")
                .body(expense)
                .asJson();
        return resp;
    }
}
