package jake.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import jake.helpclasses.ThreeColumn_ListAdapter;
import jake.helpclasses.User;


public class TeamActivity extends AppCompatActivity {


    String url;
    ArrayList<User> scoreboard;
    RequestQueue requestQueue;
    ListView listView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        url = getResources().getString(R.string.base_url) + "/scoreboard";
        requestQueue = Volley.newRequestQueue(this);
        Log.d("TAG", "url: "+ url);

        scoreboard = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView);

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        Integer rank = i + 1;
                        scoreboard.add(new User(rank.toString(), jresponse.getString("name"), jresponse.getString("points")));
                    }

                    ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(TeamActivity.this, R.layout.list_adapter_view, scoreboard);
                    listView.setAdapter(adapter);
                    //Log.d("TAG", scoreboard.get(0).getUserName() + String.valueOf(scoreboard.get(0).getUserPoints()));
                   // Log.d("TAG", scoreboard.get(1).getUserName() + String.valueOf(scoreboard.get(1).getUserPoints()));
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
        //Log.d("TAG", "scoreboard.get(o).toString(): " + scoreboard.get(1).toString());
    }

}
