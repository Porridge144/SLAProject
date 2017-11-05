package com.example.gszzz.slaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gszzz.slaproject.storage_handler.StorageHandler;

public class PreInfoForm extends AppCompatActivity {

    String surveyName;

    EditText addressEditText, inspectionDateEditText, inspectorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_info_form);

        surveyName = getIntent().getStringExtra("surveyName");

        addressEditText = (EditText) findViewById(R.id.addressEditText);
        inspectionDateEditText = (EditText) findViewById(R.id.inspectionDateEditText);
        inspectorEditText = (EditText) findViewById(R.id.inspectorEditText);
    }

    public void prevStepOnClicked(View view) {
    }

    public void nextStepOnClicked(View view) {

        if (addressEditText.getText().toString().equals("")
                || inspectionDateEditText.getText().toString().equals("")
                || inspectorEditText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Please fill in all info first before proceed...", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageHandler storageHandler = new StorageHandler();
        storageHandler.execute(getApplicationContext(), StorageHandler.DATA_WRITE, StorageHandler.PAGE_PRE_INFO_FORM,
                addressEditText.getText().toString(),
                inspectionDateEditText.getText().toString(),
                inspectorEditText.getText().toString());

//        String[] infoStrings = storageHandler.execute(getApplicationContext(), StorageHandler.DATA_READ, StorageHandler.PAGE_PRE_INFO_FORM);
//        Toast.makeText(getApplicationContext(), infoStrings[0] + "\n" + infoStrings[1] + "\n" + infoStrings[2], Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(this, BasicInfoForm.class);
        intent.putExtra("surveyName", surveyName);
        startActivity(intent);
    }
}
