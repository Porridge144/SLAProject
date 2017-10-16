package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BuildingSelectionForm extends AppCompatActivity {

    String surveyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_selection_form);

        surveyName = getIntent().getStringExtra("surveyName");
    }


    public void nextStepOnClicked(View view) {
        Intent intent = new Intent(this, PreInfoForm.class);
        intent.putExtra("surveyName", surveyName);
        startActivity(intent);
    }
}
