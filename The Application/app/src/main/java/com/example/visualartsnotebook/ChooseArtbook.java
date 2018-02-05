package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Here the user chooses the artbook to go to. The Private Journal is for private art. The Portfolio is for art that
 * admins and teachers can potentially get/see.
 * @author Kersley Jatto
 */
public class ChooseArtbook extends AppCompatActivity {

    /**
     * Holds the username
     */
    private String username;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_artbook);

        //Get the user name
        username = getIntent().getStringExtra("username");
    }

    /**
     * This takes us to the Private Journal
     * @param view
     */
    public void thePrivateJournal(View view)
    {
        /**
        Intent intent = new Intent(this, ChoosePrivateCanvas.class);
        intent.putExtra("username", username);
        startActivity(intent);
         */
    }

    /**
     * This takes us to the Portfolio
     * @param view
     */
    public void toThePortfolio(View view)
    {
        /**
        Intent intent = new Intent(this, ChoosePortfolioCanvas.class);
        intent.putExtra("username", username);
        startActivity(intent);
         */
    }

    /**
     * This takes us back
     * @param view
     */
    public void goBack(View view)
    {
        /**
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
         */
    }
}
