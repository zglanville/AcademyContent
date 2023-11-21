package za.co.wethinkcode.weshare.app.model;

public class NettValues {
    private Double expenseAmounts;
    private Double unsettledClaims;
    private Double settledClaims;
    private Double nettExpenses;

    public NettValues(Double expenseAmounts, Double unsettledClaims, Double settledClaims, Double nettExpenses) {
        this.expenseAmounts = expenseAmounts;
        this.unsettledClaims = unsettledClaims;
        this.settledClaims = settledClaims;
        this.nettExpenses = nettExpenses;
    }

    public Double getExpenseAmounts() {
        return expenseAmounts;
    }

    public Double getUnsettledClaims() {
        return unsettledClaims;
    }

    public Double getSettledClaims() {
        return settledClaims;
    }

    public Double getNettExpenses() {
        return nettExpenses;
    }
}
