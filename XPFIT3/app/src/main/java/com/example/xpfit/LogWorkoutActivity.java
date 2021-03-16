package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogWorkoutActivity extends AppCompatActivity {

    private ToggleButton btncardioStrength;
    private Button btnSubmit, btnBack;
    private String cardioorstrength;
    private EditText inputworkoutName, inputTime, inputDistance, inputWeight, inputReps;
    private Spinner inputtypeofLift;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, userbeingUpdated;
    private FirebaseAuth mAuth;
    private int xpGained, currentcardioxp, currentstrengthxp, currenttotalxp, currentordervalue, reps, currentstrengthorder, currentcardioorder, weight;
    private FirebaseAnalytics firebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);

        mAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btncardioStrength = findViewById(R.id.cardiostrengthbutton);
        btnSubmit = findViewById(R.id.submit);
        btnBack = findViewById(R.id.back);
        inputworkoutName = findViewById(R.id.inputworkoutname);
        inputTime =  findViewById(R.id.inputtime);
        inputDistance = findViewById(R.id.inputdistance);
        inputtypeofLift = findViewById(R.id.inputtypeoflift);
        inputWeight = findViewById(R.id.inputweight);
        inputReps = findViewById(R.id.inputreps);

        inputtypeofLift.setVisibility(View.GONE);
        inputWeight.setVisibility(View.GONE);
        inputReps.setVisibility(View.GONE);
        cardioorstrength = "Cardio";


        String[] items = new String[]{"Bench Press", "Squat", "Deadlift"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        inputtypeofLift.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogWorkoutActivity.this, FitnessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btncardioStrength.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cardioorstrength = "Strength";
                    inputTime.setVisibility(View.GONE);
                    inputDistance.setVisibility(View.GONE);
                    inputtypeofLift.setVisibility(View.VISIBLE);
                    inputWeight.setVisibility(View.VISIBLE);
                    inputReps.setVisibility(View.VISIBLE);
                }
                else {
                    cardioorstrength = "Cardio";
                    inputTime.setVisibility(View.VISIBLE);
                    inputDistance.setVisibility(View.VISIBLE);
                    inputtypeofLift.setVisibility(View.GONE);
                    inputWeight.setVisibility(View.GONE);
                    inputReps.setVisibility(View.GONE);
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSubmit();
            }
        });


    }

    private void processSubmit() {
        final String workoutname, lifttype, sDistance, sTime,sReps, sWeight;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        workoutname = inputworkoutName.getText().toString();
        if (cardioorstrength == "Strength"){
            sReps = inputReps.getText().toString();
            sWeight = inputWeight.getText().toString();
            reps = Integer.parseInt(inputReps.getText().toString());
            weight = Integer.parseInt(inputWeight.getText().toString());
            lifttype = inputtypeofLift.getSelectedItem().toString();

            if (workoutname.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a workout name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sReps.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter an amount of reps!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(sReps) == 0 || Integer.parseInt(sReps) > 12){
                Toast.makeText(getApplicationContext(), "Please enter an amount of reps greater than 0 and less than 12!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sWeight.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a weight amount!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(sWeight) == 0){
                Toast.makeText(getApplicationContext(), "Please enter a weight greater than 0kg!", Toast.LENGTH_SHORT).show();
                return;
            }

            // workout xp score here

           xpGained = workoutstrengthXP(reps, weight, lifttype);

            StrengthWorkout strengthWorkout = new StrengthWorkout(user.getUid(),workoutname,"strength", lifttype, Integer.parseInt(sReps), Integer.parseInt(sWeight), xpGained);
            databaseReference.child("StrengthWorkouts").child(user.getUid()).push().setValue(strengthWorkout);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getUid());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "StrengthWorkoutLogged");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "strengthworkout");

            userbeingUpdated= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            userbeingUpdated.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     currenttotalxp= dataSnapshot.child("xpscore").getValue(Integer.class);
                     currentstrengthxp= dataSnapshot.child("strengthxp").getValue(Integer.class);
                     currentordervalue=dataSnapshot.child("order").getValue(Integer.class);
                     currentstrengthorder = dataSnapshot.child("strengthorder").getValue(Integer.class);
                     userbeingUpdated.child("xpscore").setValue(xpGained+currenttotalxp);
                     userbeingUpdated.child("strengthxp").setValue(xpGained+currentstrengthxp);
                    int level = workoutLevel(xpGained+currenttotalxp);
                    userbeingUpdated.child("level").setValue(level);
                    userbeingUpdated.child("order").setValue(currentordervalue-xpGained);
                    userbeingUpdated.child("strengthorder").setValue(currentstrengthorder-xpGained);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            Toast.makeText(getApplicationContext(), "Strength Workout Logged. Well done! You have earned "+(xpGained)+" XP for this!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LogWorkoutActivity.this, FitnessActivity.class);
            startActivity(intent);
            finish();

        } else if (cardioorstrength == "Cardio"){
            sDistance = inputDistance.getText().toString();
            sTime = inputTime.getText().toString();

            if (workoutname.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a workout name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sDistance.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a distance!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(sDistance) == 0 || Integer.parseInt(sDistance) > 26){
                Toast.makeText(getApplicationContext(), "Please enter a distance greater than 0km and less than 26km!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sTime.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter a time!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(sTime) == 0){
                Toast.makeText(getApplicationContext(), "Please enter a time greater than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            Double workoutcardioXP = workoutcardioXP();
            xpGained = workoutcardioXP.intValue();

            if (xpGained > 1000){
                xpGained = 1000;
            }

            CardioWorkout cardioWorkout = new CardioWorkout(user.getUid(),workoutname,"cardio",  Integer.parseInt(sTime), Integer.parseInt(sDistance), xpGained);
            databaseReference.child("CardioWorkouts").child(user.getUid()).push().setValue(cardioWorkout);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getUid());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "CardioWorkoutLogged");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "cardioworkout");

            userbeingUpdated= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

            userbeingUpdated.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currenttotalxp= dataSnapshot.child("xpscore").getValue(Integer.class);
                    currentcardioxp= dataSnapshot.child("cardioxp").getValue(Integer.class);
                    currentordervalue=dataSnapshot.child("order").getValue(Integer.class);
                    currentcardioorder = dataSnapshot.child("cardioorder").getValue(Integer.class);
                    userbeingUpdated.child("xpscore").setValue(xpGained+currenttotalxp);
                    userbeingUpdated.child("cardioxp").setValue(xpGained+currentcardioxp);
                    int level = workoutLevel(xpGained+currenttotalxp);
                    userbeingUpdated.child("level").setValue(level);
                    userbeingUpdated.child("order").setValue(currentordervalue-xpGained);
                    userbeingUpdated.child("cardioorder").setValue(currentcardioorder-xpGained);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

           Toast.makeText(getApplicationContext(), "Cardio Workout Logged. Well done! You have earned "+(xpGained)+" XP for this!", Toast.LENGTH_LONG).show();

           Intent intent = new Intent(LogWorkoutActivity.this, FitnessActivity.class);
           startActivity(intent);
           finish();
        }

    }

    private int workoutLevel(int currenttotalxp){
        if (currenttotalxp <= 800) {
            return 1;
        } else if (currenttotalxp > 801 && currenttotalxp <= 2000){
            return 2;
        } else if (currenttotalxp > 2001 && currenttotalxp <= 3800){
            return 3;
        } else if (currenttotalxp > 3801 && currenttotalxp <= 6200){
            return 4;
        } else if (currenttotalxp > 6201 && currenttotalxp <= 9400){
            return 5;
        } else if (currenttotalxp > 9401 && currenttotalxp <= 13600){
            return 6;
        } else if (currenttotalxp > 13601 && currenttotalxp <= 19000){
            return 7;
        } else if (currenttotalxp > 19001 && currenttotalxp <= 25800){
            return 8;
        } else {
            return 1000;
        }
    }


    private int workoutstrengthXP(int numberofReps, int weightlifted, String typeofLift) {
        int xpperrep = 0;

        if (typeofLift == "Bench Press") {
            if (weightlifted >= 1 && weightlifted <= 6) {
                xpperrep = 5;
            } else if (weightlifted >= 7 && weightlifted <= 12) {
                xpperrep = 10;
            } else if (weightlifted >= 13 && weightlifted <= 18) {
                xpperrep = 20;
            } else if (weightlifted >= 19 && weightlifted <= 24) {
                xpperrep = 30;
            } else if (weightlifted >= 25 && weightlifted <= 30) {
                xpperrep = 40;
            } else if (weightlifted >= 31 && weightlifted <= 36) {
                xpperrep = 50;
            } else if (weightlifted >= 37 && weightlifted <= 42) {
                xpperrep = 60;
            } else if (weightlifted >= 43) {
                xpperrep = 70;
            }
        } else if (typeofLift == "Squat") {
            if (weightlifted >= 1 && weightlifted <= 10) {
                xpperrep = 5;
            } else if (weightlifted >= 11 && weightlifted <= 20) {
                xpperrep = 10;
            } else if (weightlifted >= 21 && weightlifted <= 30) {
                xpperrep = 20;
            } else if (weightlifted >= 31 && weightlifted <= 40) {
                xpperrep = 30;
            } else if (weightlifted >= 41 && weightlifted <= 50) {
                xpperrep = 40;
            } else if (weightlifted >= 51 && weightlifted <= 60) {
                xpperrep = 50;
            } else if (weightlifted >= 61 && weightlifted <= 70) {
                xpperrep = 60;
            } else if (weightlifted >= 71) {
                xpperrep = 70;
            }
        } else if (typeofLift == "Deadlift") {
                if (weightlifted >= 1 && weightlifted <= 16) {
                    xpperrep = 5;
                } else if (weightlifted >= 17 && weightlifted <= 32) {
                    xpperrep = 10;
                } else if (weightlifted >= 33 && weightlifted <= 48) {
                    xpperrep = 20;
                } else if (weightlifted >= 49 && weightlifted <= 64) {
                    xpperrep = 30;
                } else if (weightlifted >= 65 && weightlifted <= 80) {
                    xpperrep = 40;
                } else if (weightlifted >= 81 && weightlifted <= 96) {
                    xpperrep = 50;
                } else if (weightlifted >= 97 && weightlifted <= 112) {
                    xpperrep = 60;
                } else if (weightlifted >= 113) {
                    xpperrep = 70;
                }

            } else {
                xpperrep = 99999;
            }

        return (xpperrep * numberofReps);
    }

    private double workoutcardioXP(){

        int Distance = Integer.parseInt((inputDistance.getText().toString()));
        int Time = Integer.parseInt((inputTime.getText().toString()));
        double doubleTime;
        double speed;
        if (Distance >0 && Time > 0){
            doubleTime = (double) Time/60; //get time in hours
            speed = (double) Distance/ doubleTime; //calculate speed
        } else {
            speed = 0;
        }

        if (Distance > 0 && Distance <=2){
            return  (speed  * 100);
        } else if ((Distance >=3 && Distance <=5)){
            return  (speed * 110);
        } else if ((Distance >=6 && Distance <=8)) {
            return  (speed * 120);
        } else if (Distance >=9){
            return  (speed * 140);
        }

        return 99999;
    }


    public void onBackPressed() {
        Intent intent = new Intent(LogWorkoutActivity.this, FitnessActivity.class);
        startActivity(intent);
        finish();
    }


}
