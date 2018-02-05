package com.example.visualartsnotebook;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is AnswerQuestion. This class allows the user to answer their secuirty question. If the answer
 * is correct they get the ability to send an email with their credentials.
 * @author Kersley Jatto
 */
public class AnswerQuestion extends AppCompatActivity {

    /**
     * This holds the email
     */
    private String emailG;
    /**
     * This will be used to print the user's security question.
     */
    private TextView squestionG;
    /**
     * This holds the user's answer.
     */
    private EditText answerG;

    /**
     * This will allow us to print messages to the user.
     */
    private AlertDialog inputError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        //Retrieve the email typed last time so the user doesn't have to type it again.
        emailG = getIntent().getStringExtra("email");

        //Display the security question for the user.
        String question = getIntent().getStringExtra("squestion");
        squestionG = (TextView) findViewById(R.id.theQuestion);
        squestionG.setText(question);

        //Get a reference to their answer
        answerG = (EditText) findViewById(R.id.yourAnswer);
    }

    /**
     * This method checks the user's answer. If it's right, we let them get their email.
     * @param view
     */
    public void retrieveLogin(View view)
    {
        //These will hold the text from the EditText values as Strings.
        String email = emailG;
        String squestion = squestionG.getText().toString();
        String answer = answerG.getText().toString();

        //We instatiate inputError to create dialogs for bad input.
        inputError = new AlertDialog.Builder(this).create();
        inputError.setMessage("Please fill all the fields with valid input.");
        inputError.setTitle("Invalid Input");

        //Make sure every field has been filled with a legal value. If not, send an error message.
        if(email.isEmpty() || squestion.isEmpty() || answer.isEmpty() || !isEmailValid(email))
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
            //If answer isn't empty, we send it to ValidateUser which checks with the database.
            String type = "retrieve";
            ValidateUser retrieve = new ValidateUser(this);
            retrieve.execute(type, email, squestion, answer);
        }
    }

    /**
     * This method allows us to return to the main menu upon the back button being clicked
     * @param view This will have the button reference.
     */
    public void returnHome3(View view) {
        Intent intent = new Intent(this, MainActivity.class);
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
}
