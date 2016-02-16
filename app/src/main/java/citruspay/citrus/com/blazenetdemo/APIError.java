package citruspay.citrus.com.blazenetdemo;

/**
 * Created by mangesh on 7/1/16.
 */
public class APIError {
    private String errorMessage = null;

    public APIError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
