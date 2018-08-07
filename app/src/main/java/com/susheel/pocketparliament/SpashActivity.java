package com.susheel.pocketparliament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.susheel.pocketparliament.ui.MainActivity;

public class SpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
