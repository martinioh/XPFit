package com.example.xpfit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegistration;
    private EditText enterEmail, enterPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //sets the layout as the login page

        mAuth = FirebaseAuth.getInstance();

        enterEmail = findViewById(R.id.email); //initialise page attributes as variables to manipulate
        enterPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnRegistration = findViewById(R.id.register);

        btnLogin.setOnClickListener(new View.OnClickListener() { //when login button is clicked
            @Override
            public void onClick(View v) {
                processLogin(); //run process login method
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() { //when login button is clicked
            @Override
            public void onClick(View v) {
                pressRegistration(); // links to the method that opens registration
            }
        });
    }

    private void pressRegistration(){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
    }

    private void processLogin(){
        String email, password;

        email = enterEmail.getText().toString();
        password = enterPassword.getText().toString();

        if (email.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter an Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password) //this method is built in and signs the user in with their email and password - checks in firebase db
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //when this is complete
                        if (task.isSuccessful()) { //if the user exists then give a welcome message and link to dashboard, if not then display error message
                            Toast.makeText(getApplicationContext(), "Welcome to XPFIT.", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login Unsuccessful. Please check your credentials and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}