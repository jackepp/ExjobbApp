package jake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import jake.helpclasses.Singleton;
import jake.helpclasses.User;

public class LoginActivity extends Activity {

    private EditText etEmail, etPassword;
    private String email, password, url;
    private Button bLogin;
    private JsonObjectRequest jsonRequest;
    public static User user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

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

                url = getResources().getString(R.string.base_url) + "/signin?email=" + "jake@live.se" + "&password=" + "1";

                //if (!(email.isEmpty()) && !(password.isEmpty())) {
                    jsonRequest = new JsonObjectRequest
                            (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getBoolean("auth")) {
                                            user = new User(response);
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Incorrect credentials, try again.", Toast.LENGTH_SHORT).show();
                                            etPassword.getText().clear();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(LoginActivity.this, "Incorrect credentials, try again.", Toast.LENGTH_SHORT).show();
                                        etPassword.getText().clear();
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, "No response from web server.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                    Singleton.getInstance(LoginActivity.this).addToRequestQueue(jsonRequest);
                } //else {
                   // Toast.makeText(LoginActivity.this, "Please enter a value in both inputs", Toast.LENGTH_SHORT).show();
                    //return;
                //}
        //    }
        });
    }
}