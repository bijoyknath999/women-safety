package com.womansafety;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.womansafety.R;

public class Settings extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppCompatButton Add, Remove;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Number Settings");


        Add = findViewById(R.id.settings_add);
        Remove = findViewById(R.id.settings_remove);
        editText = findViewById(R.id.settings_number);



        sharedPreferences = getSharedPreferences("women_safety", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String number = sharedPreferences.getString("number","");

        if (!number.isEmpty()) {
            editText.setText(number);
            Add.setText("Edit");
        }

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editText.getText().toString();
                if (number.isEmpty())
                    Toast.makeText(Settings.this, "Please enter the number!!", Toast.LENGTH_SHORT).show();
                else if (editText.length()<10)
                    Toast.makeText(Settings.this, "Please enter valid 10 digits number.", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(Settings.this, "Number saved successfully!!", Toast.LENGTH_SHORT).show();
                    editor.putString("number", number);
                    editor.commit();
                    editText.setText(number);
                }
            }
        });

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editor.clear();
               editor.apply();
               editText.setText(null);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}