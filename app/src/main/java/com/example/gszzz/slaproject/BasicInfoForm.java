package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class BasicInfoForm extends AppCompatActivity {

    String surveyName;
    private MapView mapView;
    EditText type_othersEditText, vacancy_othersEditText;
    //Test
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
        Intent intent = new Intent(this, LevelsForm.class);
        intent.putExtra("surveyName", surveyName);
        startActivity(intent);
    }

    public void prevStepOnClicked(View view) {
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
