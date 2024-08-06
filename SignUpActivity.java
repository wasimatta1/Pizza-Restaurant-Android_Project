package com.example.advancepizza.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.R;
import com.example.advancepizza.obects.User;


public class SignUpActivity extends AppCompatActivity {


    boolean isValid ;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String gender;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        //connect ot data base
        DataBaseHelper dataBaseHelper =new DataBaseHelper(SignUpActivity.this, MainActivity.DBName,null,1);

        //add spinner options
        String[] options = { "Male", "Female" };
        final Spinner genderSpinner =(Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);


        //define all view on activity
        TextView signin= findViewById(R.id.signupText);
        TextView emailTextView = findViewById(R.id.email);
        TextView firstNameTextView = findViewById(R.id.firstName);
        TextView lastNameTextView = findViewById(R.id.lastName);
        TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
        TextView passwordTextView = findViewById(R.id.password);
        TextView confirmPasswordTextView = findViewById(R.id.confirmPassword);
        Button sign = (Button) findViewById(R.id.signUpButton);






        emailTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = emailTextView.getText().toString().trim();
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailTextView.setError("Invalid email address");
                }else {
                    emailTextView.setError(null);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        firstNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = firstNameTextView.getText().toString();
                if (firstName.length() < 3) {
                    firstNameTextView.setError("First name must be at least 3 characters");
                }else {
                    firstNameTextView.setError(null);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = lastNameTextView.getText().toString();
                if (lastName.length() < 3) {
                    lastNameTextView.setError("last name must be at least 3 characters");
                }else {
                    lastNameTextView.setError(null);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        phoneNumberTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumber = phoneNumberTextView.getText().toString();
                if (!phoneNumber.matches("^05[0-9]{8}$")) {
                    phoneNumberTextView.setError("Phone number must be 10 digits starting with '05'");
                }else{
                    phoneNumberTextView.setError(null);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = passwordTextView.getText().toString();
                if (password.length() < 8 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*")) {
                    passwordTextView.setError("Password must be at least 8 characters long and contain at least one letter and one number");
                }else{
                    passwordTextView.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPasswordTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPassword = confirmPasswordTextView.getText().toString().trim();
                password = passwordTextView.getText().toString().trim();
                if (!password.equals(confirmPassword)) {
                    confirmPasswordTextView.setError("Passwords do not match");
                }else {
                    confirmPasswordTextView.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        // create new user and check validation
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isValid =true;
                email = emailTextView.getText().toString();
                firstName = firstNameTextView.getText().toString();
                lastName = lastNameTextView.getText().toString();
                phoneNumber = phoneNumberTextView.getText().toString();
                gender = genderSpinner.getSelectedItem().toString();
                password = passwordTextView.getText().toString();
                confirmPassword = confirmPasswordTextView.getText().toString();


                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    isValid = false;
                    emailTextView.setError("Invalid email address");

                }


                if (!phoneNumber.matches("^05[0-9]{8}$")) {
                    isValid = false;
                    phoneNumberTextView.setError("Phone number must be 10 digits starting with '05'");

                }


                if (firstName.length() < 3 || lastName.length() < 3) {
                    isValid = false;
                    firstNameTextView.setError("First name must be at least 3 characters");
                    lastNameTextView.setError("last name must be at least 3 characters");


                }


                if (password.length() < 8 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*")) {
                    isValid = false;
                    passwordTextView.setError("Password must be at least 8 characters long and contain at least one letter and one number");

                }


                if (!password.equals(confirmPassword)) {
                    isValid = false;
                    confirmPasswordTextView.setError("Passwords do not match");
                }

                if (isValid) {
                    // Move to the login page or perform further actions for successful registration
                    // Example: Start a new activity

                    boolean checkEmail = dataBaseHelper.checkUserEmail(email);
                    if(checkEmail){
                        emailTextView.setError("This email is already in use");
                        return;
                    }

                    User user = new User(email,firstName,lastName,phoneNumber,MainActivity.hashPassword(password),"Customer",gender);
                    dataBaseHelper.insertUser(user);

                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);

                } else {
                    // Show error message to the user
                    return;
                }
            }
        });





            // if have account go to log in page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}