package com.example.parstagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show Actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        setContentView(R.layout.login_activity);

        if (ParseUser.getCurrentUser() != null)
            goMainActivity();


        etUsername      = findViewById(R.id.etUsername);
        etPassword      = findViewById(R.id.etPassword);
        btnLogin        = findViewById(R.id.btnLogin);
        btnSignUp       = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }


    /*
     * Name         : loginUser
     * Parameters   : The username and password of the user
     * Description  : Checks the credentials of the user by making a network request to parse server in the background thread. If success then e is null
     * Returns      : void
     */
    private void loginUser(String username, String password){
        Log.i(TAG, "Attempting to login user: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login\n", e);
                    Toast.makeText(LoginActivity.this, "Unsuccessful login", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                }
            }
        });
    }


    /*
     * Name         : signUp
     * Parameters   : New Username and New Password
     * Description  : Signup with a new username (does not check to see if username is taken)
     * Return       : void
     */
    private void signUp(String username, String password){
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(username);
        parseUser.setPassword(password);
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.i(TAG, "Successful SignUp");
                    goMainActivity();
                }else
                    Log.e(TAG, "Unsuccessful Sigup");
            }
        });
    }



    /*
     * Name         : goMainActivity
     * Parameters   : void
     * Description  : Launches the main activity
     * Returns      : void
     */
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}