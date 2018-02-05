package com.example.visualartsnotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * This ManagePhotos. It gives the user a choice between uploading pictures, deleting pictures, or
 * view the uploaded pictures.
 * @author Kersley Jatto
 */
public class ManagePhotos extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;

    /**
     * Instantiate the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_photos);

        //Get the username
        username = getIntent().getStringExtra("username");
    }

    /**
     * This method takes the user to an activity to upload picturs
     * @param view
     */
    public void uploadPhoto(View view)
    {
        Intent intent = new Intent(this, UploadPictures.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method takes the user view their photos
     * @param view
     */
    public void viewPhotos(View view)
    {
        Intent intent = new Intent(this, ViewPhotos.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


    /**
     * This method takes the user to an activity to delete pictures
     * @param view
     */
    public void deletePhoto(View view)
    {
        Intent intent = new Intent(this, DeletePhotos.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This goes back
     * @param view
     */
    public void goHome2(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}
