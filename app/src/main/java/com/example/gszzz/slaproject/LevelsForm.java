package com.example.gszzz.slaproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gszzz.slaproject.server_interaction.LoginForm;
import com.example.gszzz.slaproject.server_interaction.ServerQueryAsyncTask;
import com.example.gszzz.slaproject.storage_handler.OutputGenerator;

public class LevelsForm extends AppCompatActivity {

    String surveyName;
    ListView levelListListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_form);

        try {
            surveyName = getIntent().getStringExtra("surveyName");
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("surveyName", surveyName);
            editor.apply();
        } catch (Exception ignored) {
        }

        levelListListView = (ListView) findViewById(R.id.levelListListView);

        //Query survey for level list
        //Set up listener for survey list query result intent
        BroadcastReceiver levelListQueryResultListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Parse info here and display in list view
                String levelNameString = intent.getStringExtra("levelNameString");
                String[] levelList = levelNameString.split(":");
                final String[] levelListRefined = new String[levelList.length - 1];
                System.arraycopy(levelList, 1, levelListRefined, 0, levelList.length - 1);
                final String[] levelListRefinedNoExtension = new String[levelListRefined.length];

                //cut off the extensions
                for (int i = 0; i < levelListRefined.length; i++) {
                    int length = levelListRefined[i].length();
                    String tmp = levelListRefined[i].substring(0, length - 4);
                    levelListRefinedNoExtension[i] = tmp;
                }

                //Save level names
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                StringBuilder builder = new StringBuilder("");
                for (String levelName : levelListRefinedNoExtension) {
                    String tmp = levelName + ":";
                    builder.append(tmp);
                }
                editor.putString("levelNames", builder.toString());
                editor.apply();

                //==============================================For Testing============================================
//                Toast.makeText(getApplicationContext(),
//                        sharedPreferences.getString("levelNames", ""),
//                        Toast.LENGTH_SHORT).show();
                //=====================================================================================================

                ListAdapter listAdapter = new ArrayAdapter<>(LevelsForm.this, android.R.layout.simple_list_item_1, levelListRefinedNoExtension);
                levelListListView.setAdapter(listAdapter);

                levelListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), LevelPlan.class);
                        intent.putExtra("levelName", levelListRefinedNoExtension[i]);

                        //Save current level name for other activities' use
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("currentLevelName", levelListRefinedNoExtension[i]);
                        editor.apply();

                        intent.putExtra("floorPlanName", levelListRefined[i]);
                        intent.putExtra("surveyName", surveyName);
                        startActivity(intent);
                    }
                });
            }
        };
        IntentFilter intentFilter = new IntentFilter(ServerQueryAsyncTask.LEVEL_LIST_QUERY_RESULT);
        registerReceiver(levelListQueryResultListener, intentFilter);


        //Call background activity to query server
        String method = "levellistquery";
        ServerQueryAsyncTask serverQueryAsyncTask = new ServerQueryAsyncTask(this);
        serverQueryAsyncTask.execute(method, surveyName);

    }

    public void facadeOnClicked(View view) {
    }

    public void northElevationOnClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLevelName", "NorthElevation");
        editor.putString("roomLabelString", "NorthElevation");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void westElevationOnClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLevelName", "WestElevation");
        editor.putString("roomLabelString", "WestElevation");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void southElevationOnClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLevelName", "SouthElevation");
        editor.putString("roomLabelString", "SouthElevation");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void eastElevationOnClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLevelName", "EastElevation");
        editor.putString("roomLabelString", "EastElevation");


        editor.apply();

        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void submitButtonOnClicked(View view) {

        new OutputGenerator(surveyName).generateCSV(getApplicationContext());
        Intent intent = new Intent(this, LoginForm.class);
        startActivity(intent);
        finish();

    }
}
