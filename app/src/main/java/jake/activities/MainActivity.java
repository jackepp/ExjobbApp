package jake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jake.fragments.BadgeFragment;
import jake.fragments.PersonalFragment;
import jake.fragments.TeamFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_changepassword:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                return true;

            case R.id.action_signout:

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {
        TextView personalTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        personalTab.setText("Personal");
        personalTab.setGravity(Gravity.CENTER_HORIZONTAL);
        personalTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.person_white, 0, 0);
        tabLayout.getTabAt(0).setCustomView(personalTab);

        TextView teamTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        teamTab.setText("Team");
        teamTab.setGravity(Gravity.CENTER_HORIZONTAL);
        teamTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.leaderboard_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(teamTab);

        TextView badgesTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        badgesTab.setText("Badges");
        badgesTab.setGravity(Gravity.CENTER_HORIZONTAL);
        badgesTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.star_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(badgesTab);
    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PersonalFragment(), "Personal");
        adapter.addFrag(new TeamFragment(), "Team");
        adapter.addFrag(new BadgeFragment(), "Badges");
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
