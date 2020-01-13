package API.Errors;

public class Error {
    private Error() {
        //Constructor voor sonar validatie
    }
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
    public static final String INVALID_TABLEID = "INVALID_TABLEID";
    public static final String INVALID_ID = "INVALID_ID";
    public static final String INVALID_TRANSACTION_ID = "INVALID_TRANSACTION_ID";
    public static final String DATABASEERROR = "DATABASE ERROR";
}
