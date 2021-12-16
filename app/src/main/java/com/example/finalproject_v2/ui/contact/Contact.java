package com.example.finalproject_v2.ui.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject_v2.R;

public class Contact extends Fragment {
    private Button reachByPhone;
    private Button reachByMail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        reachByMail = v.findViewById(R.id.reach_mail);
        reachByMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                // Fill it with Data
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                // Send it off to the Activity-Chooser
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        reachByPhone = v.findViewById(R.id.reach_phone);
        reachByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+R.string.reach_out_phone_number));
                startActivity(callIntent);
            }
        });
        return v;
    }
}
