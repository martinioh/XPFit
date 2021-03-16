package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ProfileActivity extends AppCompatActivity {

    private Button btnHome;
    private TextView displayuserInfo;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private DatabaseReference xpReference;
    private Query rankingReference, cardioReference, strengthReference;
    private User user;
    private int currentRanking, currentstrengthRanking, currentcardioRanking;
    private ArrayList<String> arraylist, cardioarraylist, strengtharraylist;
    private int currenttotalxp, currentstrengthxp, currentcardioxp, currentlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnHome = findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeClicked();

            }
        });

        getRanking();
       getcardioRanking();
      getstrengthRanking();
        getXP();

    }

    public void getRanking() {
        firebaseUser = getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rankingReference = firebaseDatabase.getReference("Users").orderByChild("order");
        arraylist = new ArrayList<>();
        rankingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user=ds.getValue(User.class);
                    arraylist.add(user.getUid());
                }
                currentRanking = 1 + (arraylist.indexOf(firebaseUser.getUid()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getcardioRanking(){
        firebaseUser = getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        cardioReference = firebaseDatabase.getReference("Users").orderByChild("cardioorder");
        cardioarraylist = new ArrayList<>();
        cardioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user=ds.getValue(User.class);
                    cardioarraylist.add(user.getUid());
                    //Toast.makeText(getApplicationContext(),user.getUid(),Toast.LENGTH_SHORT).show();
                }
                currentcardioRanking = 1 + (cardioarraylist.indexOf(firebaseUser.getUid()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"broken",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getstrengthRanking(){
        firebaseUser = getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        strengthReference = firebaseDatabase.getReference("Users").orderByChild("strengthorder");
        strengtharraylist = new ArrayList<>();
        strengthReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user=ds.getValue(User.class);
                    strengtharraylist.add(user.getUid());

                }
                currentstrengthRanking = 1 + (strengtharraylist.indexOf(firebaseUser.getUid()));
                //Toast.makeText(getApplicationContext(),currentstrengthRanking,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getXP(){

        FirebaseUser user = getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        xpReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        xpReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = getInstance().getCurrentUser();
                currenttotalxp = dataSnapshot.child("xpscore").getValue(Integer.class);
                currentstrengthxp = dataSnapshot.child("strengthxp").getValue(Integer.class);
                currentcardioxp = dataSnapshot.child("cardioxp").getValue(Integer.class);
                currentlevel = dataSnapshot.child("level").getValue(Integer.class);
                displayuserInfo = findViewById(R.id.displayuserinfo);
                displayuserInfo.setText("Name: " + user.getDisplayName() + "\n" + "Your total XP is " + currenttotalxp + "XP." + "\n" +
                        "This is made up of " + currentstrengthxp + "XP in strength and " + currentcardioxp + "XP in cardio. " +  "\n" + " You are level " + currentlevel + "." + "\n" +
                        "This makes you rank: " + currentRanking + " overall, rank " + currentcardioRanking + " in cardio, and rank " + currentstrengthRanking + " in strength.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void homeClicked(){
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}
