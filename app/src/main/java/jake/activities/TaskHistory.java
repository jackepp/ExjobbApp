package jake.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jake.helpclasses.Badge;
import jake.helpclasses.BadgeListAdapter;
import jake.helpclasses.Singleton;
import jake.helpclasses.Task;
import jake.helpclasses.TaskListAdapter;

public class TaskHistory extends Activity {

    private JsonArrayRequest arrayreq;
    private String url, userAddress;
    private TaskListAdapter adapter;
    ArrayList<Task> tasks;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);

        userAddress = LoginActivity.user.getUserAddress();
        url = getResources().getString(R.string.base_url) + "/user/tasks?address=" + userAddress;
        tasks = new ArrayList<>();
        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        tasks.add(new Task(jresponse));
                    }

                    TaskListAdapter adapter = new TaskListAdapter(TaskHistory.this, R.layout.list_adapter_view_badges, tasks);
                    listView.setAdapter(adapter);
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
        Singleton.getInstance(TaskHistory.this).addToRequestQueue(arrayreq);
    }


    }





