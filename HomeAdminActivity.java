package com.example.advancepizza.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.advancepizza.R;
import com.example.advancepizza.fragment.*;
import com.google.android.material.navigation.NavigationView;

public class HomeAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    ProfileFragment profileFragment;

    AddAdminFragment addAdminFragment;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_admin);


        Toolbar toolbar =  findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView name = headerView.findViewById(R.id.eamil_header);
        TextView email = headerView.findViewById(R.id.userName_header);


        profileImage = headerView.findViewById((R.id.headerImage));

        if (MainActivity.user.getImage()!= null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.user.getImage(), 0, MainActivity.user.getImage().length);
            if (bitmap != null) {
                profileImage.setImageBitmap(bitmap);
            }
        }

        name.setText(MainActivity.user.getFirstName()+" "+MainActivity.user.getLastName());
        email.setText(MainActivity.user.getEmail());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            profileFragment = ProfileFragment.newInstance(MainActivity.user);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, profileFragment).commit();
            navigationView.setCheckedItem(R.id.nav_profile_admin);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_order_admin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new OrderFragment(true)).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_offers_admin){
            PizzaMenuFragment pizzaMenuFragment = new PizzaMenuFragment(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, pizzaMenuFragment).commit();
            setphoto();
        } else if (menuItem.getItemId() == R.id.nav_add_admin){

            addAdminFragment = AddAdminFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, addAdminFragment).commit();
            setphoto();

        }else if (menuItem.getItemId() == R.id.nav_profile_admin){
             profileFragment = ProfileFragment.newInstance(MainActivity.user);
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, profileFragment).commit();

        }else if (menuItem.getItemId() == R.id.nav_logout_admin){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }else if (menuItem.getItemId() == R.id.nav_report_admin){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new ReportSalesFragment()).commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void  setphoto(){
        byte[] imageData = MainActivity.user.getImage();
        if (imageData != null && imageData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            if (bitmap != null) {
                profileImage.setImageBitmap(bitmap);
            }
        }
    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}