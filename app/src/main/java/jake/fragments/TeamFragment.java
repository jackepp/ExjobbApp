package jake.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import jake.activities.TeamActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {


    ListView listView;
    ArrayAdapter<String> adapter;
    String url;
    ArrayList<String> team = new ArrayList<String>();
    RequestQueue requestQueue;

    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getResources().getString(R.string.base_url) + "/scoreboard";
        requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("TAG", "url: "+ url);

        listView = (ListView) getActivity().findViewById(R.id.mobile_list);

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        team.add(jresponse.getString("name"));
                        Log.d("TAG", "jresponse.getString(): " + jresponse.getString("name"));
                    }
                    adapter = new ArrayAdapter<>(getActivity(), R.layout.row_layout, R.id.textView1, team);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false);
    }
}
