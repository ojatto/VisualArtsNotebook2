package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * This is ManageClasses. It gives a user the choice of redeeming an account key to become a student,
 * teacher, and/or administrator. If the user is already one of those account types, they can view the
 * courses they are taking as a studnet, teaching as a teacher, or supervising as an adminstrator.
 * @author Kersle Jatto
 */
public class ManageClasses extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        username = getIntent().getStringExtra("username");

    }

    /**
     * This method takes the user to a place where they can redeem their key
     * @param view
     */
    public void redeemKey(View view)
    {
        Intent intent = new Intent(this, RedeemKey.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This lets the user view the classes they are taking
     * @param view
     */
    public void takingCourse(View view)
    {
        String type = "student";
        CheckUser check = new CheckUser(this);
        check.execute(type, username);
    }

    /**
     * This lets the user view the classes they are teaching
     * @param view
     */
    public void teachingCourse(View view)
    {
        String type = "teacher";
        CheckUser check = new CheckUser(this);
        check.execute(type, username);
    }

    /**
     * This lets the user view the classes they are administrating
     * @param view
     */
    public void administrateCourse(View view)
    {
        String type = "administrator";
        CheckUser check = new CheckUser(this);
        check.execute(type, username);
    }

    /**
     * This takes us back to the post login menu
     * @param view
     */
    public void returnHome3(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


}
