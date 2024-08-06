package com.example.advancepizza.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.advancepizza.R;


public class ContactFragment extends Fragment {

    Button call;
    Button email;

    Button map;

    public static ContactFragment newInstance() {
        ContactFragment contactFragment = new ContactFragment();
        return contactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_contact, container, false);


         call =(Button) view.findViewById(R.id.button_call);
         email =(Button) view.findViewById(R.id.button_email);
         map =(Button) view.findViewById(R.id.button_map);




        call .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dailIntent =new Intent();
                dailIntent.setAction(Intent.ACTION_DIAL);
                dailIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(dailIntent);
            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent=new Intent();
                mapIntent.setAction(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:31.961013, 35.190483"));
                startActivity(mapIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gmailIntent = new Intent(Intent.ACTION_SENDTO);
                gmailIntent.setData(Uri.parse("mailto:AdvancePizza@Pizza.com"));
                gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Order");
                gmailIntent.putExtra(Intent.EXTRA_TEXT, "Best Pizza");
                startActivity(gmailIntent);

            }
        });





        return view;
    }
}