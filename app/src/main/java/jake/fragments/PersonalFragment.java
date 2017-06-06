package jake.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

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
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        getAllInfo(v);
        return v;
    }


    public void getAllInfo(View v) {

        userName = user.getUserName();
        userLevel = user.getUserLevel().trim();
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
                            //points = 100;
                            int progress;
                            Log.d(TAG, "points: " + String.valueOf(points));

                            switch(userLevel){
                                case "Beginner":
                                    progress = (points / 10);   //0 base. 1k range.
                                    Log.d(TAG, "progress: " + String.valueOf(progress));
                                    progressBar.setProgress(progress);
                                    break;
                                case "Contributor":
                                    progress = ( (points - 1000) / 20); //1000 base. 2k range.
                                    progressBar.setProgress((int) progress);
                                    break;
                                case "Advanced Contributor":
                                    progress = ((points - 3000) / 20);  //3000 base. 2k range.
                                    progressBar.setProgress((int) progress);
                                    break;
                                case "Expert":
                                    progress = ((points - 5000)/ 40); //5000 base. 4k range.
                                    progressBar.setProgress((int) progress);
                                    break;
                                case "Master":
                                    progress = 100;
                                    progressBar.setProgress(progress);
                                    break;
                                default:
                                    Log.d(TAG, "DEFAULT");
                                    progressBar.setProgress(90);
                                    break;

                            }


                        } catch (JSONException e) {
                            Log.d("Error X ", e.toString());
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
                    Log.d("Error Y: ", e.toString());
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

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Integer amountOfBadges = response.length();
                    badgeView.setText(amountOfBadges.toString());

                } catch (Exception e) {
                    Log.d("Error Z", e.toString());

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error C", error.toString());
                    }
                }
        );
        Singleton.getInstance(getContext()).addToRequestQueue(arrayreq);

    }

}
