package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * TeachCourses gives a teacher user one of several choices for managing their courses. They can
 * generate a student or admin access key, view their courses, or make new classes
 */
public class TeacherCourses extends AppCompatActivity {

    /**
     * Holds the username
     */
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_courses);

        username = getIntent().getStringExtra("username");
    }

    /**
     * This method takes the user to Teacher
     * @param view
     */
    public void makeCourse(View view)
    {
        Intent intent = new Intent(this, MakeCourses.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method lists the teacher's classes
     * @param view
     */
    public void viewTaughtCourses(View view)
    {
        Intent intent = new Intent(this, TaughtCourses.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This takes the user to a class that they can use to make keys and return ones for students.
     * @param view
     */
    public void studentKeys(View view)
    {
        Intent intent = new Intent(this, StudentKeys.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }



    /**
     * Return to the main menu.
     * @param view
     */
    public void backMain(View view)
    {
        Intent intent = new Intent(this, ManageClasses.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
