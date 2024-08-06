package com.example.advancepizza.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.R;
import com.example.advancepizza.obects.User;

public class SignInActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        // open the data bsee
        DataBaseHelper dataBaseHelper =new DataBaseHelper(SignInActivity.this, MainActivity.DBName,null,1);

        //add admin
        User user = new User("admin@gmail.com","admin","admin","0596547099",MainActivity.hashPassword("admin123"),"Admin","Male");
        if(!dataBaseHelper.checkUserEmail("admin@gmail.com")){
            dataBaseHelper.insertUser(user);
        }

        // define all view in activity
        TextView  signUp= (TextView)findViewById(R.id.signupText);
        TextView emailTextView = findViewById(R.id.username);
        TextView passwordTextView = findViewById(R.id.password);
         Button signIn = (Button) findViewById(R.id.signInButton);
        sharedPrefManager =SharedPrefManager.getInstance(this);
        CheckBox rememberMeCheckBox = findViewById(R.id.checkBoxRememberMe);


        // check if there any user in remember me  if there load this email and pass
        String check = sharedPrefManager.readString("userName","noValue");
        if(!check.equals("noValue")){
            emailTextView.setText(sharedPrefManager.readString("userName","noValue"));
            passwordTextView.setText(sharedPrefManager.readString("password","noValue"));
        }


        // check if the user data is correct or if he had account
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String hashedPass=MainActivity.hashPassword(password);

                boolean checkUSer =dataBaseHelper.checkUser(email,hashedPass);

                if (checkUSer){
                    if(rememberMeCheckBox.isChecked()){
                        sharedPrefManager.writeString("userName",email);
                        sharedPrefManager.writeString("password",password);

                    }
                    MainActivity.user = dataBaseHelper.getUserByEmail(email);
                    MainActivity.user.setImage(dataBaseHelper.getProfilePhotoByEmail(MainActivity.user.getEmail()));
                    if(MainActivity.user.getAuthorities().equals("Customer")){
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(SignInActivity.this, HomeAdminActivity.class);
                        startActivity(intent);
                    }

                }else {
                    Toast.makeText(SignInActivity.this,"Email or Password Incorrect ", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });




    }
}