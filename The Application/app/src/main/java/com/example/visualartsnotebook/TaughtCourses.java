package com.example.visualartsnotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TaughtCourses lets user see the courses they are teaching.
 */
public class TaughtCourses extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;
    /**
     * The params
     */
    public String params = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taught_courses);

        //Instantiate the username variable
        username = getIntent().getStringExtra("username");

        //Get the class titles.
        GetCourses asyncTask = new GetCourses(new GetCourses.AsyncResponse() {
            @Override
            public void processFinish(String result) {
                String[] strValues = result.split("NEXT CLASS TITLE:");
                ArrayList<String> classList = new ArrayList<String>(Arrays.asList(strValues));

                makeCourseList(classList);
            }
        });

        asyncTask.execute(params, username);
    }

    /**
     * Make the courses into buttons and add them to the activity.
     * @param arrayList
     */
    public void makeCourseList(ArrayList<String> arrayList)
    {
        final ArrayList<String> list = arrayList;
        LinearLayout linear = (LinearLayout)findViewById(R.id.listCoursesTaught);

        //We'll use this to set the width and height for each class button
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        int buttonWidth = (int) (width * (.95));
        int buttonHeight = (int) (height * (.15));


        //Use each title to make a button
        for(int i = 0; i < list.size(); i++)
        {
            Button button = new Button(this);
            button.setText(list.get(i));
            button.setTextSize(18);
            linear.addView(button, buttonWidth, buttonHeight);
        }
    }

    /**
     * This takes us back to TeacherCourses
     * @param view
     */
    public void backToTeacher(View view)
    {
        Intent intent = new Intent(this, TeacherCourses.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
