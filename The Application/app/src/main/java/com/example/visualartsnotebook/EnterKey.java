package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EnterKey extends AppCompatActivity {

    private String username;
    private String keyType;
    private EditText keyF;

    private AlertDialog input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_key);


        RedeemKey redeem = new RedeemKey();

        //Instantiate our fields
        username = getIntent().getStringExtra("username");
        keyType = getIntent().getStringExtra("type");
        keyF = (EditText) findViewById(R.id.keyHere);

    }

    /**
     * This method will let the user return to the Post Menu.
     * @param view
     */
    public void returnToHome(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This method sends the key to be validated.
     * @param view
     */
    public void validateKey(View view)
    {
        //These will hold the key as a String.

        String key = keyF.getText().toString();

        //We instatiate inputError to create dialogs for bad input.
        input = new AlertDialog.Builder(this).create();
        input.setMessage("Please fill the field with valid input.");
        input.setTitle("Invalid Input");

        if(key.isEmpty() || key.length() <= 13)
        {
            input.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    input.dismiss();
                }
            });
            //Log.i("Catch", "We got here 0");

            input.show();
        }
        else
        {
            String type = keyType;
            //Log.i("Catch", "We got here 1");
            VerifyUserKey verify = new VerifyUserKey(this);
            verify.execute(type, username, key);
        }

    }
}
