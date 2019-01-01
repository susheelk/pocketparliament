package tech.susheelkona.pocketparliament.android.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.susheelkona.pocketparliament.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
