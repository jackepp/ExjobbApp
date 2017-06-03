package jake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import jake.R;
import jake.helpclasses.Singleton;


public class LoginActivity extends Activity {


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
        message = (TextView) findViewById(R.id.message);

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
                                    if (response.getBoolean("auth") || response.getString("status") != "error") {
                                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                    } else {
                                        Log.d("TAG", "ELSE");
                                        Toast.makeText(LoginActivity.this, "Incorrect credentials, try again.", Toast.LENGTH_SHORT).show();
                                        etEmail.getText().clear();
                                        etPassword.getText().clear();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "No response from web server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Singleton.getInstance(LoginActivity.this).addToRequestQueue(jsonRequest);
            }
        });

    }

}