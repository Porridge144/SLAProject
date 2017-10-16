package com.example.gszzz.slaproject.server_interaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gszzz.slaproject.R;

public class RegistrationForm extends AppCompatActivity {

    EditText usernameEditText2, passwordEditText2, passwordEditText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        usernameEditText2 = (EditText) findViewById(R.id.usernameEditText2);
        passwordEditText2 = (EditText) findViewById(R.id.passwordEditText2);
        passwordEditText3 = (EditText) findViewById(R.id.passwordEditText3);

    }

    public void registerButtonOnClicked(View view) {
        String username = usernameEditText2.getText().toString();
        String password = passwordEditText2.getText().toString();
        String password1 = passwordEditText3.getText().toString();

        if (password.equals(password1)) {
            String method = "register";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, username, password);
        } else {
            Toast.makeText(getApplicationContext(), "Password and Password Confirm do not match...", Toast.LENGTH_SHORT).show();
            usernameEditText2.setText("");
            passwordEditText2.setText("");
            passwordEditText3.setText("");
        }
    }
}
