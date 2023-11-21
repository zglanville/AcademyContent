package za.co.wethinkcode.weshare.app.db.memory;

import com.google.common.collect.ImmutableList;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import za.co.wethinkcode.weshare.WeShareClaimsServer;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryDb implements DataRepository {
    private final Set<Claim> claims = new HashSet<>();
    private final Set<Settlement> settlements = new HashSet<>();

    volatile long lastPersonId = 0L;

    public MemoryDb() {
        setupTestData();
    }

    //<editor-fold desc="Claims">

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Claim> getClaims() {
        return ImmutableList.copyOf(claims);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Claim> getClaimsBy(Person claimedBy, boolean onlyUnsettled) {
        return claims.stream().filter(claim -> claim.getClaimedBy().equals(claimedBy)
                        && (!onlyUnsettled || !claim.isSettled()))
                .sorted(Comparator.comparing(Claim::getDueDate))
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Claim> getClaim(UUID id) {
        return claims.stream()
                .filter(Claim -> Claim.getId().equals(id))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Claim addClaim(Claim claim) {
        claims.add(claim);
        return claim;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeClaim(Claim claim) {
        claims.remove(claim);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateClaim(Claim updatedClaim) {
        Optional<Claim> ClaimOpt = claims.stream().filter(Claim -> Claim.equals(updatedClaim)).findFirst();
        if (ClaimOpt.isPresent()) {
            claims.remove(ClaimOpt.get());
            claims.add(updatedClaim);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Claim> findUnsettledClaimsClaimedBy(Person person) {
        return claims.stream().filter(Claim ->
                        Claim.getClaimedBy().getEmail().equalsIgnoreCase(person.getEmail()) &&
                                !Claim.isSettled())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalUnsettledClaimsClaimedBy(Person person) {
        return findUnsettledClaimsClaimedBy(person).stream().mapToDouble(Claim::getAmount).sum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalUnsettledClaimsClaimedFrom(Person person) {
        return findUnsettledClaimsClaimedFrom(person).stream().mapToDouble(Claim::getAmount).sum();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ImmutableList<Claim> getClaimsFrom(Person claimedFrom, boolean onlyUnsettled) {
        return claims.stream()
                .filter(claim -> claim.getClaimedFrom().equals(claimedFrom) && (!onlyUnsettled || !claim.isSettled()))
                .sorted(Comparator.comparing(Claim::getDueDate))
                .collect(ImmutableList.toImmutableList());
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ImmutableList<Claim> findUnsettledClaimsClaimedFrom(Person person) {
        return getClaimsFrom(person, true);
    }
    //</editor-fold>

    //<editor-fold desc="Settlements">

    /**
     * {@inheritDoc}
     */
    @Override
    public Settlement addSettlement(Settlement settlement) {
        settlements.add(settlement);
        return settlement;
    }

    @Override
    public void dropClaims() {
        claims.clear();
    }

    @Override
    public void dropSettlements() {
        settlements.clear();
    }
    //</editor-fold>


    private void setupTestData() {

//        Calls all expenses for herman and Mike below.
        String JSONExpenseHerman = Unirest.get(WeShareClaimsServer.EXPENSES_SERVER+"/expenses")
                .queryString("email","herman@wethinkcode.co.za")
                .asString()
                .getBody();

        String JSONExpenseMike = Unirest.get(WeShareClaimsServer.EXPENSES_SERVER+"/expenses")
                .queryString("email","mike@wethinkcode.co.za")
                .asString()
                .getBody();

//        According to the test data, only their first expenses should have claims, ie. braai(herman) and beers(mike)
        JSONObject MikeBeerExpense = new JSONArray(JSONExpenseMike).getJSONObject(0);
        JSONObject HermanBraaiExpense = new JSONArray(JSONExpenseHerman).getJSONObject(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");

        JSONArray dateBlockHerman = (JSONArray) HermanBraaiExpense.get("date");
        if(dateBlockHerman.get(1).toString().length() == 1){
            dateBlockHerman.put(1,"0"+dateBlockHerman.get(1));
        }
//        I created the String below because LocalDate.parse wouldn't parse when the source String (dateBlock)
//        was sent through
        String stringDateHerman = dateBlockHerman.get(2)+"-"+dateBlockHerman.get(1)+"-"+dateBlockHerman.get(0);
        LocalDate dateHerman = LocalDate.parse(stringDateHerman, formatter);

        JSONArray dateBlockMike = (JSONArray) HermanBraaiExpense.get("date");
        if(dateBlockMike.get(1).toString().length() == 1){
            dateBlockMike.put(1,"0"+dateBlockMike.get(1));
        }
        String stringDateMike = dateBlockMike.get(2)+"-"+dateBlockMike.get(1)+"-"+dateBlockMike.get(0);
        LocalDate dateMike = LocalDate.parse(stringDateMike, formatter);

//        Converting the JSONObject data to an Expense for both Mike and Herman.
        Expense HermanExpense = new Expense(
                HermanBraaiExpense.getDouble("amount"),
                dateHerman,
                HermanBraaiExpense.getString("description"),
                new Person("herman@wethinkcode.co.za")
        );

        Claim HermanClaim = new Claim(HermanExpense,
                new Person("mike@wethinkcode.co.za"),
                200.0,
                LocalDate.of(2021, 11, 1)
        );
        addClaim(HermanClaim);

        Expense MikeExpense = new Expense(
                MikeBeerExpense.getDouble("amount"),
                dateMike,
                MikeBeerExpense.getString("description"),
                new Person("mike@wethinkcode.co.za")
        );

        Claim MikeClaim = new Claim(MikeExpense,
                new Person("herman@wethinkcode.co.za"),
                200.0,
                LocalDate.of(2021, 11, 1)
        );
        addClaim(MikeClaim);
    }
}