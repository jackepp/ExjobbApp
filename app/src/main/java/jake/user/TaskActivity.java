package jake.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jake.loginregister.R;
import jake.loginregister.Singleton;

public class TaskActivity extends AppCompatActivity {


    TextView message;
    String url ="http://130.240.5.53:8080/tasks";
    String jsonUrl = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Array.JSON";
    String data = "";
    RequestQueue requestQueue;
    String id;
    String name;
    String vph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        requestQueue = Volley.newRequestQueue(this);

        message = (TextView) findViewById(R.id.message);


       /* JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject Obj = response.getJSONObject(0);
                            id = Obj.getString("id");
                            name = Obj.getString("name");
                            vph = Obj.getString("valuePerHour");

                            data = "Id " + id + "nColor Name: " + name +
                                    "nHex Value : " + vph + "nnn";

                            message.setText(data);

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        */
        JsonArrayRequest arrayreq = new JsonArrayRequest(jsonUrl,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Retrieves first JSON object in outer array
                            JSONObject colorObj = response.getJSONObject(0);
                            // Retrieves "colorArray" from the JSON object
                            JSONArray colorArry = colorObj.getJSONArray("colorArray");
                            // Iterates through the JSON Array getting objects and adding them
                            //to the list view until there are no more objects in colorArray

                                //gets each JSON object within the JSON array
                                JSONObject jsonObject = colorArry.getJSONObject(0);

                                // Retrieves the string labeled "colorName" and "hexValue",
                                // and converts them into javascript objects
                                String color = jsonObject.getString("colorName");
                                String hex = jsonObject.getString("hexValue");

                                // Adds strings from the current object to the data string
                                //spacing is included at the end to separate the results from
                                //one another
                                data = "Name: " + color + "\n"+
                                        "Hex Value : " + hex;

                            // Adds the data string to the TextView "results"
                            message.setText(data);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON array request "arrayreq" to the request queue
        requestQueue.add(arrayreq);


    }
}
