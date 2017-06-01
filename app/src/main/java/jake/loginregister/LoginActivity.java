package jake.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    public EditText etEmail;
    public EditText etPassword;
    public String email;
    public String password;
    public String url;

    public Button bLogin;
    public TextView message;

    public JsonObjectRequest jsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                url = "http://130.240.5.53:8080/signin?email=" + email + "&password=" + password;

               jsonRequest = new JsonObjectRequest
                        (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    message.setText(
                                            " Name: " + response.getString("name") +
                                            "\n Email: " + response.getString("email") +
                                            "\n Level: "+ response.getString("level"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                Singleton.getInstance(LoginActivity.this).addToRequestQueue(jsonRequest);
            }
        });

            }

}