package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * This is the PostLoginMenu class. It serves as a hub from which users can manage classes, manage exhibits, manage photos, or
 * get right to drawing
 */
public class PostLoginMenu extends AppCompatActivity {

    /**
     * This is used print the username at the activity
     */
    private TextView welcomeLine;
    /**
     * This hold the username
     */
    private String username;

    /**
     * Creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login_menu);

        //Get the username for the current user.
        username = getIntent().getStringExtra("username");

        //The line that'll be printed at time
        String helloUser = "Welcome " + username;

        //We programmatically set the TextView on top at run time.
        welcomeLine = (TextView)findViewById(R.id.welcomeUser);
        welcomeLine.setText(helloUser);
    }

    /**
     * This method takes to a menu to choose an artbook
     * @param view
     */
    public void chooseBook(View view)
    {
        Intent intent = new Intent(this, ChooseArtbook.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method takes the user to a menu to manage their classes
     * @param view
     */
    public void manageClass(View view)
    {
        Intent intent = new Intent(this, ManageClasses.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method takes the user to a menu to manage their photo stash
     * @param view
     */
    public void managePhotos(View view)
    {
        Intent intent = new Intent(this, ManagePhotos.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method takes the user to a menu to where they can view their account information
     * @param view
     */
    public void manageAccount(View view)
    {
        String type = "account";
        ValidateUser process = new ValidateUser(this);
        process.execute(type,username);
    }

    /**
     * This method logs the user out
     * @param view
     */
    public void logOut(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method lets the user view their exhibits
     * @param view
     */
    public void viewExhibit(View view)
    {
        Intent intent = new Intent(this, ViewExhibits.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }




}
