package com.example.visualartsnotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateAccount extends AppCompatActivity {

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
     * This will create Alert dialogs for user to type input into.
     */
    private AlertDialog.Builder changeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        //Instantiate references to all the User information fields.
        TextView usernameTextView = findViewById(R.id.currentUsername);
        TextView emailTextView = findViewById(R.id.currentEmail);
        TextView passwordTextView = findViewById(R.id.currentPassword);
        TextView birthYearTextView = findViewById(R.id.currentBirthYear);
        TextView squestionTextView = findViewById(R.id.currentQuestion);
        TextView answerTextView = findViewById(R.id.currentAnswer);

        //Get the information from activity intent.
        theUsername = getIntent().getStringExtra("username");
        thePassword = getIntent().getStringExtra("password");
        theEmail = getIntent().getStringExtra("email");
        theBirthYear = getIntent().getStringExtra("birthyear");
        theQuestion = getIntent().getStringExtra("squestion");
        theAnswer = getIntent().getStringExtra("answer");

        //We create the Strings that we want displayed on the app UI
        String displayUsername = "Username: " + "\n" + getIntent().getStringExtra("username");
        String displayPassword = "Password: " + "\n" + getIntent().getStringExtra("password");
        String displayEmail = "Email: " + "\n" + getIntent().getStringExtra("email");
        String displayBirthYear = "Birth Year: " + "\n" + getIntent().getStringExtra("birthyear");
        String displaySquestion = "Security Question: " + "\n" +  getIntent().getStringExtra("squestion");
        String displayAnswer = "The Answer: " + "\n" + getIntent().getStringExtra("answer");

        //Use the display variables to set the text for User Account information
        usernameTextView.setText(displayUsername);
        emailTextView.setText(displayEmail);
        passwordTextView.setText(displayPassword);
        birthYearTextView.setText(displayBirthYear);
        squestionTextView.setText(displaySquestion);
        answerTextView.setText(displayAnswer);
    }

    /**
     * This is the method that allows us to update the various fields. This method will be invoked when any of the update buttons in the
     * activity_update_account.xml file. When a button is clicked, and alert input dialog will appear and present the field to be updated and
     * the current
     * @param view A reference for when an update button is clicked.
     */
    public void updateInformation(View view)
    {
        //We compare the view to each of the buttons. The user will get an input dialog to type some text for a new value for the field of their
        //account depending on which button is clicked.

        final String fieldType;

        if(view.getId() == R.id.usernameButton)
        {
            //Set field type to username
            fieldType = "username";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Username");
            changeField.setMessage("This is your current username: " + theUsername + "\n" + "Enter your new" +
                    "username twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newUsername1 = new EditText(this);
            newUsername1.setHint("Enter your new username:");
            final EditText newUsername2 = new EditText(this);
            newUsername2.setHint("Enter a second time to verify it:");
            newUsername1.setSingleLine();
            newUsername2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newUsername1);
            layout.addView(newUsername2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newUsername1, newUsername2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
            });
            changeField.create().show();
        }
        else if(view.getId() == R.id.emailChangeButton)
        {
            //Set field type to email.
            fieldType = "email";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Email");
            changeField.setMessage("This is your current email: " + theEmail + "\n" + "Enter your new" +
                    "email twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newEmail1 = new EditText(this);
            newEmail1.setHint("Enter your new email:");
            final EditText newEmail2 = new EditText(this);
            newEmail2.setHint("Enter it a second time to verify it:");
            newEmail1.setSingleLine();
            newEmail2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newEmail1);
            layout.addView(newEmail2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newEmail1, newEmail2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            changeField.create().show();
        }
        else if(view.getId() == R.id.passwordButton)
        {
            fieldType = "password";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Password:");
            changeField.setMessage("This is your current password: " + thePassword + "\n" + "Enter your new" +
                    "password twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newPassword1 = new EditText(this);
            newPassword1.setHint("Enter your new password:");
            final EditText newPassword2 = new EditText(this);
            newPassword2.setHint("Enter it a second time to verify it:");
            newPassword1.setSingleLine();
            newPassword2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newPassword1);
            layout.addView(newPassword2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newPassword1, newPassword2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            changeField.create().show();
        }
        else if(view.getId() == R.id.birthYearButton)
        {
            fieldType = "birthyear";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Birth Year:");
            changeField.setMessage("This is your current birth year: " + theBirthYear + "\n" + "Enter your new" +
                    "birth year twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newBirthYear1 = new EditText(this);
            newBirthYear1.setHint("Enter your new birth year:");
            final EditText newBirthYear2 = new EditText(this);
            newBirthYear2.setHint("Enter it a second time to verify it:");
            newBirthYear1.setSingleLine();
            newBirthYear2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newBirthYear1);
            layout.addView(newBirthYear2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newBirthYear1, newBirthYear2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            changeField.create().show();
        }
        else if(view.getId() == R.id.questionButton)
        {
            fieldType = "question";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Password:");
            changeField.setMessage("This is your current security question: " + theQuestion + "\n" + "Enter your new" +
                    "question twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newQuestion1 = new EditText(this);
            newQuestion1.setHint("Enter your new password:");
            final EditText newQuestion2 = new EditText(this);
            newQuestion2.setHint("Enter it a second time to verify it:");
            newQuestion1.setSingleLine();
            newQuestion2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newQuestion1);
            layout.addView(newQuestion2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newQuestion1, newQuestion2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            changeField.create().show();
        }
        else if(view.getId() == R.id.answerButton)
        {
            fieldType = "answer";

            //Instantiate the AlertDialog Builder object.
            changeField = new AlertDialog.Builder(this);
            changeField.setTitle("Update Answer:");
            changeField.setMessage("This is your current security answer: " + theAnswer + "\n" + "Enter your new" +
                    "answer twice and then click OK.");

            //Define a Linear Layout to hold the EditText.
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 20, 50, 10);

            //Create two EditText objects. One for entering the new username and a second for verifying it. We set hints for the user
            //to tell which is which.
            final EditText newAnswer1 = new EditText(this);
            newAnswer1.setHint("Enter your new password:");
            final EditText newAnswer2 = new EditText(this);
            newAnswer2.setHint("Enter it a second time to verify it:");
            newAnswer1.setSingleLine();
            newAnswer2.setSingleLine();

            //Add them the EditText fields to the Linear Layout
            layout.addView(newAnswer1);
            layout.addView(newAnswer2);

            //Set the view into the Alert Dialog
            changeField.setView(layout);

            changeField.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Invoke the updater
                    performUpdate(fieldType, newAnswer1, newAnswer2);
                }
            });
            changeField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            changeField.create().show();
        }

    }

    /**
     * Takes the user back to their account page.
     * @param view A view reference for the back button
     */
    public void backToAccount(View view)
    {
        Intent intent = new Intent(this, UserAccountInfo.class);
        intent.putExtra("username", theUsername);
        intent.putExtra("email", theEmail);
        intent.putExtra("password", thePassword);
        intent.putExtra("birthyear",theBirthYear);
        intent.putExtra("squestion",theQuestion);
        intent.putExtra("answer", theAnswer);
        startActivity(intent);
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

    /**
     * A worker method that allows us to invoke ValidateUser's Async Tasks from within the Positive Buttons while preserving the "this" context.
     * for UpdateAccount without resorting to getApplicationContext().
     * @param text1 The value of the first box
     * @param text2 The value of the second box
     * @param field The type of value the user wants to change
     */
    public void performUpdate(String field, EditText text1, EditText text2)
    {
        //Retrieve the values from the EditText and put them into Strings
        String newValueBox1 = text1.getText().toString();
        String newValueBox2 = text2.getText().toString();

        if(field.equalsIgnoreCase("username"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same username was entered twice, if so, we send the new username off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2)) {
                    String type = "update"; //The process type

                    //Create a ValidateUser object for launching an Aysnc task
                    ValidateUser updater = new ValidateUser(this);
                    updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);

                } else {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
        else if(field.equalsIgnoreCase("email"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same email wasn't entered twice, if so, we send the new email off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2))
                {
                    //If the two fields are the same, we then have to make sure it's a valid email.
                    if(isEmailValid(newValueBox1) && isEmailValid(newValueBox2)) {
                        String type = "update"; //The process type

                        //Create a ValidateUser object for launching an Aysnc task
                        ValidateUser updater = new ValidateUser(this);
                        updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);
                    }
                    else
                    {
                        //If it's not a valid email, tell them to enter a valid one.
                        Toast.makeText(getApplicationContext(), "This is not a valid email. Please enter a valid one.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
        else if(field.equalsIgnoreCase("password"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same password was entered twice, if so, we send the new password off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2)) {
                    String type = "update"; //The process type

                    //Create a ValidateUser object for launching an Aysnc task
                    ValidateUser updater = new ValidateUser(this);
                    updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);

                } else {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
        else if(field.equalsIgnoreCase("birthyear"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same birth year was entered twice, if so, we send the new birth year off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2))
                {
                    //If the two fields are the same, we then have to make sure it's a valid year.
                    if(isValidYear(newValueBox1) && isValidYear(newValueBox2)) {
                        String type = "update"; //The process type

                        //Create a ValidateUser object for launching an Aysnc task
                        ValidateUser updater = new ValidateUser(this);
                        updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);
                    }
                    else
                    {
                        //If it's not a valid email, tell them to enter a valid one.
                        Toast.makeText(getApplicationContext(), "This is not a valid year. Please enter a valid one.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
        else if(field.equalsIgnoreCase("question"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same security question was entered twice, if so, we send the new security question off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2)) {
                    String type = "update"; //The process type

                    //Create a ValidateUser object for launching an Aysnc task
                    ValidateUser updater = new ValidateUser(this);
                    updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);

                } else {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
        else if(field.equalsIgnoreCase("answer"))
        {
            //First verify that the two boxes aren't empty
            if (!newValueBox1.isEmpty() && !newValueBox2.isEmpty()) {
                //Now we make sure the same security answer was entered twice, if so, we send the new security answer off to the database to see
                //if it'll work.
                if (newValueBox1.equalsIgnoreCase(newValueBox2)) {
                    String type = "update"; //The process type

                    //Create a ValidateUser object for launching an Aysnc task
                    ValidateUser updater = new ValidateUser(this);
                    updater.execute(type, theUsername, field, newValueBox1, theUsername, theEmail, thePassword, theBirthYear, theQuestion, theAnswer);

                } else {
                    //If they're not the same, we tell the user to double check their input.
                    Toast.makeText(getApplicationContext(), "The two fields are not the same. Please double check.", Toast.LENGTH_SHORT).show();
                }
            } else {
                //If either box is empty, must tell the user to fill all the boxes
                Toast.makeText(getApplicationContext(), "Please fill in both boxes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
