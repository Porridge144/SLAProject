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
import android.widget.Toast;

public class BuildingDetailForm extends AppCompatActivity {


    ListView structuralListView, architecturalListView, auxiliaryListView, roofListView;
    private static boolean performedClickFlag = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_building_detail_form);


        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor= sharedPreferences.edit();
        final String currentLevelName = sharedPreferences.getString("currentLevelName", "");

        String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + "elementNames", "");



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
        roofListView = (ListView) findViewById(R.id.roofListView);



        structuralListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName = String.valueOf(parent.getItemAtPosition(position));

                //Save element names for current level
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + "elementNames", levelElementNames);
                editor.apply();

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

                //Save element names for current level
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + "elementNames", levelElementNames);
                editor.apply();

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

                //Save element names for current level
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + "elementNames", levelElementNames);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });

        roofListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);


                String itemName =String.valueOf(parent.getItemAtPosition(position));

                //Save element names for current level
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + "elementNames", levelElementNames);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });


        try {
            if (!levelElementNames.equals("")) {
                String[] elementsNames = levelElementNames.split(":");
                for (String elementName : elementsNames) {
                    String[] tmpElementNames = getResources().getStringArray(R.array.structuralElementsArray);
                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
//                            TextView tmpTextView = (TextView) structuralListView.getAdapter().getView(i, null, null);
//                            structuralListView.performItemClick(structuralListView.getChildAt(i), i, structuralListView.getItemIdAtPosition(i));

//                            String tmp = "AA";
//                            ((TextView) structuralListView.getAdapter().getView(i, null, null)).performClick();

//                            Toast.makeText(getApplicationContext(), String.valueOf(tmp), Toast.LENGTH_LONG).show();
                            performedClickFlag = true;
                            structuralListView.performItemClick(structuralListView.getAdapter().getView(i, null, null), i,
                                    structuralListView.getItemIdAtPosition(i));

                        }
                    }
                    tmpElementNames = getResources().getStringArray(R.array.architecturalElementsArray);
                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            TextView tmpTextView = (TextView) architecturalListView.getChildAt(i);
//                            tmpTextView.setTextColor(Color.RED);
//                            tmpTextView.refreshDrawableState();
                        }
                    }
                    tmpElementNames = getResources().getStringArray(R.array.auxiliaryElementsArray);
                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            TextView tmpTextView = (TextView) auxiliaryListView.getChildAt(i);
//                            tmpTextView.setTextColor(Color.RED);
//                            tmpTextView.refreshDrawableState();
                        }
                    }
                    tmpElementNames = getResources().getStringArray(R.array.roofElementsArray);
                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            TextView tmpTextView = (TextView) roofListView.getChildAt(i);
//                            tmpTextView.setTextColor(Color.RED);
//                            tmpTextView.refreshDrawableState();
                        }
                    }

                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "NullPointerException...", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }



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
