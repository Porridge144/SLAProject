package com.example.gszzz.slaproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RoomSurveyList extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    LinearLayout picturesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_survey_list);

        picturesLinearLayout = (LinearLayout) findViewById(R.id.picturesLinearLayout);
        Button cameraButton = (Button) findViewById(R.id.takePictureButton);

        //Disable button if no camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            cameraButton.setEnabled(false);
        }

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
}
