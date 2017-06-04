package jake.activities;

import android.app.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jake.R;
import jake.helpclasses.Task;
import jake.helpclasses.User;


public class TeamActivity extends Activity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String url;
    ArrayList<String> team = new ArrayList<String>();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        url = getResources().getString(R.string.base_url) + "/scoreboard";
        requestQueue = Volley.newRequestQueue(this);
        Log.d("TAG", "url: "+ url);

        listView = (ListView) findViewById(R.id.mobile_list);

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        team.add(jresponse.getString("name"));
                        Log.d("TAG", "jresponse.getString(): " + jresponse.getString("name"));
                    }
                    adapter = new ArrayAdapter<>(TeamActivity.this, R.layout.row_layout, R.id.textView1, team);
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
        requestQueue.add(arrayreq);
    }

}
