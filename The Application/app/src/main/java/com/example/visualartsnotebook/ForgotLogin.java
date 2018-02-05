package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the class ForgotLogin. This activity makes the user enter their email. If it's in the system
 * they are taken to a page where they can answer the corresponding security question.
 * @author Kersley Jatto
 */
public class ForgotLogin extends AppCompatActivity {

    /**
     * This holds the typed in email.
     */
    private EditText emailG;
    /**
     * This will allow us to print messages to the user.
     */
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_login);

        //Set the variable to the email EditText
        emailG = (EditText) findViewById(R.id.emailLine2);
    }

    /**
     * This method allows us to return to the main menu upon being clicked.
     * @param view This will have the button reference.
     */
    public void returnHome2(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * This method takes the email typed and invokes ValidateUser's AsyncTask to check if it's in the
     * database.
     * @param view
     */
    public void sendLogin(View view)
    {
        //These will hold the text from the EditText values as Strings.
        String email = emailG.getText().toString();

        //We instatiate inputError to create dialogs for bad input.
        inputError = new AlertDialog.Builder(this).create();
        inputError.setMessage("Please fill type in a valid email.");
        inputError.setTitle("Invalid Input");

        //Make sure every field has been filled with a legal value. If not, send an error message.
        if(email.isEmpty()  || !isEmailValid(email))
        {
            inputError.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){
                    inputError.dismiss();
                }
            });
            inputError.show();
        }
        else
        {
            //If the email is not empty and is in email format, we send it to ValidateUser.
            String type = "search";
            ValidateUser search = new ValidateUser(this);
            search.execute(type, email);
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
}
