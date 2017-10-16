package com.example.gszzz.slaproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LevelsForm extends AppCompatActivity {

    LinearLayout levelsContainerLinearLayout;
    static int levelCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_form);

        levelsContainerLinearLayout = (LinearLayout) findViewById(R.id.levelsContainerLinearLayout);
        levelCount = 0;
        addLevelOnClicked(new TextView(this));
    }

    public void addLevelOnClicked(View view) {
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
    }

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
