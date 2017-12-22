package com.susheel.pocketparliament.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.FilterParameters;

public class MemberParliamentActivity extends AppCompatActivity {

    // Views
    private TextView mpName;

    private MemberParliament memberParliament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_parliament);
        bindViews();
        Bundle arguments = getIntent().getExtras();
        getData(arguments.getString(FilterParameters.URL));
    }

    private void bindViews() {
        mpName = (TextView) findViewById(R.id.mp_name);
    }

    private void updateData() {

    }

    private void getData(String url) {
        mpName.setText(url);
    }

}
