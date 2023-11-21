package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import za.co.wethinkcode.weshare.app.Sessions;
import za.co.wethinkcode.weshare.app.DefaultAccessManager;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.claim.ClaimExpenseController;
import za.co.wethinkcode.weshare.claim.ClaimsApiController;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;
import za.co.wethinkcode.weshare.login.LoginController;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;
import za.co.wethinkcode.weshare.rating.RatingViewController;
import za.co.wethinkcode.weshare.settle.SettlementViewController;

import java.time.LocalDate;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class WeShareWebServer {
    private static int WEB_PORT = 7070;
    public static String EXPENSES_SERVER = "http://localhost:7001";
    public static String CLAIMS_SERVER = "http://localhost:7002";
    public static String RATINGS_SERVER = "http://localhost:7003";
    private static final String STATIC_DIR = "/html";
    private final Javalin app;


    /**
     * The main class starts the server on the default port 7070.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WeShareWebServer server = new WeShareWebServer();
        String portStr = System.getenv("WEB_PORT");
        String expServerUrl = System.getenv("EXPENSES_SERVER");
        String clmServerUrl = System.getenv("CLAIMS_SERVER");
        String rtnServerUrl = System.getenv("RATINGS_SERVER");
        if (expServerUrl != null) {
            EXPENSES_SERVER = expServerUrl;
        }
        if (clmServerUrl != null) {
            CLAIMS_SERVER = clmServerUrl;
        }
        if (rtnServerUrl != null) {
            RATINGS_SERVER = rtnServerUrl;
        }
        if (portStr != null) {
            WEB_PORT = Integer.parseInt(portStr);
        }
        server.start(WEB_PORT);
    }


    public WeShareWebServer() {
        configureThymeleafTemplateEngine();
        app = createAndConfigureServer();
        setupRoutes(app);
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

    /**
     * Setup the Thymeleaf template engine to load templates from 'resources/templates'
     */
    private void configureThymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateEngine.setTemplateResolver(templateResolver);

        templateEngine.addDialect(new LayoutDialect());
        JavalinThymeleaf.configure(templateEngine);
    }

    private void setupRoutes(Javalin server) {
        server.routes(() -> {
            loginAndLogoutRoutes();
            homePageRoute();
            expenseRoutes();
            claimRoutes();
            settlementRoutes();
            ratingRoutes();
        });
    }

    private static void ratingRoutes() {
        path(RatingViewController.PATH, () -> {
            get(RatingViewController::renderRatingPage);
        });
    }


    private static void settlementRoutes() {
        path(SettlementViewController.PATH, () -> {
            get(SettlementViewController::renderSettleClaimForm);
            post(SettlementViewController::submitSettlement);
        });
    }

    private static void claimRoutes() {
        get(ClaimExpenseController.PATH, ClaimExpenseController::renderClaimExpensePage);
        post(ClaimsApiController.PATH, ClaimsApiController::create);
    }

    private static void expenseRoutes() {
        path("/newexpense", () -> get(context -> {
            Expense expense = new Expense(0.00, LocalDate.now(), "?", context.sessionAttribute("user"));
            context.render("expenseform.html", Map.of("expense", expense));
        }));
        post(CaptureExpenseController.PATH, CaptureExpenseController::createExpense);
    }

    private void homePageRoute() {
        path(NettExpensesController.PATH, () -> get(NettExpensesController::renderHomePage));
    }

    private void loginAndLogoutRoutes() {
        post(LoginController.LOGIN_PATH, LoginController::handleLogin);
        get(LoginController.LOGOUT_PATH, LoginController::handleLogout);
    }

    @NotNull
    private Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.addStaticFiles(STATIC_DIR, Location.CLASSPATH);
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }
}