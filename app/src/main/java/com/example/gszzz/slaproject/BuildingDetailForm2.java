package com.example.gszzz.slaproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszzz.slaproject.server_interaction.FileUploadAsyncTask;
import com.example.gszzz.slaproject.storage_handler.StorageHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class BuildingDetailForm2 extends AppCompatActivity {

    static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath = "";
    private Uri photoURI;
    private String imageFileName;
    LinearLayout picturesLinearLayout, timberOptionsLinearLayout, masonaryOptionsLinearLayout, concreteOptionsLinearLayout, metalworksOptionsLinearLayout, generalOptionsLinearLayout;
    String elementName;
    TextView itemNameTextView;

    //Check boxes
    CheckBox timberCheckBox, masonaryCheckBox, concreteCheckBox, metalworksCheckBox, generalCheckBox;
    RadioGroup conditionClassRadioGroup;


    private static int count = 0;

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


        //All CheckBoxes...
        timberCheckBox = (CheckBox) findViewById(R.id.timberCheckBox);
        masonaryCheckBox = (CheckBox) findViewById(R.id.masonaryCheckBox);
        concreteCheckBox = (CheckBox) findViewById(R.id.concreteCheckBox);
        metalworksCheckBox = (CheckBox) findViewById(R.id.metalworksCheckBox);
        generalCheckBox = (CheckBox) findViewById(R.id.generalCheckBox);

        conditionClassRadioGroup = (RadioGroup) findViewById(R.id.conditionClassRadioGroup);

//        Disable button if no camera
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            cameraButton.setEnabled(false);
        }

        Intent intent = getIntent();
        elementName = intent.getStringExtra("elementName");
        itemNameTextView = (TextView) findViewById(R.id.elementTextView);
        itemNameTextView.setText(elementName);

    }
    public void takePictureOnClicked(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == -1)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        else
            proceedPhotoTaking();

