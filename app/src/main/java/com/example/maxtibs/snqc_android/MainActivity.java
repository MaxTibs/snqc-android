package com.example.maxtibs.snqc_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.maxtibs.snqc_android.Videos.Video;
import com.example.maxtibs.snqc_android.utilities.FirebaseUtility;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Video> videoArrayList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * Views to render when clicking bottom navigation item
         * @param item
         * @return
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new DashboardFragment()).commit();
                    return true;
                case R.id.navigation_video:
                    // Create a bundle to pass the videos to the fragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("videoList", videoArrayList);
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, videoFragment).commit();
                    return true;
                case R.id.navigation_toolkits:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new ToolkitFragment()).commit();
                    return true;
                /*case R.id.navigation_test:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new TestFragment()).commit();
                    return true;
                case R.id.navigation_debug:
                    getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new DebugFragment()).commit();
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the Firebase utility
        FirebaseUtility.initializeAnalytics(this);

        setContentView(R.layout.activity_main);

        // Retrieve the videos from the loadingActivity
        Bundle b = getIntent().getExtras();
        if(b != null)
            videoArrayList = b.getParcelableArrayList("videoList");

        //Create bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }
}