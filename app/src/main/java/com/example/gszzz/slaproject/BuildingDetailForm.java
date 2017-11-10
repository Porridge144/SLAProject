package com.example.gszzz.slaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class BuildingDetailForm extends AppCompatActivity {


    ListView structuralListView, architecturalListView, auxiliaryListView, rootListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_building_detail_form);


        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        String currentLevelName = sharedPreferences.getString("currentLevelName", "");

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();



        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.Tab1);
        spec.setIndicator("Structural");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.Tab2);
        spec.setIndicator("Architectural");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.Tab3);
        spec.setIndicator("Auxiliary");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.Tab4);
        spec.setIndicator("Roof");
        host.addTab(spec);

        if (currentLevelName.equals("Roof")) {
            host.setCurrentTab(3);
            host.getTabWidget().getChildTabViewAt(0).setVisibility(View.GONE);
            host.getTabWidget().getChildTabViewAt(1).setVisibility(View.GONE);

        } else {
            host.getTabWidget().getChildTabViewAt(3).setVisibility(View.GONE);
        }

        if (!currentLevelName.equals("North Elevation") &&
                !currentLevelName.equals("South Elevation") &&
                !currentLevelName.equals("West Elevation") &&
                !currentLevelName.equals("East Elevation")) {
            host.getTabWidget().getChildTabViewAt(2).setVisibility(View.GONE);
        }

        structuralListView = (ListView) findViewById(R.id.structuralListView);
        architecturalListView = (ListView) findViewById(R.id.architecturalListView);
        auxiliaryListView = (ListView) findViewById(R.id.auxiliaryListView);
        rootListView = (ListView) findViewById(R.id.roofListView);

        structuralListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });
        architecturalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });
        auxiliaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });

        rootListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });


    }

    public void backButtonOnCLicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        String currentLevelName = sharedPreferences.getString("currentLevelName", "");
        if (!currentLevelName.equals("EastElevation") && !currentLevelName.equals("WestElevation")
                && !currentLevelName.equals("NorthElevation") && !currentLevelName.equals("SouthElevation")) {
//            Intent intent = new Intent(this, LevelPlan.class);
//            startActivity(intent);
            finish();
        } else {
//            Intent intent = new Intent(this, LevelsForm.class);
//            startActivity(intent);
            finish();
        }

    }
}
