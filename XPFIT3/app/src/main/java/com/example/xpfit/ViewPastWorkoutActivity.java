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


public class ViewPastWorkoutActivity extends AppCompatActivity {

    private Button back,cardio,strength;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_workout);

        back = findViewById(R.id.back);
        cardio = findViewById(R.id.cardio);
        strength = findViewById(R.id.strength);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPastWorkoutActivity.this, FitnessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPastWorkoutActivity.this, ViewPastCardioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPastWorkoutActivity.this, ViewPastStrengthActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(ViewPastWorkoutActivity.this, FitnessActivity.class);
        startActivity(intent);
        finish();
    }


}
