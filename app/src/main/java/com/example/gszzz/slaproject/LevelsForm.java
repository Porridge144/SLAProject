package com.example.gszzz.slaproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gszzz.slaproject.server_interaction.LoginForm;
import com.example.gszzz.slaproject.server_interaction.ServerQueryAsyncTask;
import com.example.gszzz.slaproject.storage_handler.OutputGenerator;

public class LevelsForm extends AppCompatActivity {

    String surveyName;
    ListView levelListListView, elevationsListListView, roofListListView, landscapeListListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_levels_form);

        try {
            surveyName = getIntent().getStringExtra("surveyName");
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("surveyName", surveyName);
            editor.apply();
        } catch (Exception ignored) {
        }

        levelListListView = (ListView) findViewById(R.id.levelListListView);
        elevationsListListView = (ListView) findViewById(R.id.elevationsListListView);
        roofListListView = (ListView) findViewById(R.id.roofListListView);
        landscapeListListView = (ListView) findViewById(R.id.landscapeListListView);



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

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) levelListListView.getLayoutParams();
                layoutParams.height = elevationsListListView.getHeight() / 4 * levelListRefinedNoExtension.length;
                levelListListView.setLayoutParams(layoutParams);
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

                        ((TextView) view).setTextColor(Color.RED);

                        //Reset room counts
                        LevelPlan.resetRoomCounts();

                        intent.putExtra("floorPlanName", levelListRefined[i]);
                        intent.putExtra("surveyName", surveyName);
                        startActivity(intent);
                    }
                });
            }
        };
        IntentFilter intentFilter = new IntentFilter(ServerQueryAsyncTask.LEVEL_LIST_QUERY_RESULT);
        registerReceiver(levelListQueryResultListener, intentFilter);

        elevationsListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentLevelName", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.putString("roomLabelString", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.apply();

                ((TextView) view).setTextColor(Color.RED);

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                startActivity(intent);
            }
        });

        roofListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentLevelName", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.putString("roomLabelString", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.apply();

                ((TextView) view).setTextColor(Color.RED);

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                startActivity(intent);
            }
        });

        landscapeListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentLevelName", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.putString("roomLabelString", String.valueOf(adapterView.getItemAtPosition(i)));
                editor.apply();

                ((TextView) view).setTextColor(Color.RED);

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                startActivity(intent);
            }
        });


        //Call background activity to query server
        String method = "levellistquery";
        ServerQueryAsyncTask serverQueryAsyncTask = new ServerQueryAsyncTask(this);
        serverQueryAsyncTask.execute(method, surveyName);

    }


    public void submitButtonOnClicked(View view) {

        new OutputGenerator(surveyName).generateCSV(getApplicationContext());
        Intent intent = new Intent(this, LoginForm.class);
        startActivity(intent);
        finish();

    }
}
