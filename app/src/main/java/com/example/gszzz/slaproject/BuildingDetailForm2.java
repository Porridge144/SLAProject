package com.example.gszzz.slaproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class BuildingDetailForm2 extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    LinearLayout picturesLinearLayout, timberOptionsLinearLayout, masonaryOptionsLinearLayout, concreteOptionsLinearLayout, metalworksOptionsLinearLayout, generalOptionsLinearLayout;
    String itemName;
    TextView itemNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail_form2);
        picturesLinearLayout = (LinearLayout) findViewById(R.id.picturesLinearLayout);
        timberOptionsLinearLayout = (LinearLayout) findViewById(R.id.timberOptionsLinearLayout);
        masonaryOptionsLinearLayout = (LinearLayout) findViewById(R.id.masonaryOptionsLinearLayout);
        concreteOptionsLinearLayout = (LinearLayout) findViewById(R.id.concreteOptionsLinearLayout);
        metalworksOptionsLinearLayout = (LinearLayout) findViewById(R.id.metalworksOptionsLinearLayout);
        generalOptionsLinearLayout = (LinearLayout) findViewById(R.id.generalOptionsLinearLayout);
        Button cameraButton = (Button) findViewById(R.id.takePictureButton);

//        Disable button if no camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            cameraButton.setEnabled(false);
        }

        Intent intent = getIntent();
        itemName = intent.getStringExtra("itemName");
        itemNameTextView = (TextView) findViewById(R.id.elementTextView);
        itemNameTextView.setText(itemName);
    }
    public void takePictureOnClicked(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(photo);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            picturesLinearLayout.addView(imageView, layoutParams);

        }
    }

    public void selectPictureOnClicked(View view) {
    }

    public void timberButtonOnClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            timberOptionsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            timberOptionsLinearLayout.setVisibility(View.GONE);
        }
    }
    public void masonaryButtonOnClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            masonaryOptionsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            masonaryOptionsLinearLayout.setVisibility(View.GONE);
        }
    }
    public void concreteButtonOnClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            concreteOptionsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            concreteOptionsLinearLayout.setVisibility(View.GONE);
        }
    }
    public void metalworksButtonOnClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            metalworksOptionsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            metalworksOptionsLinearLayout.setVisibility(View.GONE);
        }
    }
    public void generalButtonOnClicked(View view) {
        if (((CheckBox) view).isChecked()) {
            generalOptionsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            generalOptionsLinearLayout.setVisibility(View.GONE);
        }
    }



    public void checkBoxOnClicked(View view) {
        if (((RadioButton) view).isChecked()) {
            Toast.makeText(getApplicationContext(), "If...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Else...", Toast.LENGTH_SHORT).show();
        }

    }

    public void finishButtonOnClick(View view) {
        Intent intent = new Intent(this, BuildingDetailForm.class);
        startActivity(intent);
    }
}
