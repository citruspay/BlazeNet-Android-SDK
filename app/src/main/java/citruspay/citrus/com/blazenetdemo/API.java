package citruspay.citrus.com.blazenetdemo;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by mangesh on 7/1/16.
 */
public interface API {

    //Signup Token
    @Headers("Content-Type: application/json")
    @POST("/netbank/auth")
    Call<AccessToken> getToken(@Body RequestBody body);


    @Headers("Content-Type: application/json")
    @POST("/netbank/trans")
    Call<ResponseBody>  initTransaction(@Header("auth_token") String header, @Body RequestBody body);

}




