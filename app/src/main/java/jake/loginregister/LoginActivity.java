package jake.loginregister;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class LoginActivity extends AppCompatActivity {

    public EditText etEmail;
    public EditText etPassword;
    public String email;
    public String password;
    public String url;

    public Button bLogin;
    public TextView message;
    public TextView errmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        message = (TextView) findViewById(R.id.message);
        errmsg = (TextView) findViewById(R.id.errmsg);

        bLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                url = "http://130.240.5.53:8080/signin?email=" + email + "&password=" + password;

                Log.d("CHECKPOINT", "LoginActivity.onClick()");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("CHECKPOINT", "LoginActivity.onResponse()");
                                errmsg.setText(url);
                                message.setText(response);
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error){
                                Log.d("CHECKPOINT", "LoginActivity.onErrorResponse()");

                                message.setText("No response from webserver.");
                                error.printStackTrace();
                                System.out.println(error.getStackTrace());
                            }
                });
                Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

    }

}