//        if (isExternalStorageWritable()) {
//            Toast.makeText(getApplicationContext(), "Writable...", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Not Writable...", Toast.LENGTH_SHORT).show();
//        }

    }

    public void onRequestPermissionsResult(int requestCode,
       String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    proceedPhotoTaking();

                } else {
                        Toast.makeText(getApplication(), "Permission Denied...Task cancelled...", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void proceedPhotoTaking() {

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Save information in shared preference file
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Get the count to generate name of pics
        String imageCountVariableName = sharedPreferences.getString("surveyName", "") + "_" +
                sharedPreferences.getString("currentLevelName", "") + "_" +
                sharedPreferences.getString("roomLabelString", "") + "_" + elementName + "_" +
                "imageCount";
        int count = sharedPreferences.getInt(imageCountVariableName, 0);
        count += 1;
        editor.putInt(imageCountVariableName, count);

        //save the file name inside a string set
        String imageFileName = sharedPreferences.getString("surveyName", "") + "_" +
                sharedPreferences.getString("currentLevelName", "") + "_" +
                sharedPreferences.getString("roomLabelString", "") + "_" + elementName + "_" +
                "cameraImage" + Integer.toString(count) +".jpg";
        String stringSetName = sharedPreferences.getString("surveyName", "") + "_" +
                sharedPreferences.getString("currentLevelName", "") + "_" +
                sharedPreferences.getString("roomLabelString", "") + "_" + elementName + "_" +
                "imageFileNamesStringSet";
        Set<String> imageFileNamesStringSet = sharedPreferences.getStringSet(stringSetName, new HashSet<String>());
        imageFileNamesStringSet.add(imageFileName);
        editor.putStringSet(stringSetName, imageFileNamesStringSet);
        editor.apply();

        this.imageFileName = imageFileName;

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Failed to create image file...", Toast.LENGTH_SHORT).show();
        }

        if (photoFile != null) {
//            Uri photoURI = FileProvider.getUriForFile(this,
//                    "com.example.gszzz.slaproject.android.fileprovider",
//                    photoFile);
//            photoURI = Uri.fromFile(photoFile);
//            photoURI = Uri.parse(currentPhotoPath);
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.gszzz.slaproject.android.fileprovider",
                    photoFile);
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            Toast.makeText(getApplication(), currentPhotoPath, Toast.LENGTH_LONG).show();
            try {
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                null,         /* suffix */
                storageDir      /* directory */
        );
//        File image = new File(storageDir, imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
//        Toast.makeText(getApplicationContext(), "Current Path: " + currentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            //Save file to internal storage
//            String imageFilePath = getFilesDir().toString() + "/" + imageFileName;

//            getContentResolver().notifyChange(photoURI, null);
//            ContentResolver cr = getContentResolver();
//            Bitmap bitmap = null;
//            try {
//                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI);
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Failed to load...", Toast.LENGTH_SHORT).show();
//            }





//            String imageFilePath = getFilesDir().toString() + "/" + imageFileName;
//
//            FileOutputStream out = null;
//            try {
//                File outputFile = new File(imageFilePath);
//                out = new FileOutputStream(outputFile);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Compression or new FileOutputStream Exception", Toast.LENGTH_SHORT).show();
//            } finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (IOException ignored) {
//
//                }
//            }



/*            if (bitmap != null) {
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                picturesLinearLayout.addView(imageView, layoutParams);
            }*/

            File oldFile = new File(currentPhotoPath);
            File newFile = new File(getFilesDir(), imageFileName);

            try {
                copy(oldFile, newFile);
                Toast.makeText(getApplicationContext(),String.valueOf(count), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "IOException..", Toast.LENGTH_LONG).show();
            }

            new FileUploadAsyncTask(getApplicationContext()).execute(newFile.getAbsolutePath());


            // Get the dimensions of the View
            int targetW = 200;
            int targetH = 200;

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            ImageView imageView = new ImageView(this);

            imageView.setImageBitmap(bitmap);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            picturesLinearLayout.addView(imageView, layoutParams);

        }
    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                    count += 1;
                }
            }
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

        //-----------------------------------------TIMBER--------------------------------------------
        String timberCheckBoxIDString = "", timberCheckBoxContentString = "";
        if (timberCheckBox.isChecked()) {
            CheckBox tmpCheckBox = (CheckBox) findViewById(R.id.timberCb1);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.timberCb2);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.timberCb3);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.timberCb4);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.timberCb5);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.timberCb6);
            if (tmpCheckBox.isChecked()) {
                timberCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                timberCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }

        } else {
            timberCheckBoxIDString = "NA";
            timberCheckBoxContentString = "NA";
        }

        //--------------------------------------MASONARY-----------------------------------------
        String masonaryCheckBoxIDString = "", masonaryCheckBoxContentString = "";
        if (masonaryCheckBox.isChecked()) {
            CheckBox tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb1);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb2);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb3);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb4);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb5);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.masonaryCb6);
            if (tmpCheckBox.isChecked()) {
                masonaryCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                masonaryCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }

        } else {
            masonaryCheckBoxIDString = "NA";
            masonaryCheckBoxContentString = "NA";
        }

        //----------------------------------CONCRETE--------------------------------------------
        String concreteCheckBoxIDString = "", concreteCheckBoxContentString = "";
        if (concreteCheckBox.isChecked()) {
            CheckBox tmpCheckBox = (CheckBox) findViewById(R.id.concreteCb1);
            if (tmpCheckBox.isChecked()) {
                concreteCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                concreteCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.concreteCb2);
            if (tmpCheckBox.isChecked()) {
                concreteCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                concreteCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.concreteCb3);
            if (tmpCheckBox.isChecked()) {
                concreteCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                concreteCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.concreteCb4);
            if (tmpCheckBox.isChecked()) {
                concreteCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                concreteCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.concreteCb5);
            if (tmpCheckBox.isChecked()) {
                concreteCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                concreteCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }

        } else {
            concreteCheckBoxIDString = "NA";
            concreteCheckBoxContentString = "NA";
        }


        //-----------------------------------------METALWORKS-------------------------------------------
        String metalworksCheckBoxIDString = "", metalworksCheckBoxContentString = "";
        if (metalworksCheckBox.isChecked()) {
            CheckBox tmpCheckBox = (CheckBox) findViewById(R.id.metalworksCb1);
            if (tmpCheckBox.isChecked()) {
                metalworksCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                metalworksCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.metalworksCb2);
            if (tmpCheckBox.isChecked()) {
                metalworksCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                metalworksCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.metalworksCb3);
            if (tmpCheckBox.isChecked()) {
                metalworksCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                metalworksCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }

        } else {
            metalworksCheckBoxIDString = "NA";
            metalworksCheckBoxContentString = "NA";
        }


        //--------------------------------------GENERAL------------------------------------------------
        String generalCheckBoxIDString = "", generalCheckBoxContentString = "";
        if (generalCheckBox.isChecked()) {
            CheckBox tmpCheckBox = (CheckBox) findViewById(R.id.generalCb1);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb2);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb3);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb4);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb5);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb6);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb7);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }
            tmpCheckBox = (CheckBox) findViewById(R.id.generalCb8);
            if (tmpCheckBox.isChecked()) {
                generalCheckBoxIDString += Integer.toString(tmpCheckBox.getId()) + ":";
                generalCheckBoxContentString += tmpCheckBox.getText().toString() + ":";
            }

        } else {
            generalCheckBoxIDString = "NA";
            generalCheckBoxContentString = "NA";
        }


        String emptyFieldMessage = "Please select at least one option for Material: ";
        boolean emptyFieldFlag = false;
        if (timberCheckBoxIDString.equals("")) {
            emptyFieldMessage += "Timber ";
            emptyFieldFlag = true;
        }
        if (masonaryCheckBoxIDString.equals("")) {
            emptyFieldMessage += "Masonary ";
            emptyFieldFlag = true;
        }
        if (concreteCheckBoxIDString.equals("")) {
            emptyFieldMessage += "Concrete ";
            emptyFieldFlag = true;
        }
        if (metalworksCheckBoxIDString.equals("")) {
            emptyFieldMessage += "Metalworks ";
            emptyFieldFlag = true;
        }
        if (generalCheckBoxIDString.equals("")) {
            emptyFieldMessage += "General ";
            emptyFieldFlag = true;
        }

        if (emptyFieldFlag) {
            Toast.makeText(getApplicationContext(), emptyFieldMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        if (conditionClassRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select Condition Class", Toast.LENGTH_SHORT).show();
            return;
        }


        RadioButton tmpButton = (RadioButton) findViewById(conditionClassRadioGroup.getCheckedRadioButtonId());
        String conditionClassButtonString = tmpButton.getText().toString();

        StorageHandler storageHandler = new StorageHandler();
        storageHandler.execute(getApplicationContext(), StorageHandler.DATA_WRITE, StorageHandler.PAGE_BUILDING_DETAIL_FORM2, elementName,
                timberCheckBoxIDString, masonaryCheckBoxIDString, concreteCheckBoxIDString, metalworksCheckBoxIDString, generalCheckBoxIDString,
                timberCheckBoxContentString, masonaryCheckBoxContentString, concreteCheckBoxContentString, metalworksCheckBoxContentString, generalCheckBoxContentString,
                conditionClassButtonString);

        finish();

    }
}
