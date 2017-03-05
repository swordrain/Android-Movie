package edu.self.movies.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.self.movies.R;
import edu.self.movies.fragment.ComingFragment;
import edu.self.movies.fragment.InTheaterFragment;
import edu.self.movies.fragment.SearchFragment;
import edu.self.movies.fragment.Top250Fragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();

    }


    private void initControls() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new Top250Fragment();
                        break;
                    case 1:
                        fragment = new InTheaterFragment();
                        break;
                    case 2:
                        fragment = new ComingFragment();
                        break;
                    case 3:
                        fragment = new SearchFragment();
                        break;
                    default:

                        break;
                }
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Top250";
                    case 1:
                        return "In Theater";
                    case 2:
                        return "Coming";
                    case 3:
                        return "Search";
                    default:
                        return "Top250";
                }
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }
}
