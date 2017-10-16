package com.example.gszzz.slaproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.gszzz.slaproject.server_interaction.ServerQueryAsyncTask;

public class LevelsForm extends AppCompatActivity {

    String surveyName;
    ListView levelListListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_form);

        try {
            surveyName = getIntent().getStringExtra("surveyName");
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

                ListAdapter listAdapter = new ArrayAdapter<>(LevelsForm.this, android.R.layout.simple_list_item_1, levelListRefinedNoExtension);
                levelListListView.setAdapter(listAdapter);

                levelListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), LevelPlan.class);
                        intent.putExtra("levelName", levelListRefinedNoExtension[i]);
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


//
//        levelCount = 0;
//        addLevelOnClicked(new TextView(this));
    }

/*    public void addLevelOnClicked(View view) {
        levelCount += 1;
        final TextView textView = new TextView(this);
        String tmp = "Level " + levelCount;
        textView.setText(tmp);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(23);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LevelPlan.class);
                intent.putExtra("levelName", textView.getText().toString());
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(15, 10, 10, 10);
        levelsContainerLinearLayout.addView(textView, layoutParams);
    }*/

    public void facadeOnClicked(View view) {
    }

    public void northElevationOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void westElevationOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void southElevationOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

    public void eastElevationOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
        startActivity(intent);
    }

}
