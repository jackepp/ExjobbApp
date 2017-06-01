package jake.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import jake.loginregister.R;
import jake.loginregister.Singleton;

public class TaskActivity extends AppCompatActivity{

    String url ="http://130.240.5.53:8080/tasks";
    String jsonUrl = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Array.JSON";
    String data = "";
    RequestQueue requestQueue;
    String id;
    String name;
    String vph;
    ArrayList<String> stringList;
    ArrayList<Task> taskList;
    Spinner spinnerTask;
    Spinner spinnerTime;
    ArrayAdapter<Task> adapter;
    private static final String TAG = TaskActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        requestQueue = Volley.newRequestQueue(this);

        spinnerTask = (Spinner) findViewById(R.id.spinnerTask);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);

        //stringList = new ArrayList<String>();
        taskList = new ArrayList<Task>();

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                // The second parameter Listener overrides the method onResponse() and passes
                //JSONArray as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i < response.length(); i++){
                                JSONObject jresponse = response.getJSONObject(i);

                                taskList.add(new Task(Integer.parseInt(jresponse.getString("id")),
                                        jresponse.getString("name"),
                                        Integer.parseInt(jresponse.getString("valuePerHour"))));
                            }
                        }
                        catch (Exception e){
                            Log.e("Error", e.toString());
                        }

                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );


        adapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_dropdown_item, taskList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTask.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", Integer.toString(position));
                Task task = (Task)parent.getItemAtPosition(position);
                Log.i("TAG", Integer.toString(task.getTaskId()));
                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+ " selected.", Toast.LENGTH_SHORT).show();
                spinnerTask.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        requestQueue.add(arrayreq);
        Log.d(TAG, "hi");
    }
}
