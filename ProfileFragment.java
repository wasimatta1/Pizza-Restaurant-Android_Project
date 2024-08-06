package com.example.advancepizza.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advancepizza.Activity.MainActivity;
import com.example.advancepizza.R;
import com.example.advancepizza.dataBase.DataBaseHelper;
import com.example.advancepizza.obects.PizzaMenu;
import com.example.advancepizza.obects.User;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private TextView emailTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView phoneNumberTextView;

    private Button commit;
    private Button editPassword;
    private TextView passwordTextView;
    private TextView confirmPasswordTextView;
    private TextView oldPasswordTextView;
    private ImageButton ImageButton;

    private ImageView profileImage;
    private LinearLayout parentLinear;
    private LinearLayout passOld;
    private LinearLayout passNew;
    private LinearLayout passNew2;
    boolean isValid ;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;

    String password;
    String confirmPassword;

    String oldpassword;

    private DataBaseHelper dataBaseHelper;
    boolean enableChange=false;

    ActivityResultLauncher<Intent> resultLauncher;



    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("email", user.getEmail());
        args.putString("firstName", user.getFirstName());
        args.putString("lastName", user.getLastName());
        args.putString("phone", user.getPhone());


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), MainActivity.DBName, null, 1);

        emailTextView = view.findViewById(R.id.editEmail);
        firstNameTextView = view.findViewById(R.id.editFirstName);
        lastNameTextView = view.findViewById(R.id.editLastName);
        phoneNumberTextView = view.findViewById(R.id.editPhoneNumber);
        commit = view.findViewById(R.id.editProfile);
        passwordTextView = view.findViewById(R.id.ediPassword);
        confirmPasswordTextView = view.findViewById(R.id.editConfirmPassword);
        oldPasswordTextView = view.findViewById(R.id.oldPassword);
        ImageButton = view.findViewById(R.id.changeImage);
        profileImage = view.findViewById(R.id.profileImage);

        passOld= view.findViewById(R.id.passOld);
        passNew= view.findViewById(R.id.passNew);
        passNew2= view.findViewById(R.id.passNew2);
        editPassword  = view.findViewById(R.id.editPassword);
        parentLinear = view.findViewById(R.id.parentLinear);

        byte[] imageData = MainActivity.user.getImage();

        if (imageData != null && imageData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            if (bitmap != null) {
                profileImage.setImageBitmap(bitmap);
            }
        }
            registerResult();
        if (getArguments() != null) {
            emailTextView.setText(getArguments().getString("email"));
            firstNameTextView.setText(getArguments().getString("firstName"));
            lastNameTextView.setText(getArguments().getString("lastName"));
            phoneNumberTextView.setText(getArguments().getString("phone"));
        }

        ImageButton.setOnClickListener(view1 -> pickImage());
        parentLinear.removeView(passOld);
        parentLinear.removeView(passNew);
        parentLinear.removeView(passNew2);
        parentLinear.removeView(editPassword);


        // create new user and check validation
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enableChange){
                    isValid = true;
                    email = emailTextView.getText().toString();
                    firstName = firstNameTextView.getText().toString();
                    lastName = lastNameTextView.getText().toString();
                    phoneNumber = phoneNumberTextView.getText().toString();
                    password = passwordTextView.getText().toString();
                    confirmPassword = confirmPasswordTextView.getText().toString();
                    oldpassword = oldPasswordTextView.getText().toString();

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        isValid = false;
                        emailTextView.setError("Invalid email address");

                    } else {
                        emailTextView.setError(null);

                    }


                    if (!phoneNumber.matches("^05[0-9]{8}$")) {
                        isValid = false;
                        phoneNumberTextView.setError("Phone number must be 10 digits starting with '05'");

                    } else {
                        phoneNumberTextView.setError(null);

                    }


                    if (firstName.length() < 3) {
                        isValid = false;
                        firstNameTextView.setError("First name must be at least 3 characters");

                    } else {
                        firstNameTextView.setError(null);

                    }

                    if (lastName.length() < 3) {
                        isValid = false;
                        lastNameTextView.setError("last name must be at least 3 characters");

                    } else {
                        lastNameTextView.setError(null);

                    }
                    if (!passwordTextView.getText().toString().isEmpty() && (password.length() < 8 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*"))) {
                        isValid = false;
                        passwordTextView.setError("Password must be at least 8 characters long and contain at least one letter and one number");

                    } else {

                        passwordTextView.setError(null);

                    }


                    if (!password.equals(confirmPassword)) {
                        isValid = false;
                        confirmPasswordTextView.setError("Passwords do not match");

                    } else {
                        confirmPasswordTextView.setError(null);
                    }
                    if (!MainActivity.hashPassword(oldpassword).equals(MainActivity.user.getPassword())) {
                        isValid = false;
                        oldPasswordTextView.setError("Passwords incorect");
                    }

                    if (isValid) {
                        // Move to the login page or perform further actions for successful registration
                        // Example: Start a new activity
                        if (!firstName.equals(MainActivity.user.getFirstName())) {
                            dataBaseHelper.updateUserFirstName(email, firstName);
                            MainActivity.user.setFirstName(firstName);
                        }
                        if (!lastName.equals(MainActivity.user.getLastName())) {
                            dataBaseHelper.updateUserLastName(email, lastName);
                            MainActivity.user.setLastName(lastName);
                        }
                        if (!phoneNumber.equals(MainActivity.user.getPhone())) {
                            dataBaseHelper.updateUserPhoneNumber(email, phoneNumber);
                            MainActivity.user.setPhone(phoneNumber);
                        }
                        if (!password.isEmpty()) {
                            dataBaseHelper.updateUserPassword(email, MainActivity.hashPassword(password));
                            MainActivity.user.setPassword(MainActivity.hashPassword(password));
                        }
                        saveTheImage(profileImage);
                        dataBaseHelper.updateProfilePhotoByEmail(MainActivity.user.getEmail(),MainActivity.user.getImage());
                        passwordTextView.setText(null);
                        oldPasswordTextView.setText(null);
                        confirmPasswordTextView.setText(null);
                        Toast.makeText(getActivity(), "User Data Updated Successfully", Toast.LENGTH_LONG).show();

                    }
                }else {
                    enableChange=true;
                    commit.setText("Commit");
                    parentLinear.addView(passOld,7);
                    phoneNumberTextView.setEnabled(true);
                    firstNameTextView.setEnabled(true);
                    lastNameTextView.setEnabled(true);
                    parentLinear.addView(editPassword,8);
                }

            }
        });
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLinear.removeView(editPassword);
                parentLinear.addView(passNew,8);
                parentLinear.addView(passNew2,9);
            }
        });

        return view;
    }
    private void pickImage () {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }
    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();

                         //   String im = "https://www.pngarts.com/files/3/Pizza-Download-PNG-Image.png";
                         profileImage.setImageURI(imageUri);
                         //   Glide.with(getActivity()).load(im).into(profileImage);

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void saveTheImage(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        MainActivity.user.setImage(byteArrayOutputStream.toByteArray());
    }


}