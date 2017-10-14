package com.example.gszzz.slaproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelPlan extends AppCompatActivity{

    private int position;
    private ImageView imageView;
    private String optionChosen;
    private AlertDialog dialog;
    private int x, y;
    private RelativeLayout relativeLayout;

    private static int roomCount = 1, kitchenCount = 1, toiletCount = 1, othersCount = 1;
    private ArrayList<View> viewArrayList = new ArrayList<>();


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
        setContentView(R.layout.activity_level_plan);

        //Which item got clicked
        Intent i = getIntent();
        position = i.getIntExtra("itemPosition", 0);

        imageView = (ImageView) findViewById(R.id.imageView);
        relativeLayout = (RelativeLayout) findViewById(R.id.floorplanRLayout);

        //Google Map

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
        String[] options = {"Kitchen", "Toilet", "Room", "Others"};
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

/*    private void showInputBox() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(LevelPlan.this);
        builder.setTitle("Enter label");
        builder.setCancelable(true);
        builder.setView(editText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!editText.getText().toString().equals("")) {
                    renderLabel(editText.getText().toString());
                    dialogInterface.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), "Name cannot be empty!", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();

    }*/

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
            case "Others" :
                labelString = optionChosen  + " " + othersCount;
                othersCount += 1;
                break;
        }

        dialog.dismiss();

        final TextView textView = new TextView(this);
        textView.setText(labelString);
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLUE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuildingDetailForm.class);
                intent.putExtra("labelString", textView.getText().toString());
                startActivity(intent);
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.leftMargin = this.x - 9 * (5 + label.length());
        layoutParams.topMargin = this.y - 209 - 15;

        viewArrayList.add(textView);

        relativeLayout.addView(textView, layoutParams);

    }
}
