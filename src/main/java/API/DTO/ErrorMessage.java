package API.DTO;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class ErrorMessage {
    private Response.Status errorCode;
    private ArrayList<String> errorMessage;
    private String errorBody;

    public ErrorMessage(Response.Status errorCode, ArrayList<String> errorMessage, String errorBody) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorBody = errorBody;
    }

    public ErrorMessage(Response.Status errorCode, ArrayList<String> errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorMessage() {
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }

    public Response.Status getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Response.Status errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ArrayList<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
