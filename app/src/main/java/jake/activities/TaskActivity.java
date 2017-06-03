package jake.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jake.helpclasses.Task;
import jake.R;


public class TaskActivity extends AppCompatActivity {

    String url = "http://130.240.5.53:8080/tasks";
    RequestQueue requestQueue;
    ArrayList<String> stringList;
    ArrayList<Task> taskList = new ArrayList<Task>();
    Spinner spinnerTask;
    Spinner spinnerTime;
    ArrayAdapter<Task> adapter;
    TextView message;
    private static final String TAG = TaskActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        requestQueue = Volley.newRequestQueue(this);

        spinnerTask = (Spinner) findViewById(R.id.spinnerTask);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        message = (TextView) findViewById(R.id.message);


        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        //String temp = jresponse.g();
                        taskList.add(new Task(Integer.parseInt(jresponse.getString("id")),
                                jresponse.getString("name"),
                                Integer.parseInt(jresponse.getString("valuePerHour"))));
                        //message.setText(taskList.get(i).getTaskId());
                    }
                    adapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_list_item_1, taskList);
                    spinnerTask.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );

        spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                Toast.makeText(TaskActivity.this, task.toString() + " selected.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        requestQueue.add(arrayreq);
    }
}