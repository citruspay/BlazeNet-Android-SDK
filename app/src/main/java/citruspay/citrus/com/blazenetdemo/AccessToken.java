package citruspay.citrus.com.blazenetdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mangesh on 7/1/16.
 */

public class AccessToken {

    @SerializedName("auth_token")
    private String authToken;

    /**
     *
     * @return
     * The authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @param authToken
     * The auth_token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    @Override
    public String toString() {
        return "AccessToken{" +
                "authToken='" + authToken + '\'' +
                '}';
    }
}