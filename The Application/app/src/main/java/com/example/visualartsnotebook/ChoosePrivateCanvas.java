package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * This the activity lets the user choose what to draw on. There options would be starting on a fresh, blank canvas, using
 * one of their pictures as a canvas, or continuing off a previous project.
 * @author Kersley Jatto
 */
public class ChoosePrivateCanvas extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;
    /**
     * This holds the type of artbook
     */
    private String artbook;
    /**
     * This holds the canvas type.
     */
    private String canvasType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_private_canvas);

        //Set canvas type to "priv"
        artbook = "priv";

        //Get the username
        username = getIntent().getStringExtra("username");
    }

    /**
     * This sets the Private Journal to a blank canvas.
     * @param view
     */
    public void newBlank(View view)
    {
        /**
        canvasType = "blank";
        Intent intent = new Intent(this, PrivateJournal.class);
        intent.putExtra("username", username);
        intent.putExtra("canvas", canvasType);
        intent.putExtra("artbook", artbook);
        startActivity(intent);
         */
    }

    /**
     * This lets the user choose a picture for a new art project
     * @param view
     */
    public void newPicture(View view)
    {
        canvasType = "picture";
        Intent intent = new Intent(this, ChoosePicture.class);
        intent.putExtra("username", username);
        intent.putExtra("canvas", canvasType);
        intent.putExtra("artbook", artbook);
        startActivity(intent);
    }

    /**
     * This recovers an old project.
     * @param view
     */
    public void oldProject(View view)
    {
        /**
        canvasType = "private";
        Intent intent = new Intent(this, ChoosePrivateProject.class);
        intent.putExtra("username", username);
        intent.putExtra("canvas", canvasType);
        intent.putExtra("artbook", artbook);
        startActivity(intent);
         */
    }

    /**
     * This goes back
     * @param view
     */
    public void backArtbook(View view)
    {
        Intent intent = new Intent(this, ChooseArtbook.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


}