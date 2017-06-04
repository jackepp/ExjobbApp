package jake.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jake.fragments.TeamFragment;
import jake.fragments.PersonalFragment;
import jake.helpclasses.Singleton;

import jake.fragments.BadgeFragment;
import jake.R;

public class ProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        /*name = (TextView) findViewById(R.id.name);
        level = (TextView) findViewById(R.id.level);
        badgeView = (TextView) findViewById(R.id.badges);
        pointView = (TextView) findViewById(R.id.points);
        rankView = (TextView) findViewById(R.id.rank);*/

        userName = LoginActivity.user.getUserName();
        //name.setText(userName);

        userLevel = LoginActivity.user.getUserLevel();
        //level.setText(userLevel);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TaskActivity.class);
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
                        Toast.makeText(ProfileActivity.this, "No response from web server while getting points.", Toast.LENGTH_SHORT).show();
                    }
                });
        Singleton.getInstance(ProfileActivity.this).addToRequestQueue(jsonObjectRequest);

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
                        Toast.makeText(ProfileActivity.this, "No response from web server while getting rank.", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        Singleton.getInstance(ProfileActivity.this).addToRequestQueue(jsonArrayRequest);
    }


    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Personal");
        tabOne.setGravity(Gravity.CENTER_HORIZONTAL);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.person_white, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Team");
        tabTwo.setGravity(Gravity.CENTER_HORIZONTAL);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.leaderboard_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Badges");
        tabThree.setGravity(Gravity.CENTER_HORIZONTAL);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new PersonalFragment(), "one");
        adapter.addFragments(new TeamFragment(), "two");
        adapter.addFragments(new BadgeFragment(), "three");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragments(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
