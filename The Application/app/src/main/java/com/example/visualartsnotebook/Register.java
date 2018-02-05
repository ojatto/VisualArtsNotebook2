package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.regex.Pattern;

/**
 * This class creates the activity that a user can use to Register. They register by typing a
 * unique username and email along with a password, birth year, security question, and answer.
 * @author Kersley Jatto
 */
public class Register extends AppCompatActivity {

    /**
     * This will hold the typed in username
     */
    private EditText usernameF;
    /**
     * This holds the typed in email
     */
    private EditText emailF;
    /**
     * This holds the typed in password
     */
    private EditText passwordF;
    /**
     * This ensure they didn't screw up
     */
    private EditText confirmPasswordF;
    /**
     * Their birth year
     */
    private EditText birthYearF;
    /**
     * This is a security question to recovery their password.
     */
    private EditText squestionF;
    /**
     * The answer to their security question
     */
    private EditText answerF;

    /**
     * This variable will be used to print prompts to the user.
     */
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiate our fields with the text in the EditText boxes
        usernameF = (EditText) findViewById(R.id.userName);
        emailF = (EditText) findViewById(R.id.emailLine);
        passwordF = (EditText) findViewById(R.id.passwordHere);
        confirmPasswordF = (EditText) findViewById((R.id.passwordAgain));
        birthYearF = (EditText) findViewById((R.id.birthYear));
        squestionF = (EditText) findViewById(R.id.securityQuestion);
        answerF = (EditText) findViewById(R.id.answer);
    }

    /**
     * This method allows us to return to the main menu upon being clicked.
     * @param view This will have the button reference.
     */
    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * Once the user clicks the next button, sendRegister verifies their input.
     * @param view The reference to the clicked button.
     */
    public void sendRegistrationData(View view) {
        //These will hold the text from the EditText values as Strings.
        String username;
        String email;
        String password;
        String confirmPassword;
        String birthYear;
        String squestion;
        String answer;

        //Retrieve the values
        username = usernameF.getText().toString();
        email = emailF.getText().toString();
        password = passwordF.getText().toString();
        confirmPassword = confirmPasswordF.getText().toString();
        birthYear = birthYearF.getText().toString();
        squestion = squestionF.getText().toString();
        answer = answerF.getText().toString();

        //We instatiate inputError to create dialogs for bad input.
        inputError = new AlertDialog.Builder(this).create();
        inputError.setMessage("Please fill all the fields with valid input.");
        inputError.setTitle("Invalid Input");

        //Make sure every field has been filled. If not, send an error message when a click of Next is made.
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || birthYear.isEmpty() || confirmPassword.isEmpty()
                || squestion.isEmpty() || answer.isEmpty() || !isEmailValid(email) || !isValidYear(birthYear)) {
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
            //If the input checks out, we invoke Validate User which begins an Async task to check if the provided input is
            //unique the database
            String type = "register";

            ValidateUser register = new ValidateUser(this);
            register.execute(type, username, email, password, confirmPassword, birthYear, squestion, answer);
        }
    }

    /**
     * This method verifies if the email provided is valid.
     * @param email The user's email address
     * @return True if the email is valid and false otherwise
     */
    public boolean isEmailValid(String email) {
        return !(email == null || TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * This method verifies birth year is a valid year.
     * @param year The year
     * @return True if it's a year and false otherwise.
     */
    public boolean isValidYear(String year)
    {
        if(year.length() == 4)
        {
            try
            {
                Integer.parseInt(year);
                return true;
            }
            catch(NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
