package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * This is the MakeCourses class. A Teacher can use this activity to create a course with a unique name
 */
public class MakeCourses extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;
    /**
     * This holds the course title given
     */
    private EditText courseTitle;

    //This variable will be used to print prompts to the user.
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_courses);

        //Get the username given by intent
        username = getIntent().getStringExtra("username");
        //Get a reference to the course textbox
        courseTitle = (EditText) findViewById(R.id.courseTitle);
    }

    /**
     * This method creates a course with the given title.
     * @param view
     */
    public void generateCourse(View view)
    {
        //Create a string
        String course = courseTitle.getText().toString();

        //We instatiate inputError to create dialogs for bad input.
        inputError = new AlertDialog.Builder(this).create();
        inputError.setMessage("Please type in a Course Title");
        inputError.setTitle("Invalid Input");

        //Make sure every field has been filled. If not, send an error message when a click of Next is made.
        if (username.isEmpty() || course.isEmpty()) {
            inputError.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    inputError.dismiss();
                }
            });

            inputError.show();
        }
        else
        {
            //If the name isn't empty, we send the course title to CourseControl.
            String type = "make";
            CourseControl control = new CourseControl(this);
            control.execute(type, username, course);
        }
    }

    /**
     * This method takes the user back to main menu.
     * @param view
     */
    public void returnBack(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
