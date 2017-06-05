package jake.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jake.activities.R;
import jake.activities.TaskActivity;
import jake.helpclasses.Singleton;

import static jake.activities.LoginActivity.user;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    private Integer points, rank, userId;
    private JsonObjectRequest jsonObjectRequest;
    private JsonArrayRequest jsonArrayRequest;
    private TextView name, level, pointView, badgeView, rankView;
    private String address, url, userLevel, userName, userBadges;
    private FloatingActionButton fab;

    private static final String TAG = PersonalFragment.class.getName();


    public PersonalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_personal, container, false);

        name = (TextView) v.findViewById(R.id.name);
        level = (TextView) v.findViewById(R.id.level);
        badgeView = (TextView) v.findViewById(R.id.badges);
        pointView = (TextView) v.findViewById(R.id.points);
        rankView = (TextView) v.findViewById(R.id.rank);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        getAllInfo(v);
        // Inflate the layout for this fragment
        return v;
    }


    public void getAllInfo(View v){

        userName = user.getUserName();
        userLevel = user.getUserLevel();
        address = user.getUserAddress();
        url = getResources().getString(R.string.base_url) + "/point/get?address=" + address;

        name.setText(userName);
        level.setText(userLevel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                startActivity(intent);
            }
        });

        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            points = Integer.parseInt(response.getString("points"));
                            pointView.setText(String.valueOf(points));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No response from web server while getting points.", Toast.LENGTH_SHORT).show();
                    }
                });
        Singleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

        url = getResources().getString(R.string.base_url) + "/scoreboard";
        userId = user.getUserid();

        jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);
                        Integer temp = jresponse.getInt("id");
                        if (userId == temp) {
                            rank = i + 1;
                            rankView.setText(String.valueOf(rank));
                        }
                    }

                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No response from web server while getting rank.", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        Singleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

        address = user.getUserAddress();

        url = getResources().getString(R.string.base_url) + "/user/badges?address=" + address;

        Log.d(TAG, "url: " + url);
        Log.d(TAG, "address: " + address);

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Integer amountOfBadges = response.length();
                    badgeView.setText(amountOfBadges.toString());

                } catch (Exception e) {
                    Log.e("Error", e.toString());
                    badgeView.setText("0");
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
