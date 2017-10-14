package com.example.gszzz.slaproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import java.net.Inet4Address;

public class BuildingDetailForm extends AppCompatActivity {


    ListView structuralListView, architecturalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail_form);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.Tab1);
        spec.setIndicator("Structural Elements");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.Tab2);
        spec.setIndicator("Architectural Elements");
        host.addTab(spec);

        structuralListView = (ListView) findViewById(R.id.structuralListView);
        architecturalListView = (ListView) findViewById(R.id.architecturalListView);
        structuralListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });
        architecturalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName =String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm2.class);
                intent.putExtra("itemName", itemName);
                startActivity(intent);
            }
        });


    }
}
