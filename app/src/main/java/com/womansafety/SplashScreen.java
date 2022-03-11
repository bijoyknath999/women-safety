package com.womansafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SplashScreen.this == null){
                    return;
                }
                Intent Intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity( Intent );
                finish();
            }
        },2000);
    }
}