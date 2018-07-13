package com.susheel.pocketparliament.ui;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.tasks.ColorUtils;

public class BillActivity extends AppCompatActivity {

    public static final String NUMBER = "number";
    public static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Bundle args = getIntent().getExtras();
        setTitle("Bill "+args.getString(BillActivity.NUMBER));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int color = 0;
        if(args.getString(BillActivity.NUMBER).startsWith("S")){
            color = getResources().getColor(R.color.senate);
        } else if (args.getString(BillActivity.NUMBER).startsWith("C")) {
            color = getResources().getColor(R.color.colorPrimary);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getWindow().setStatusBarColor(ColorUtils.darken(color));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
