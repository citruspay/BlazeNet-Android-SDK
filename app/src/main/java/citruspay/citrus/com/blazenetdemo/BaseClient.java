package citruspay.citrus.com.blazenetdemo;

import android.content.Context;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONObject;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by mangesh on 7/1/16.
 */
public class BaseClient {

    protected Retrofit builder = null;
    OkHttpClient okHttpClient = null;

    private final String BASE_URL = "https://sboxblazenet.citruspay.com";

    private Context context;

    public BaseClient(Context context) {
        this.context = context;
    }


    private API api;

    public synchronized void init() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient();
        okHttpClient.setFollowRedirects(false);


        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                // .addConverterFactory(new ToStringConverterFactory())
                .build();
        api = builder.create(API.class);

        okHttpClient.interceptors().add(logging);
    }

    public void getToken(JSONObject object, final APICallBack<AccessToken> accessTokenAPICallBack) {
        Call<AccessToken> accessTokenCall = api.getToken(RequestBody.create(MediaType.parse("application/json"), object.toString()));


        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    accessTokenAPICallBack.success(response.body());
                }
                else {
                    sendError(accessTokenAPICallBack, response);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                sendError(accessTokenAPICallBack, t);
            }
        });


    }


    public void initTransaction(String header, JSONObject object, final APICallBack<String> transactionCallBack) {
        Call<ResponseBody> stringCall = api.initTransaction(header, RequestBody.create(MediaType.parse("application/json"), object.toString()));
        stringCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    try {
                        String bankHTML = new String(response.body().bytes());
                        transactionCallBack.success(bankHTML);
                    } catch (IOException e) {
                        sendError(transactionCallBack, new Error(e));
                    }
                }
                else {
                    sendError(transactionCallBack, response);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                    sendError(transactionCallBack, t);
            }
        });
    }



    protected void sendError(APICallBack callback, Throwable t) {
        if (callback != null) {
            com.orhanobut.logger.Logger.d("THROWABLE MESSAGE ***" + t.getMessage());
            callback.error(new APIError(t.getMessage()));
        }
    }

    protected <T> void sendError(APICallBack callback, Response<T> response) {
        if (callback != null) {

            if (response != null && response.errorBody() != null) {
                try {
                    String message = new String(response.errorBody().bytes());
                    callback.error(new APIError(message));
                    com.orhanobut.logger.Logger.d("ERROR **** " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



