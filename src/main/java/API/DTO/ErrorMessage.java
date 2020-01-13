package API.DTO;

import API.Errors.Error;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class ErrorMessage {
    private Response.Status errorCode = Response.Status.BAD_REQUEST;
    private ArrayList<String> errorMessage;
    private String errorBody;


    public ErrorMessage( ArrayList<String> errorMessage) {
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
