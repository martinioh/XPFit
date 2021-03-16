package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPastStrengthActivity extends AppCompatActivity {

    private Button btnBack;
    private ListView listview;
    private FirebaseDatabase database;
    private Query reference;
    private ArrayList<String> arraylist;
    private ArrayAdapter<String> adapter;
    private CardioWorkout cardioWorkout;
    private StrengthWorkout strengthWorkout;
    private FirebaseUser user;
    private TextView errorMessage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_strength);

        errorMessage = findViewById(R.id.errormessage2);
        errorMessage.setVisibility(View.INVISIBLE);
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClicked();

            }
        });

        listview = (ListView) findViewById(R.id.viewlast5strengthworkouts);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        reference = database.getReference("StrengthWorkouts").child(user.getUid());
        arraylist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.workout, R.id.workoutinfo, arraylist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    strengthWorkout = ds.getValue(StrengthWorkout.class);
                    arraylist.add("Workout Name: " + strengthWorkout.getWorkoutname() + ". Type of Lift:  " + strengthWorkout.typeoflift + ". Weight Lifted: " +
                            strengthWorkout.getWeight() + ". Number of Reps: " + strengthWorkout.getReps() + ". XP Gained: " + strengthWorkout.getXpscore());
                }
                if (arraylist.size() >= 1){
                    listview.setAdapter(adapter);
                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void backClicked() {
        Intent intent = new Intent(ViewPastStrengthActivity.this, ViewPastWorkoutActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent(ViewPastStrengthActivity.this, ViewPastWorkoutActivity.class);
        startActivity(intent);
        finish();
    }

}