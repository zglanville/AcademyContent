package za.co.wethinkcode.weshare.expense.server;

import io.javalin.Javalin;

public class WeShareExpenseService {
    private final Javalin server;
    private int EXPENSE_PORT = 7001;

    public WeShareExpenseService() {
        server = Javalin.create();

        ExpenseServiceAPIHandler apiHandler = new ExpenseServiceAPIHandler();

        server.get("/expenses/{id}", context -> apiHandler.getExpenseByID(context));
        server.get("/expenses", context -> apiHandler.getExpensesByEmail(context));
        server.get("/person/{email}", context -> apiHandler.getInfoForPerson(context));
        server.post("/expenses", context -> apiHandler.expenseController(context));

        server.get("/printID", context -> apiHandler.printID(context));
    }

    public void start(int port) {
        int listen_port = port > 0 ? port : EXPENSE_PORT;
        this.server.start(listen_port);
    }

    public void stop() {
        this.server.stop();
    }

    public static void main(String[] args) {
        WeShareExpenseService server = new WeShareExpenseService();
        String portStr = System.getenv("EXPENSE_PORT");
        if (portStr != null) {
            server.EXPENSE_PORT = Integer.parseInt(portStr);
        }
        server.start(server.EXPENSE_PORT);
    }

}
