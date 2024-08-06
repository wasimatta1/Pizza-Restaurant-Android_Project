package com.example.advancepizza.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advancepizza.R;

public class loginAndRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_and_registration);
        Button singIn = (Button) findViewById(R.id.signIn);
        Button singUp = (Button) findViewById(R.id.signUp);

        // button action to sign in page
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginAndRegistrationActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        // button action to sign Up page
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginAndRegistrationActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}