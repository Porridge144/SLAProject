package com.example.gszzz.slaproject.server_interaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.gszzz.slaproject.R;

public class LoginForm extends AppCompatActivity {

    EditText usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        //Clear existing shared preference
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        usernameText = (EditText) findViewById(R.id.usernameEditText);
        passwordText = (EditText) findViewById(R.id.passwordEditText);

    }

    public void loginButtonOnClicked(View view) {

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String method = "login";
        ServerQueryAsyncTask serverQueryAsyncTask = new ServerQueryAsyncTask(this);
        serverQueryAsyncTask.execute(method, username, password);
    }

    public void registerButtonOnClicked(View view) {

        Intent intent = new Intent(this, RegistrationForm.class);
        String username = usernameText.getText().toString();
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
