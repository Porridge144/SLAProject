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

public class MainActivity extends AppCompatActivity {

    ListView surveyListListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surveyListListView = (ListView) findViewById(R.id.surveyListListView);

        //Set up listener for survey list query result intent
        BroadcastReceiver surveyListQueryResultListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Parse info here and display in list view
                String surveyNameString = intent.getStringExtra("surveyNameString");
                String[] surveyList = surveyNameString.split(":");
                String[] surveyListRefined = new String[surveyList.length - 1];
                System.arraycopy(surveyList, 1, surveyListRefined, 0, surveyList.length - 1);

                ListAdapter listAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, surveyListRefined);
                surveyListListView.setAdapter(listAdapter);

                surveyListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), BuildingSelectionForm.class);
                        intent.putExtra("surveyName", String.valueOf(adapterView.getItemAtPosition(i)));
                        startActivity(intent);
                    }
                });
            }
        };
        IntentFilter intentFilter = new IntentFilter(ServerQueryAsyncTask.SURVEY_LIST_QUERY_RESULT);
        registerReceiver(surveyListQueryResultListener, intentFilter);

        //Get username from background task
        String username = getIntent().getStringExtra("username");

        //Call background activity to query server
        String method = "surveylistquery";
        ServerQueryAsyncTask serverQueryAsyncTask = new ServerQueryAsyncTask(this);
        serverQueryAsyncTask.execute(method, username);
    }

    public void newSurveyButtonOnclick(View view) {
        Intent i = new Intent(this, BuildingSelectionForm.class);
        startActivity(i);
    }


    public void refreshListButtonOnClicked(View view) {
    }
}
