package citruspay.citrus.com.blazenetdemo;

/**
 * Created by mangesh on 7/1/16.
 */

public interface APICallBack<T> {

    void success(T t);

    void error(APIError error);
}