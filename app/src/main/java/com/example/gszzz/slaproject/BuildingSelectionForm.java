package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gszzz.slaproject.storage_handler.StorageHandler;

import java.util.ArrayList;
import java.util.List;

public class BuildingSelectionForm extends AppCompatActivity {

    String surveyName;
    Spinner clusterSpinner, buildingNameSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_selection_form);

        surveyName = getIntent().getStringExtra("surveyName");

        //Initialize spinners
        clusterSpinner = (Spinner) findViewById(R.id.clusterSpinner);
        buildingNameSpinner = (Spinner) findViewById(R.id.buildingNameSpinner);

        ArrayAdapter<String> clusterSpinnerAdapter;
        List<String> clusterList = new ArrayList<>();
        clusterList.add("North");
        clusterList.add("South");
        clusterList.add("East");
        clusterList.add("West");
        clusterSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, clusterList);
        clusterSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clusterSpinner.setAdapter(clusterSpinnerAdapter);

        ArrayAdapter<String> buildingNameSpinnerAdapter;
        List<String> buildingNameList = new ArrayList<>();
        buildingNameList.add("Building A");
        buildingNameList.add("Building B");
        buildingNameList.add("Building C");
        buildingNameList.add("Building D");
        buildingNameSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, buildingNameList);
        buildingNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingNameSpinner.setAdapter(buildingNameSpinnerAdapter);


    }


    public void nextStepOnClicked(View view) {

        StorageHandler storageHandler = new StorageHandler();
        storageHandler.execute(getApplicationContext(), StorageHandler.DATA_WRITE, StorageHandler.PAGE_BUILDING_SELECTION_FORM,
                clusterSpinner.getSelectedItem().toString(), buildingNameSpinner.getSelectedItem().toString());


//        String[] infoStrings = storageHandler.execute(getApplicationContext(), StorageHandler.DATA_READ, StorageHandler.PAGE_BUILDING_SELECTION_FORM);
//        Toast.makeText(getApplicationContext(), infoStrings[0] + "\n" + infoStrings[1], Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PreInfoForm.class);
        intent.putExtra("surveyName", surveyName);
        startActivity(intent);
    }
}
