package za.co.wethinkcode.weshare.settle;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import kong.unirest.Unirest;
import za.co.wethinkcode.weshare.WeShareWebServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Settlement;
import za.co.wethinkcode.weshare.claim.ClaimsApiController;
import za.co.wethinkcode.weshare.rating.RatingViewController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {
    public static final String PATH = "/settleclaim";

    public static void renderSettleClaimForm(Context context) throws JsonProcessingException {
        UUID claimId = UUID.fromString(Objects.requireNonNull(context.queryParam("claimId")));

        Claim claim = ClaimsApiController.getClaimById(claimId);
        context.render("settleclaim.html", Map.of("claim", claim));

    }

    public static void submitSettlement(Context context) throws JsonProcessingException {
        UUID claimId = UUID.fromString(Objects.requireNonNull(context.formParam("id")));
        Claim claim = ClaimsApiController.getClaimById(claimId);

        Settlement settlement = claim.settleClaim(LocalDate.now());
        Unirest.post(WeShareWebServer.CLAIMS_SERVER + "/settlement")
                .body(settlement);
        RatingViewController.sendSettlement(settlement);
        ClaimsApiController.updateClaim(claim);
        RatingViewController.sendClaim(claim);

        context.redirect("/home");
    }

}