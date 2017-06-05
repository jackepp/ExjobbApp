package jake.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jake.activities.R;
import jake.helpclasses.Singleton;
import jake.helpclasses.ScoreboardListAdapter;
import jake.helpclasses.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    String url;
    ArrayList<User> scoreboard;
    ListView listView;
    ViewGroup headerView;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.viewcontents_layout, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        headerView = (ViewGroup) v.inflate(getActivity(), R.layout.header_view_scoreboard, null);
        listView.addHeaderView(headerView);

        getAllInfo(v);
        return v;
    }

    public void getAllInfo(View v) {
        url = getResources().getString(R.string.base_url) + "/scoreboard";
        scoreboard = new ArrayList<>();

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        Integer rank = i + 1;
                        scoreboard.add(new User(rank.toString(), jresponse.getString("name"), jresponse.getString("points")));
                    }

                    ScoreboardListAdapter adapter = new ScoreboardListAdapter(getActivity(), R.layout.list_adapter_view_scoreboard, scoreboard);
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
        Singleton.getInstance(getContext()).addToRequestQueue(arrayreq);
    }
}

