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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gszzz.slaproject.storage_handler.StorageHandler;

public class BuildingDetailForm2 extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    LinearLayout picturesLinearLayout, timberOptionsLinearLayout, masonaryOptionsLinearLayout, concreteOptionsLinearLayout, metalworksOptionsLinearLayout, generalOptionsLinearLayout;
    String itemName;
    TextView itemNameTextView;

    //Check boxes
    CheckBox timberCheckBox, masonaryCheckBox, concreteCheckBox, metalworksCheckBox, generalCheckBox;
    RadioGroup conditionClassRadioGroup;

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

        //Tmp code for test
//        StorageHandler storageHandler = new StorageHandler();
//        String[] resultStrings = storageHandler.execute(getApplicationContext(), StorageHandler.DATA_READ, StorageHandler.PAGE_BUILDING_DETAIL_FORM2, itemName);
//
//        StringBuilder tmp = new StringBuilder("");
//        for (String value : resultStrings) {
//            String tmp1 = value + "\n";
//            tmp.append(tmp1);
//        }
//        Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();

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
        storageHandler.execute(getApplicationContext(), StorageHandler.DATA_WRITE, StorageHandler.PAGE_BUILDING_DETAIL_FORM2, itemName,
                timberCheckBoxIDString, masonaryCheckBoxIDString, concreteCheckBoxIDString, metalworksCheckBoxIDString, generalCheckBoxIDString,
                timberCheckBoxContentString, masonaryCheckBoxContentString, concreteCheckBoxContentString, metalworksCheckBoxContentString, generalCheckBoxContentString,
                conditionClassButtonString);

        finish();

    }
}
