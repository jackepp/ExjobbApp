package jake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import jake.helpclasses.Singleton;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private String oldPassword, newPassword, confirmNewPassword, userAddress, url;
    private Button bConfirmChangePassword;
    private JsonObjectRequest jsonObjectRequest;
    private static final String TAG = ChangePasswordActivity.class.getName();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etOldPassword = (EditText) findViewById(R.id.oldpassowrd);
        etNewPassword = (EditText) findViewById(R.id.newpassowrd);
        etConfirmNewPassword = (EditText) findViewById(R.id.confirmnewpassword);
        bConfirmChangePassword = (Button) findViewById(R.id.bConfirmChangePassword);

        bConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                newPassword = etNewPassword.getText().toString();
                confirmNewPassword = etConfirmNewPassword.getText().toString();
                userAddress = LoginActivity.user.getUserAddress();
                oldPassword = etOldPassword.getText().toString();
                url = getResources().getString(R.string.base_url) + "/user/changepassword?address=" + userAddress + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword;

                if (!(oldPassword.isEmpty()) && !(newPassword.isEmpty()) && !(confirmNewPassword.isEmpty())) {
                    if (newPassword.equals(confirmNewPassword)) {
                        jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(ChangePasswordActivity.this, "Password changed.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ChangePasswordActivity.this, "No response from web server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Singleton.getInstance(ChangePasswordActivity.this).addToRequestQueue(jsonObjectRequest);
                    } else {
                        etNewPassword.getText().clear();
                        etConfirmNewPassword.getText().clear();
                        Toast.makeText(ChangePasswordActivity.this, "New passwords did not match. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter values in all inputs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

