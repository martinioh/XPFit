package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
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

public class ViewUsersProfile extends AppCompatActivity {
    private String userbeingViewed;
    private TextView displayinformation, usersprofiletitle;
    private String ubvfname;
    private int ubvtotalxp,ubvcardioxp,ubvstrengthxp, ubvlevel;
    private int liutotalxp;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userclickedReference, userloggedinReference;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users_profile);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            userbeingViewed = bundle.getString("userID");
            userbeingViewed = userbeingViewed.substring(userbeingViewed.length()-28);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            displayinformation = findViewById(R.id.displayinformation);
            usersprofiletitle = findViewById(R.id.usersprofiletitle);
            compareUsers();

        }
    }


    private void compareUsers(){
        if (!(userbeingViewed.equals(firebaseUser.getUid()))){
            getloggedinUser();
            getuserbeingviewed();


        } else {
            Intent intent = new Intent(ViewUsersProfile.this,ProfileActivity.class);
            startActivity(intent);
        }
    }

    public void getuserbeingviewed(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        userclickedReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userbeingViewed);

        userclickedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ubvtotalxp = dataSnapshot.child("xpscore").getValue(Integer.class);
                ubvcardioxp = dataSnapshot.child("cardioxp").getValue(Integer.class);
                ubvfname = dataSnapshot.child("fname").getValue().toString();
                ubvlevel = dataSnapshot.child("level").getValue(Integer.class);

                displayinformation.setText(("Name: " + ubvfname + "\n" + "Their total XP is " + ubvtotalxp + "XP." + "\n" +
                        "This is made up of " + ubvstrengthxp + "XP in strength and " + ubvcardioxp + "XP in cardio. " +  "\n" + ubvfname +" is level " + ubvlevel + ". Your difference in total xp is " +
                        + (liutotalxp-ubvtotalxp) + "." ));

               usersprofiletitle.setText( ubvfname + "'s Profile");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void getloggedinUser(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        userloggedinReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        userloggedinReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                liutotalxp = dataSnapshot.child("xpscore").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



}
