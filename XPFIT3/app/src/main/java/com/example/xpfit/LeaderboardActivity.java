package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private Button btnHome, btnOverall, btnStrength, btnCardio;
    private ListView listview;
    private FirebaseDatabase database;
    private Query reference;
    private ArrayList<String> arraylist, userid;
    private ArrayAdapter<String> adapter;
    private User user;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        user = new User();

        btnHome = findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeClicked();

            }
        });

        getOverall();

        btnOverall = findViewById(R.id.overall);
        btnOverall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getOverall();

            }
        });

        btnStrength = findViewById(R.id.strength);
        btnStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStrength();

            }
        });

        btnCardio = findViewById(R.id.cardio);
        btnCardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardio();

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, firebaseUser.getUid());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "OtherUserViewed");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "otheruser");
                Intent intent = new Intent(LeaderboardActivity.this,ViewUsersProfile.class);
                intent.putExtra("userID",userid.get(position));
                startActivity(intent);
            }
        });

    }

    public void getOverall(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, firebaseUser.getUid());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "OverallLeaderboardViewed");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "overallleaderboard");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        listview = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").orderByChild("order");
        arraylist = new ArrayList<>();
        userid = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user,R.id.userinfo, arraylist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int userRank = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userRank++;
                    user=ds.getValue(User.class);
                    String level = String.valueOf(user.getLevel());
                    String xp = String.valueOf(user.getXpscore());
                    arraylist.add("Rank " + userRank + " - Name: " + user.getFname() + ". XP Score: " + xp + "xp. Level: " + level + ".");
                    userid.add(user.getUid());
                }
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getStrength(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, firebaseUser.getUid());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "StrengthLeaderboardViewed");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "strengthleaderboard");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        listview = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").orderByChild("strengthorder");
        arraylist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user,R.id.userinfo, arraylist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int userRank = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userRank++;
                    user=ds.getValue(User.class);
                    String level = String.valueOf(user.getLevel());
                    String strengthxp = String.valueOf(user.getStrengthxp());
                    arraylist.add("Rank " + userRank + " - Name: " + user.getFname() + ". Strength XP Score: " + strengthxp + "xp.");
                    userid.add(user.getUid());
                }
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCardio(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, firebaseUser.getUid());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "CardioLeaderboardViewed");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "cardioleaderboard");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        listview = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").orderByChild("cardioorder");
        arraylist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user,R.id.userinfo, arraylist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int userRank = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userRank++;
                    user=ds.getValue(User.class);
                    String cardioxp = String.valueOf(user.getCardioxp());
                    arraylist.add("Rank " + userRank + " - Name: " + user.getFname() + ". Cardio XP Score: " + cardioxp + "xp.");
                    userid.add(user.getUid());
                }
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void homeClicked(){
        Intent intent = new Intent(LeaderboardActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(LeaderboardActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}
