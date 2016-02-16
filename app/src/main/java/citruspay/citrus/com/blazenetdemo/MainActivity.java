package citruspay.citrus.com.blazenetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    RadioGroup radioGroup;

    Button btnPay;
    String BANKCID = null;


    BaseClient baseClient = null;

    private final String ACCESS_KEY = "mangesh";

    private final String SECRET_KEY = "mangesh_kadam";


    AccessToken accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioBank);

        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTransaction();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioICICI) {
                    BANKCID = "CID001";

                } else if (checkedId == R.id.radioAXIS) {
                    BANKCID = "CID002";
                } else if (checkedId == R.id.radioSBI) {
                    BANKCID = "CID005";
                } else {
                    BANKCID = "CID011";
                }
            }
        });

        initBaseClient();
    }


/*
    {
        "trans_id":"x2wewe",
            "currency":"INR",
            "bank_code":"CID001",
            "consumer_email":"sachin.tah@techvisions.in",
            "consumer_mobile":9881120870,
            "amount":1,
            "returl":"https://sboxblazenet.citruspay.com/netbank/trans/returnurl/",
            "append_trans_id":1,
            "digest_res" : 1
    }*/

    private void initTransaction() {
        JSONObject object = new JSONObject();
        try {
            object.put("trans_id", String.valueOf(System.currentTimeMillis()));
            object.put("currency", "INR");
            object.put("bank_code", BANKCID);
            object.put("consumer_email", "developercitrus@mailinator.com");
            object.put("consumer_mobile", 9881120870L);
            object.put("amount", 1);
            object.put("returl", "https://salty-plateau-1529.herokuapp.com/returnUrl.blazenet.php");
            object.put("append_trans_id", 0);
            object.put("digest_res", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        baseClient.initTransaction(accessToken.getAuthToken(), object, new APICallBack<String>() {
            @Override
            public void success(String s) {
                Logger.d("HTML PAGE *** " + s);

                Intent paymentIntent = new Intent(MainActivity.this, PaymentActivity.class);
                paymentIntent.putExtra("BANK_HTML", s);
                startActivityForResult(paymentIntent, 1000);

            }

            @Override
            public void error(APIError error) {

            }
        });

    }

    private void initBaseClient() {
        baseClient = new BaseClient(this);
        baseClient.init();

        JSONObject object = new JSONObject();
        try {
            object.put("access_key", ACCESS_KEY);
            object.put("secret_key", SECRET_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        baseClient.getToken(object, new APICallBack<AccessToken>() {
            @Override
            public void success(AccessToken token) {
                accessToken = token;
                Logger.d("TOKEN IS ***" + accessToken.getAuthToken());
            }

            @Override
            public void error(APIError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
