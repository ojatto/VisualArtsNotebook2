package com.example.visualartsnotebook;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RedeemKey extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_key);

        username = getIntent().getStringExtra("username");
    }

    /**
     * This method sends an intent to EnterKey with students in mind
     * @param view
     */
    public void becomeStudent(View view)
    {
        Intent intent = new Intent(this, EnterKey.class);
        String s = "student";
        intent.putExtra("type", s);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * This meth sends an intent to EnterKey with teachers in mind
     * @param view
     */
    public void becomeTeacher(View view)
    {
        Intent intent = new Intent(this, EnterKey.class);
        String t = "teacher";
        intent.putExtra("type", t);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void becomeAdmin(View view)
    {
        Intent intent = new Intent(this, EnterKey.class);
        String a = "administrator";
        intent.putExtra("type", a);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}