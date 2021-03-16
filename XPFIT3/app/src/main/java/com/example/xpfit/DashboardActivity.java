package com.example.xpfit;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {

    private Button btnLeaderboard, btnfitnessActivities, btnmyProfile;
    private Button btnlogOut;
    private TextView welcomeUser;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        welcomeUser = findViewById(R.id.welcomeuser);
        btnLeaderboard = findViewById(R.id.leaderboard);
        btnfitnessActivities = findViewById(R.id.fitnessactivities);
        btnmyProfile = findViewById(R.id.myprofile);
        btnlogOut = findViewById(R.id.logout);

        if (user != null) {

        } else {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        welcomeUser.setText("Welcome, " + user.getDisplayName());

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderboardClicked();
            }
        });

        btnfitnessActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessactivitiesClicked();
            }
        });

        btnmyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myprofileClicked();
            }
        });

        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    private void leaderboardClicked(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getUid());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LeaderboardViewed");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "overallleaderboard");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    private void fitnessactivitiesClicked(){
        Intent intent = new Intent(DashboardActivity.this, FitnessActivity.class);
        startActivity(intent);
    }

    private void myprofileClicked(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getUid());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MyProfileViewed");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "myprofile");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Intent intent = new Intent(DashboardActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        // empty so nothing happens
    }

}