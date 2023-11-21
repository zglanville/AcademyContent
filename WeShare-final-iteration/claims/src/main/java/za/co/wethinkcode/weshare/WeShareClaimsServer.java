package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import za.co.wethinkcode.weshare.claim.ClaimController;

public class WeShareClaimsServer {
    private static int CLAIMS_PORT = 7002;
    public static String EXPENSES_SERVER = "http://localhost:7001";
    private final Javalin app;


    /**
     * The main class starts the Claims server on the default port 7001.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WeShareClaimsServer server = new WeShareClaimsServer();
        String portStr = System.getenv("CLAIMS_PORT");
        String expServerUrl = System.getenv("EXPENSES_SERVER");
        if (expServerUrl != null) {
            EXPENSES_SERVER = expServerUrl;
        }
        if (portStr != null) {
            CLAIMS_PORT = Integer.parseInt(portStr);
        }
        server.start(CLAIMS_PORT);
    }

    public WeShareClaimsServer() {
        app = Javalin.create();
//      JSON Object of claim
        app.get("/claim/{id}",
                ClaimController::getClaim);
//      return HTTP STATUS add Claim to Expense
        app.post("/claim",
                ClaimController::create);
//      return HTTP STATUS update Claim (settled value)
        app.put("/claim",
                ClaimController::updateClaim);
//      JSON list of claims
        app.get("/claims/from/{email}",
                ClaimController::getClaimsFrom);
//      JSON list of claims
        app.get("/claims/by/{email}",
                ClaimController::getClaimsBy);
//      return HTTP STATUS
        app.post("/settlement",
                ClaimController::submitSettlement);
//      JSON Object of sum
        app.get("/totalclaimvalue/from/{email}",
                ClaimController::getTotalClaimsFrom);
//      JSON Object of sum
        app.get("/totalclaimvalue/by/{email}",
                ClaimController::getTotalClaimsBy);
        app.options("/claim", ClaimController::setOptions);
    }



    public void start(int port) {
       app.start(port);
    }

    public int port() {
        return app.port();
    }

    public void close() {
        app.close();
    }
}