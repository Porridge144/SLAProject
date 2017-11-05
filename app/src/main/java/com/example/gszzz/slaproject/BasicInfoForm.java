package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.example.gszzz.slaproject.storage_handler.StorageHandler;

public class BasicInfoForm extends AppCompatActivity {

    String surveyName;
    private MapView mapView;
    EditText type_othersEditText, vacancy_othersEditText;

    RadioGroup type_radioGroup, vacancy_radioGroup;
    EditText yearBuiltEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_form);

        surveyName = getIntent().getStringExtra("surveyName");

        mapView = (MapView) findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 1.2915439, 103.76965, 16);
        mapView.setMap(map);

        type_othersEditText = (EditText) findViewById(R.id.type_otherEditText);
        vacancy_othersEditText = (EditText) findViewById(R.id.vacancy_otherEditText);

        type_radioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
        vacancy_radioGroup = (RadioGroup) findViewById(R.id.vacancyRadioGroup);
        yearBuiltEditText = (EditText) findViewById(R.id.yearBuiltEditText);
    }

    @Override
    protected void onPause() {
        mapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.resume();
        super.onResume();
    }

    public void nextStepOnClicked(View view) {

        if (type_radioGroup.getCheckedRadioButtonId() != -1 && vacancy_radioGroup.getCheckedRadioButtonId() != -1
                && !yearBuiltEditText.getText().toString().equals("")) {

            //Save data
            RadioButton tmpButton = (RadioButton) findViewById(type_radioGroup.getCheckedRadioButtonId());
            String typeButtonString = tmpButton.getText().toString();
            tmpButton = (RadioButton) findViewById(vacancy_radioGroup.getCheckedRadioButtonId());
            String vacancyButtonString = tmpButton.getText().toString();
            String yearBuiltString = yearBuiltEditText.getText().toString();
            String typeOtherString = type_othersEditText.getText().toString();
            String vacancyOtherString = vacancy_othersEditText.getText().toString();

            StorageHandler storageHandler = new StorageHandler();
            storageHandler.execute(getApplicationContext(), StorageHandler.DATA_WRITE, StorageHandler.PAGE_BASIC_INFO,
                    typeButtonString, vacancyButtonString, yearBuiltString, typeOtherString, vacancyOtherString);


            //Go to next page
            Intent intent = new Intent(this, LevelsForm.class);
            intent.putExtra("surveyName", surveyName);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all fields before proceed to next step...", Toast.LENGTH_SHORT).show();
        }


    }

    public void prevStepOnClicked(View view) {
//        StorageHandler storageHandler = new StorageHandler();
//        storageHandler.execute(getApplicationContext(), StorageHandler.DATA_READ, StorageHandler.PAGE_BASIC_INFO);
    }


    public void typeRadioGroupOnClicked(View view) {
        if (view.getId() == R.id.type_otherRadionButton) {
            type_othersEditText.setVisibility(View.VISIBLE);
        } else {
            type_othersEditText.setVisibility(View.GONE);
        }
    }

    public void vacancyRadioGroupOnClicked(View view) {
        if (view.getId() == R.id.vacancy_otherRadionButton) {
            vacancy_othersEditText.setVisibility(View.VISIBLE);
        } else {
            vacancy_othersEditText.setVisibility(View.GONE);
        }
    }
}
