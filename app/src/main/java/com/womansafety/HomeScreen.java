package com.womansafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {
    
    private Button Add, Help;
    private RequestChecker requestChecker;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gpsTracker = new GpsTracker(this);
        requestChecker = new RequestChecker(this);
        if (!requestChecker.CheckingPermissionIsEnabledOrNot())
            requestChecker.RequestMultiplePermission();
        
        
        Add = findViewById(R.id.home_button_add);
        Help = findViewById(R.id.home_button_help);
        
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, Settings.class));
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendSOS();
            }
        });


    }

    private void SendSOS() {
        SharedPreferences sharedPreferences = getSharedPreferences("women_safety", Context.MODE_PRIVATE);
        String number = sharedPreferences.getString("number","");

        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLongitude();

        if (requestChecker.CheckingPermissionIsEnabledOrNot())
        {
            if (!number.isEmpty())
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, "Emergency Message\nYou're receiving this message this contact has " +
                        "listed you as an emergency contact.\n" +
                        "https://maps.google.com/?q=" + lat + "," + lon, null, null);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));//change the number
                startActivity(callIntent);
            }
            else
                Toast.makeText(this, "Please save your emergency number first.", Toast.LENGTH_SHORT).show();
        }
        else
            requestChecker.RequestMultiplePermission();
    }
}