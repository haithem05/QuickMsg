package com.example.quickmsg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseUser  currentUser;
    private FirebaseAuth mAuth;
    private ViewPager viewPager;
    private TabItem friends_tab;
    private TabItem chat_tab;
    private TabItem profile_tab;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mAuth =FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setOffscreenPageLimit(3);



        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewPager.setClipToOutline(false);
        }

        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position,positionOffset,true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionn, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            sendUserToLogin();}
            if (item.getItemId() == R.id.setings) {
                sendUserToSettings();
            }

            if (item.getItemId() == R.id.FindFriends) {

            }


            return true;

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendUserToLogin() ;
        }

        else
        {
VerifyUserExistances();
        }
    }

    private void VerifyUserExistances() {

    }


    private void sendUserToLogin() {
        Intent loginIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
    private void sendUserToSettings() {
        Intent settingsIntent = new Intent(DashboardActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
        finish();
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter( FragmentManager fm) {
            super(fm);

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//             Toast.makeText(getActivity().getApplicationContext(),"position "+position,Toast.LENGTH_SHORT).show();

            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            //  Toast.makeText(getActivity().getApplicationContext(),"position "+position,Toast.LENGTH_SHORT).show();
            Fragment fragment = null;
            switch(position) {
                case 0: {
                    fragment = new FriendsFragment();
                    break;
                }
                case 1: {
                    fragment = new ChatsFragment();
                    break;
                }
                case 2: {
                    fragment = new ProfileFragment();
                    break;
                }

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}