package jake.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import jake.helpclasses.Singleton;
import jake.helpclasses.Task;
import jake.helpclasses.User;


public class TaskActivity extends AppCompatActivity {

    String url = "http://130.240.5.53:8080/tasks";
    RequestQueue requestQueue;
    ArrayList<Integer> timeList;
    ArrayList<Task> taskList = new ArrayList<Task>();
    Spinner spinnerTask;
    Spinner spinnerTime;
    ArrayAdapter<Task> adapter;
    ArrayAdapter<Integer> timeAdapter;
    private static final String TAG = TaskActivity.class.getName();
    JsonObjectRequest jsonRequest;


    Integer selectedTaskId;
    Integer selectedTime;
    Task task;
    Button bConfirmTask;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        spinnerTask = (Spinner) findViewById(R.id.spinnerTask);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        bConfirmTask = (Button) findViewById(R.id.bConfirmTask);

        timeList = new ArrayList<>();
        fillSpinnerTime(5);
        timeAdapter = new ArrayAdapter<>(TaskActivity.this, R.layout.custom_spinner, R.id.spinnerContent, timeList);
        spinnerTime.setAdapter(timeAdapter);

        requestQueue = Volley.newRequestQueue(this);
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
                    adapter = new ArrayAdapter<>(TaskActivity.this, R.layout.custom_spinner, R.id.spinnerContent, taskList);
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
                task = (Task) parent.getItemAtPosition(position);
                selectedTaskId = task.getTaskId();
                //Toast.makeText(TaskActivity.this, task.getTaskId() + " selected.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int time = (Integer) parent.getItemAtPosition(position);
                selectedTime = time;
                //Toast.makeText(TaskActivity.this, time + " selected.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        requestQueue.add(arrayreq);
        //Log.d("TAG", "selectedTaskId: " + selectedTaskId.toString() + "selectedTime: " + selectedTime.toString());


        bConfirmTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaskActivity.this,"You completed task with id " + selectedTaskId + " in " + selectedTime + " hours.", Toast.LENGTH_SHORT).show();
                address = LoginActivity.user.getUserAddress();
                url = getResources().getString(R.string.base_url) + "/user/registertask?address="+ address + "&id=" + selectedTaskId + "&time=" + 7;

                jsonRequest = new JsonObjectRequest
                        (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(TaskActivity.this, "Completed task registered. Good job!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TaskActivity.this, ProfileActivity.class);
                                startActivity(intent);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(TaskActivity.this, "No response from web server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Singleton.getInstance(TaskActivity.this).addToRequestQueue(jsonRequest);



            }
        });
    }








    public void fillSpinnerTime(int maximumHours) {

        for (int i = 0; i <= maximumHours; i++) {
            timeList.add(i);
        }

    }

}