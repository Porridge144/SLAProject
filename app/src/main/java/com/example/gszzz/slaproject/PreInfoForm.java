package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PreInfoForm extends AppCompatActivity {

    String surveyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_info_form);

        surveyName = getIntent().getStringExtra("surveyName");
    }

    public void prevStepOnClicked(View view) {
    }

    public void nextStepOnClicked(View view) {
        Intent intent = new Intent(this, BasicInfoForm.class);
        intent.putExtra("surveyName", surveyName);
        startActivity(intent);
    }
}
