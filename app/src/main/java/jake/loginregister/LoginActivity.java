package jake.loginregister;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();


        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView message = (TextView) findViewById(R.id.message);
        final TextView errmsg = (TextView) findViewById(R.id.errmsg);
        bLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String url1 = "http://130.240.5.53:8080/signin?email=" + email + "&password=" + password;

                final String url2 = "http://130.240.5.53:8080/signin?email=viktor_atterlonn@live.se&password=123";

                StringBuilder server_url = new StringBuilder("http://130.240.5.53:8080/signin?email=");
                server_url.append(email);
                server_url.append("&password=");
                server_url.append(password);
                final String url3 = server_url.toString();

                final String url = url2;

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
