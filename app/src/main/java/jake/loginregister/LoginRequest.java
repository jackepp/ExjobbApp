package jake.loginregister;


import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Created by jake on 2017-05-24.
 */

public class LoginRequest extends StringRequest {


    private static final String LOGIN_REQUEST_URL="http://130.240.5.53:8080/signin";
    private Map<String, String> params;


    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        //Log.d("CHECKPOINT", "LoginRequest constructor");
    }

    @Override
    public Map<String, String> getParams() {
        //Log.d("CHECKPOINT", "LoginRequest > getParams");
        return params;
    }
}
