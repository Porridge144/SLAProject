package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UnitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);

        String[] unitNames = {"Unit Apple", "Unit Orange", "Unit Banana"};
        ListAdapter unitListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, unitNames);
        ListView listView = (ListView) findViewById(R.id.unitListView);
        listView.setAdapter(unitListAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), UnitPlan.class);
                        intent.putExtra("itemPosition", i);
                        startActivity(intent);
                    }
                }
        );
    }


}
