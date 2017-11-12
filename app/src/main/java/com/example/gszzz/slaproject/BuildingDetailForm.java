package com.example.gszzz.slaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class BuildingDetailForm extends AppCompatActivity {


    ListView structuralListView, architecturalListView, auxiliaryListView, roofListView;
    private ArrayList<String> structuralList, architecturalList, auxiliaryList, roofList;
    private ArrayAdapter<String> structuralAdapter, architecturalAdapter, auxiliaryAdapter, roofAdapter;

    private static int count = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_building_detail_form);


        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor= sharedPreferences.edit();
        final String currentLevelName = sharedPreferences.getString("currentLevelName", "");
        final String roomLabelString = sharedPreferences.getString("roomLabelString", "");

        String levelRoomElementNames = sharedPreferences.getString(currentLevelName + "_" + roomLabelString + "_" + "elementNames", "");



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

        structuralList = new ArrayList<>();
        architecturalList = new ArrayList<>();
        auxiliaryList = new ArrayList<>();
        roofList = new ArrayList<>();

        for (String tmpString : getResources().getStringArray(R.array.structuralElementsArray)) {
            structuralList.add(tmpString);
        }
        for (String tmpString : getResources().getStringArray(R.array.architecturalElementsArray)) {
            architecturalList.add(tmpString);
        }
        for (String tmpString : getResources().getStringArray(R.array.auxiliaryElementsArray)) {
            auxiliaryList.add(tmpString);
        }
        for (String tmpString : getResources().getStringArray(R.array.roofElementsArray)) {
            roofList.add(tmpString);
        }


        structuralAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, structuralList);
        architecturalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, architecturalList);
        auxiliaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, auxiliaryList);
        roofAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roofList);

        structuralListView.setAdapter(structuralAdapter);
        architecturalListView.setAdapter(architecturalAdapter);
        auxiliaryListView.setAdapter(auxiliaryAdapter);
        roofListView.setAdapter(roofAdapter);


        structuralListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) view).setTextColor(Color.RED);

                String itemName = String.valueOf(parent.getItemAtPosition(position));

                //Save element names for current level
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + roomLabelString + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + roomLabelString + "_"+ "elementNames", levelElementNames);
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
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + roomLabelString + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + roomLabelString + "_"+ "elementNames", levelElementNames);
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
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + roomLabelString + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + roomLabelString + "_"+ "elementNames", levelElementNames);
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
                String levelElementNames = sharedPreferences.getString(currentLevelName + "_" + roomLabelString + "_" + "elementNames", "");
                levelElementNames += itemName + ":";
                editor.putString(currentLevelName + "_" + roomLabelString + "_"+ "elementNames", levelElementNames);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("elementName", itemName);
                startActivity(intent);
            }
        });



        try {
            if (!levelRoomElementNames.equals("")) {
                String[] elementsNames = levelRoomElementNames.split(":");



                String indexString = "";
                final String indexStructuralStringFinal;
                for (String elementName : elementsNames) {
                    String[] tmpElementNames = getResources().getStringArray(R.array.structuralElementsArray);

                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            indexString += String.valueOf(i) + ":";
                        }
                    }
                }
                indexStructuralStringFinal = indexString;
                structuralListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, structuralList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);

                        String[] splitIndexes = indexStructuralStringFinal.split(":");
                        boolean flag = false;
                        for (String splitIndex : splitIndexes) {
                            if (splitIndex.equals(String.valueOf(position))) {
                                textView.setTextColor(Color.RED);
                                if (position == 0) flag = true;
                            }
                        }
                        //Very weird....
                        if (!flag && position == 0) textView.setTextColor(Color.BLACK);

                        return textView;
                    }
                });

                indexString = "";
                final String indexArchitecturalStringFinal;
                for (String elementName : elementsNames) {
                    String[] tmpElementNames = getResources().getStringArray(R.array.architecturalElementsArray);

                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            indexString += String.valueOf(i) + ":";
                        }
                    }
                }
                indexArchitecturalStringFinal = indexString;
                architecturalListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, architecturalList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);

                        String[] splitIndexes = indexArchitecturalStringFinal.split(":");
                        boolean flag = false;
                        for (String splitIndex : splitIndexes) {
                            if (splitIndex.equals(String.valueOf(position))) {
                                textView.setTextColor(Color.RED);
                                if (position == 0) flag = true;
                            }
                        }
                        //Very weird....
                        if (!flag && position == 0) textView.setTextColor(Color.BLACK);

                        return textView;
                    }
                });

                indexString = "";
                final String indexAuxiliaryStringFinal;
                for (String elementName : elementsNames) {
                    String[] tmpElementNames = getResources().getStringArray(R.array.auxiliaryElementsArray);

                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            indexString += String.valueOf(i) + ":";
                        }
                    }
                }
                indexAuxiliaryStringFinal = indexString;
                auxiliaryListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, auxiliaryList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);

                        String[] splitIndexes = indexAuxiliaryStringFinal.split(":");
                        boolean flag = false;
                        for (String splitIndex : splitIndexes) {
                            if (splitIndex.equals(String.valueOf(position))) {
                                textView.setTextColor(Color.RED);
                                if (position == 0) flag = true;
                            }
                        }
                        //Very weird....
                        if (!flag && position == 0) textView.setTextColor(Color.BLACK);

                        return textView;
                    }
                });

                indexString = "";
                final String indexRoofStringFinal;
                for (String elementName : elementsNames) {
                    String[] tmpElementNames = getResources().getStringArray(R.array.roofElementsArray);

                    for (int i = 0; i < tmpElementNames.length; i++) {
                        if (elementName.equals(tmpElementNames[i])) {
                            indexString += String.valueOf(i) + ":";
                        }
                    }
                }
                indexRoofStringFinal = indexString;
                roofListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roofList) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);

                        String[] splitIndexes = indexRoofStringFinal.split(":");
                        boolean flag = false;
                        for (String splitIndex : splitIndexes) {
                            if (splitIndex.equals(String.valueOf(position))) {
                                textView.setTextColor(Color.RED);
                                if (position == 0) flag = true;
                            }
                        }
                        //Very weird....
                        if (!flag && position == 0) textView.setTextColor(Color.BLACK);

                        return textView;
                    }
                });

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
