package com.example.visualartsnotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * This is EmailLogin. Upon the security question successfully being answered, it takes the username and password,
 * and puts them in an email that the user can send.
 * @author Kersley Jatto
 */
public class EmailLoginActivity extends AppCompatActivity {

    /**
     * The retrieved username
     */
    private String username;
    /**
     * The retreived emailed
     */
    private String email;
    /**
     * The retrieved password
     */
    private String password;

    /**
     * Creates the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        //Get the the intents cleanly
        String user = getIntent().getStringExtra("username");
        String mail = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("password");

        //Instantiate our instance fields
        this.username = user;
        this.email = mail;
        this.password = pass;
    }

    /**
     * This method allows us to return to the main menu upon being clicked.
     * @param view This will have the button reference.
     */
    public void returnHome3(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * This method creates an email that the user can send containing their username and login. It's sent from a generic email
     * named Josh
     * @param view
     */
    public void composeEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); //Set the destination email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Account Information"); //Set the subject
        intent.putExtra(Intent.EXTRA_TEXT, "Hello " + username + "! Your password was " + password + ". You can now" +
                " log back in so you can resume making wonderful art!"); //The message
        //Start the email activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
