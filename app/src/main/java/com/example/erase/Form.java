package com.example.erase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Form extends AppCompatActivity {

    EditText edt1,edt2;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edt1 = findViewById(R.id.dropbox);
        edt2 = findViewById(R.id.gadget);
        bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.putString("dropbox",edt1.getText().toString());
                editor.putString("gadget",edt2.getText().toString());
                editor.putBoolean("Activity", true);
                editor.commit();
                finish();
            }
        });
    }
}