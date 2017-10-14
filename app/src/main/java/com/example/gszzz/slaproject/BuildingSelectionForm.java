package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BuildingSelectionForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_selection_form);

    }


    public void nextStepOnClicked(View view) {
        Intent intent = new Intent(this, PreInfoForm.class);
        startActivity(intent);
    }
}
