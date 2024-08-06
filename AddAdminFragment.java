package com.example.advancepizza.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.Activity.SignInActivity;
import com.example.advancepizza.Activity.SignUpActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.User;


public class AddAdminFragment extends Fragment {

    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView phoneNumberTextView;

    private Button signUp;
    private TextView passwordTextView;
    private TextView confirmPasswordTextView;
    private TextView oldPasswordTextView;

    boolean isValid ;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String gender;
    String password;
    String confirmPassword;

    public static AddAdminFragment newInstance() {
        AddAdminFragment addAdminFragment = new AddAdminFragment();
        return addAdminFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_admin, container, false);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);

        String[] options = { "Male", "Female" };
        final Spinner genderSpinner =(Spinner) view.findViewById(R.id.addAdminGenderSpinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);

        emailTextView = view.findViewById(R.id.addAdminEmail);
        firstNameTextView = view.findViewById(R.id.addAdminFirstName);
        lastNameTextView = view.findViewById(R.id.addAdminLastName);
        phoneNumberTextView = view.findViewById(R.id.addAdminPhoneNumber);
        signUp = view.findViewById(R.id.addAdminSignUpButton);
        passwordTextView = view.findViewById(R.id.addAdminPassword);
        confirmPasswordTextView = view.findViewById(R.id.addAdminConfirmPassword);

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
        signUp.setOnClickListener(new View.OnClickListener() {
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

                    User user = new User(email,firstName,lastName,phoneNumber,MainActivity.hashPassword(password),"Admin",gender);
                    dataBaseHelper.insertUser(user);

                    Toast.makeText(getActivity(),"Admin Added Successfully", Toast.LENGTH_LONG).show();

                } else {
                    // Show error message to the user
                    return;
                }
            }
        });









        return view;
    }
}