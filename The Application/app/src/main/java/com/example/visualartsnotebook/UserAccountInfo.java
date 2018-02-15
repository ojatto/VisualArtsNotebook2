package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * This is User Account Info. This activity allows the user to view their information. From there, they can adjust
 * the various information about their account including their username, password, birth date, and more. From here they'll
 * also have the option to update your information and delete your account.
 */
public class UserAccountInfo extends AppCompatActivity {


    /**
     * This will hold the username
     */
    private String theUsername;
    /**
     * This will hold the email
     */
    private String theEmail;
    /**
     * This will hold the password
     */
    private String thePassword;
    /**
     * Their birth year
     */
    private String theBirthYear;
    /**
     * This holds the security question
     */
    private String theQuestion;
    /**
     * The holds the answer to the question
     */
    private String theAnswer;

    /**
     * This variable will be used to print prompts to the user.
     */
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_info);

        //Instantiate references to all the User information fields.
        TextView usernameTextView = (TextView)findViewById(R.id.usernameText);
        TextView emailTextView = (TextView)findViewById(R.id.emailText);
        TextView passwordTextView = (TextView)findViewById(R.id.passwordText);
        TextView birthYearTextView = (TextView)findViewById(R.id.birthYearText);
        TextView squestionTextView = (TextView)findViewById(R.id.questionText);
        TextView answerTextView = (TextView)findViewById(R.id.answerText);

        //Get the information from activity intent.
        theUsername = getIntent().getStringExtra("username");
        thePassword = getIntent().getStringExtra("password");
        theEmail = getIntent().getStringExtra("email");
        theBirthYear = getIntent().getStringExtra("birthyear");
        theQuestion = getIntent().getStringExtra("squestion");
        theAnswer = getIntent().getStringExtra("answer");

        //We create the Strings that we want displayed on the app UI
        String displayUsername = "Username: " + getIntent().getStringExtra("username");
        String displayPassword = "Password: " + getIntent().getStringExtra("password");
        String displayEmail = "Email: " + getIntent().getStringExtra("email");
        String displayBirthYear = "Birth Year: " + getIntent().getStringExtra("birthyear");
        String displaySquestion = "Security Question: " + getIntent().getStringExtra("squestion");
        String displayAnswer = "The Answer: " + getIntent().getStringExtra("answer");

        //Use the display variables to set the text for User Account information
        usernameTextView.setText(displayUsername);
        emailTextView.setText(displayEmail);
        passwordTextView.setText(displayPassword);
        birthYearTextView.setText(displayBirthYear);
        squestionTextView.setText(displaySquestion);
        answerTextView.setText(displayAnswer);
    }

    /**
     * Clicking this method takes user to an activity to update their account.
     * @param view A view for UpdateAccount
     */
    public void updateYourAccount(View view)
    {
        Intent intent = new Intent(this, UpdateAccount.class);
        intent.putExtra("username", theUsername);
        intent.putExtra("email", theEmail);
        intent.putExtra("password",thePassword);
        intent.putExtra("birthyear",theBirthYear);
        intent.putExtra("squestion",theQuestion);
        intent.putExtra("answer",theAnswer);
        startActivity(intent);
    }

    /**
     * Click this mehtod takes the user to an activity that lets them delete their account as well as all associated pictures,
     * art, and courses they've created.
     * @param view
     */
    public void deleteYourAccount(View view)
    {
        Intent intent = new Intent(this, DeleteAccount.class);
        intent.putExtra("username", theUsername);
        startActivity(intent);
    }

    /**
     * Returns to Post Login Menu.
     * @param view
     */
    public void returnToPostLogin(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", theUsername);
        startActivity(intent);
    }


}
