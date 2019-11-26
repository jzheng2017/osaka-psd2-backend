package API.DTO.RABO;

public class ExchangeRate {
    private String currencyFrom;
    private String currencyTo;
    private String rateContract;
    private String rateDate;
    private String rateFrom;
    private String rateTo;

    public ExchangeRate(String currencyFrom, String currencyTo, String rateContract, String rateDate, String rateFrom, String rateTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rateContract = rateContract;
        this.rateDate = rateDate;
        this.rateFrom = rateFrom;
        this.rateTo = rateTo;
    }

    public ExchangeRate() {
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getRateContract() {
        return rateContract;
    }

    public void setRateContract(String rateContract) {
        this.rateContract = rateContract;
    }

    public String getRateDate() {
        return rateDate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public String getRateFrom() {
        return rateFrom;
    }

    public void setRateFrom(String rateFrom) {
        this.rateFrom = rateFrom;
    }

    public String getRateTo() {
        return rateTo;
    }

    public void setRateTo(String rateTo) {
        this.rateTo = rateTo;
    }
}
