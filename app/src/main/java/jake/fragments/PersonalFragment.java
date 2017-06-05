package jake.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import jake.R;
import jake.activities.LoginActivity;
import jake.activities.MainActivity;
import jake.activities.ProfileActivity;
import jake.activities.TaskActivity;
import jake.helpclasses.Singleton;

import static jake.R.id.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    ArrayList<String> list;
    Integer points;
    Integer badges;
    String url;
    JsonObjectRequest jsonObjectRequest;
    JsonArrayRequest jsonArrayRequest;

    TextView name;
    String userName;
    TextView level;
    TextView pointView;
    TextView badgeView;
    TextView rankView;
    Integer rank;
    Integer userId;
    String address;
    String userLevel;
    ArrayList<Integer> people;
    FloatingActionButton fab;


    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
        }



        name = (TextView) ((MainActivity) getActivity()).findViewById(R.id.name);
        level = (TextView) ((MainActivity) getActivity()).findViewById(R.id.level);
        badgeView = (TextView) ((MainActivity) getActivity()).findViewById(R.id.badges);
        pointView = (TextView) ((MainActivity) getActivity()).findViewById(R.id.points);
        rankView = (TextView) ((MainActivity) getActivity()).findViewById(R.id.rank);
        userName = LoginActivity.user.getUserName();

        userLevel = LoginActivity.user.getUserLevel();


        fab = (FloatingActionButton) ((MainActivity) getActivity()).findViewById(R.id.fab);

        name.setText(userName);
        level.setText(userLevel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                startActivity(intent);
            }
        });

        address = LoginActivity.user.getUserAddress();
        url = getResources().getString(R.string.base_url) + "/point/get?address=" + address;

        Log.d("TAG", "url: " + url);

        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            points = Integer.parseInt(response.getString("points"));
                            //pointView.setText(String.valueOf(points));
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
        people = new ArrayList<>();
        userId = LoginActivity.user.getUserid();

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




    }
}
