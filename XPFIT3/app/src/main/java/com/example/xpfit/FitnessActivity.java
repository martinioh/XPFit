package com.example.xpfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class FitnessActivity extends AppCompatActivity {

    private Button btnHome, btnlogWorkouts, btnviewpastWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        btnHome = findViewById(R.id.home);
        btnlogWorkouts = findViewById(R.id.logworkout);
        btnviewpastWorkouts = findViewById(R.id.viewpastworkouts);




        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitnessActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnlogWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitnessActivity.this, LogWorkoutActivity.class);
                startActivity(intent);
                finish();
            }
        });

       btnviewpastWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitnessActivity.this, ViewPastWorkoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void onBackPressed() {
        Intent intent = new Intent(FitnessActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
