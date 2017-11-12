package com.example.gszzz.slaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszzz.slaproject.server_interaction.ImageDownloadAsyncTask;

import java.util.ArrayList;

public class LevelPlan extends AppCompatActivity{

    private ImageView imageView;
    private String optionChosen;
    private AlertDialog dialog;
    private int x, y;
    private RelativeLayout relativeLayout;
    private String surveyName;
    private String levelName;
    private String floorPlanName;


    private static int roomCount = 1, toiletCount = 1, corridorCount = 1, kitchenCount = 1, balconyCount = 1, patioCount = 1;


/*    //Check for permission
    private void checkFineLocationPermission() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        if (permissionCheck != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        } else {
//            Toast.makeText(getApplicationContext(), "SDK version < LOLIPOP. No need permission check.", Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_level_plan);

        //Which item got clicked
        floorPlanName = getIntent().getStringExtra("floorPlanName");
        levelName = getIntent().getStringExtra("levelName");
        surveyName = getIntent().getStringExtra("surveyName");
        TextView levelNameTextView = (TextView) findViewById(R.id.levelNameTextView);
        levelNameTextView.setText(levelName);

        imageView = (ImageView) findViewById(R.id.floorPlanImageView);
        relativeLayout = (RelativeLayout) findViewById(R.id.floorplanRLayout);

        ImageDownloadAsyncTask imageDownloadAsyncTask = new ImageDownloadAsyncTask(this, imageView);
        imageDownloadAsyncTask.execute(surveyName, floorPlanName);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        String levelRoomListString = sharedPreferences.getString(levelName + "_" + "RoomList", "");
        if (!levelRoomListString.equals("")) {
            String[] levelRooms = levelRoomListString.split(":");
            for (String roomName : levelRooms) {
                final TextView textView = new TextView(this);
                textView.setText(roomName);
                textView.setTextSize(18);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.RED);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                final int leftMargin = sharedPreferences.getInt(levelName + "_" + roomName + "_" + "leftMargin", 0);
                final int topMargin = sharedPreferences.getInt(levelName + "_" + roomName + "_" + "topMargin", 0);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Save room label in the shared preference file
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("roomLabelString", textView.getText().toString());
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                        intent.putExtra("roomLabelString", textView.getText().toString());
                        startActivity(intent);
                    }
                });

                layoutParams.leftMargin = leftMargin;
                layoutParams.topMargin = topMargin;

                relativeLayout.addView(textView, layoutParams);
            }
        }

        //Read room counts from saved values
        String roomCountsString = sharedPreferences.getString(levelName + "_" + "RoomCountsString", "");
        if (!roomCountsString.equals("")) {
            String[] roomCounts = roomCountsString.split(":");
            kitchenCount = Integer.valueOf(roomCounts[0]);
            toiletCount = Integer.valueOf(roomCounts[1]);
            roomCount = Integer.valueOf(roomCounts[2]);
            corridorCount = Integer.valueOf(roomCounts[3]);
            balconyCount = Integer.valueOf(roomCounts[4]);
            patioCount = Integer.valueOf(roomCounts[5]);

        }


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.x = x;
        this.y = y;
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            if (getLocationOnScreen(imageView).contains(x, y)) {
                showOptionListView();
            } else {
                Toast.makeText(getApplicationContext(), "Please click inside the floor plan...", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onTouchEvent(event);
    }

    private Rect getLocationOnScreen(View mView) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mView.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mView.getWidth();
        mRect.bottom = location[1] + mView.getHeight();

        return mRect;
    }

    private void showOptionListView() {
        String[] options = {"Room", "Toilet", "Corridor", "Kitchen", "Balcony", "Patio"};
        ListAdapter optionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        ListView optionListView = new ListView(this);
        optionListView.setAdapter(optionAdapter);
        optionListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                        optionChosen = String.valueOf(adapterView.getItemAtPosition(i));
                        renderLabel(optionChosen);
                    }
                }
        );
        AlertDialog.Builder builder = new AlertDialog.Builder(LevelPlan.this);
        builder.setTitle("Select type");
        builder.setCancelable(true);
        builder.setView(optionListView);
        dialog = builder.create();
        dialog.show();
    }


    private void renderLabel(String label) {

        String labelString = "";
        switch (label) {
            case "Kitchen" :
                labelString = optionChosen  + " " + kitchenCount;
                kitchenCount += 1;
                break;
            case "Toilet" :
                labelString = optionChosen  + " " + toiletCount;
                toiletCount += 1;
                break;
            case "Room" :
                labelString = optionChosen  + " " + roomCount;
                roomCount += 1;
                break;
            case "Corridor" :
                labelString = optionChosen  + " " + corridorCount;
                corridorCount += 1;
                break;
            case "Balcony" :
                labelString = optionChosen  + " " + balconyCount;
                balconyCount += 1;
                break;
            case "Patio" :
                labelString = optionChosen  + " " + patioCount;
                patioCount += 1;
                break;
        }

        dialog.dismiss();

        final TextView textView = new TextView(this);
        textView.setText(labelString);
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLUE);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        final int leftMargin = this.x - 9 * (5 + label.length());
        final int topMargin = this.y - 209 - 15;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Save room label in the shared preference file
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("roomLabelString", textView.getText().toString());

                //Save room label into the room list of this specific level
                String roomListAsLevelStringName = levelName + "_" + "RoomList";
                String tmp = sharedPreferences.getString(roomListAsLevelStringName, "");
                tmp += (textView.getText().toString() + ":");
                editor.putString(roomListAsLevelStringName, tmp);

                editor.putInt(levelName + "_" + textView.getText().toString() + "_" + "leftMargin", leftMargin);
                editor.putInt(levelName + "_" + textView.getText().toString() + "_" + "topMargin", topMargin);

                editor.apply();

                textView.setBackgroundColor(Color.RED);

                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                intent.putExtra("roomLabelString", textView.getText().toString());
                startActivity(intent);
            }
        });

        layoutParams.leftMargin = leftMargin;
        layoutParams.topMargin = topMargin;

        String roomCountsString = String.valueOf(kitchenCount) + ":" +
                                String.valueOf(toiletCount) + ":" +
                                String.valueOf(roomCount) + ":" +
                                String.valueOf(corridorCount) + ":" +
                                String.valueOf(balconyCount) + ":" +
                                String.valueOf(patioCount);

        //Save room counts string
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(levelName + "_" + "RoomCountsString", roomCountsString);
        editor.apply();

        relativeLayout.addView(textView, layoutParams);

    }

    public void finishButtonOnClicked(View view) {
//        Intent intent = new Intent(this, LevelsForm.class);
//        startActivity(intent);
        finish();
    }

    public static void resetRoomCounts() {
        roomCount = 1;
        toiletCount = 1;
        kitchenCount = 1;
        corridorCount = 1;
        balconyCount = 1;
        patioCount = 1;
    }
}
