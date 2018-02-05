package com.example.visualartsnotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * This is Main Activity. This activity controls the screen that user starts in. From here, the user
 * can choose enter their username and password to log in, register, or use their email to recover
 * a forgotten password.
 * @author Kersley Jatto
 */
public class MainActivity extends AppCompatActivity {

    //These will hold the username and password the user enters
    private EditText usernameN;

    /**
     * This variable holds the password the user enters.
     */
    private EditText passwordN;
    /**
     * This will print error messages.
     */
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This sets them to the typed values in the 2 entry fields.
        usernameN = (EditText) findViewById(R.id.userField);
        passwordN = (EditText) findViewById(R.id.passField);
    }

    /**
     * This sends the typed in username and password to a thread that checks the MySQL database
     * for the account. If the account exists, the user is logged.
     * @param view The clicked view
     */
    public void loginUser(View view)
    {
        //Make strings from the fields
        String username = usernameN.getText().toString();
        String password = passwordN.getText().toString();

        //Create error alert dialog
        inputError = new AlertDialog.Builder(this).create();
        inputError.setMessage("Please fill out all data.");

        inputError.setTitle("Invalid Input");

        //If the either username or password is empty, the user will be notified to fill them
        if (username.isEmpty() || password.isEmpty())
        {
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
            //If they are not empty, we send them to ValidateUser which invokes an AsyncTask to check the database
            String type = "login";
            ValidateUser process = new ValidateUser(this);
            process.execute(type, username, password);

        }

    }

    /**
     * If the user clicks register, this takes them to a registration activity.
     * @param view
     */
    public void registerUser(View view)
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /**
     * If the user clicks Forgot Login, they are brought to an activity to recover their login.
     * @param view
     */
    public void cantLogin(View view)
    {
        Intent intent = new Intent(this, ForgotLogin.class);
        startActivity(intent);
    }


}