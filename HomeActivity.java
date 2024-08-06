package com.example.advancepizza.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.advancepizza.R;
import com.example.advancepizza.fragment.CardFragment;
import com.example.advancepizza.fragment.ContactFragment;
import com.example.advancepizza.fragment.FavoritesFragment;
import com.example.advancepizza.fragment.HomeFragment;
import com.example.advancepizza.fragment.OffersFragment;
import com.example.advancepizza.fragment.OrderFragment;
import com.example.advancepizza.fragment.PizzaMenuFragment;
import com.example.advancepizza.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        ImageView cart = toolbar.findViewById(R.id.card_toolbar);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardFragment cardFragment = new CardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cardFragment).commit();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_home){
            HomeFragment homeFragment = HomeFragment.newInstance();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_menu){
            PizzaMenuFragment pizzaMenuFragment = PizzaMenuFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pizzaMenuFragment).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_favorites){
            FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_offers){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OffersFragment()).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_order){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrderFragment()).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_contact){
            ContactFragment contactFragment = ContactFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
            setphoto();
        }else if (menuItem.getItemId() == R.id.nav_profile){
            // Get the ProfileFragment instance
            ProfileFragment profileFragment = ProfileFragment.newInstance(MainActivity.user);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
        }else if (menuItem.getItemId() == R.id.nav_logout){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
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