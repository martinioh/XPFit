package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText enterEmail, enterPassword, enterfName;
    private Button btnRegistration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        enterEmail = findViewById(R.id.email); //initialise page attributes as variables to mani[ulate
        enterPassword = findViewById(R.id.password);
        enterfName = findViewById(R.id.fullname);
        btnRegistration = findViewById(R.id.register);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRegistration();
            }
        });
    }

    private void processRegistration() {
        final String email, password, fname;

        email = enterEmail.getText().toString();
        password = enterPassword.getText().toString();
        fname = enterfName.getText().toString();

        if (email.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter an Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fname.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Full Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(fname.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$"))){
            Toast.makeText(getApplicationContext(), "Please enter a correct Full Name!", Toast.LENGTH_SHORT).show();
            return;
        }




        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fname)
                                    .build();
                            user.updateProfile(profileUpdate);
                            Toast.makeText(getApplicationContext(), "You have registered. Please Log In!!", Toast.LENGTH_LONG).show();

                            User registeredUser = new User(user.getUid(),fname,0,0,0,1, 99999999,99999999,99999999); //adds user to a database under the category 'Users'
                            databaseReference.child("Users").child(user.getUid()).setValue(registeredUser);

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please check all fields and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

}