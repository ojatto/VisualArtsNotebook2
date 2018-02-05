package com.example.visualartsnotebook;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by okjat on 4/28/2017.
 * This class checks what account types the user is
 * @author Kersley Jatto
 */

public class CheckUser extends AsyncTask<String, Void, String>
{
    /**
     * This activity context
     */
    Context context;
    /**
     * AlertDialogue for printing
     */
    AlertDialog alertDialog;
    /**
     * The particular account type to verify the user as
     */
    String type;
    /**
     * Holds the results of the PHP interaction
     */
    private String actionToPerform;
    /**
     * The username
     */
    private String username;

    public CheckUser(Context con)
    {
        context = con;
    }

    public String doInBackground(String...params)
    {
        //Get the account the user claims to be
        type = params[0];

        String checkURL = "http:ojatto.cs.loyola.edu/checkUser.php";

        if(type.equals("student") || type.equals("teacher") || type.equals("administrator"))
        {
            try
            {
                username = params[1];
                String accountType = type;

                //Create a URL reference
                URL url = new URL(checkURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("UserName", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        + URLEncoder.encode("AccountType", "UTF-8")+"="+URLEncoder.encode(accountType, "UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                setActionToPerform(result);
                System.out.println(result + " HERE");
                return result;
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String fetchStatus() {
        System.out.println(actionToPerform);
        return actionToPerform;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
    }


    @Override
    protected void onPostExecute(String result)
    {
        actionToPerform = fetchStatus();
        Log.i("Catch", "Result: " + actionToPerform);
        if(type.equals("student"))
        {
            //If they're a student, we let them in.
            if(actionToPerform.equalsIgnoreCase("They're a student")) {
                //Intent intent = new Intent(context, StudentCourses.class);
                //intent.putExtra("username", username);
                //context.startActivity(intent);
            }
            else if(actionToPerform.equalsIgnoreCase("Not a student"))
            {
                alertDialog.setTitle("Uh Oh!");
                alertDialog.setMessage("You are not registered as a student. You can't use these features.");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("There was an unknown error...");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("teacher"))
        {
            //If they're a student, we let them in.
            if(actionToPerform.equalsIgnoreCase("They're a teacher")) {
                /**
                Intent intent = new Intent(context, TeacherCourses.class);
                intent.putExtra("username", username);
                context.startActivity(intent);
                 */
            }
            else if(actionToPerform.equalsIgnoreCase("Not a teacher"))
            {
                alertDialog.setTitle("Uh Oh!");
                alertDialog.setMessage("You are not registered as a teacher. You can't use these features.");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("There was an unknown error...");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("administrator"))
        {
            //If they're a student, we let them in.
            if(actionToPerform.equalsIgnoreCase("They're an administrator")) {
                Intent intent = new Intent(context, AdministratorCourses.class);
                intent.putExtra("username", username);
                context.startActivity(intent);
            }
            else if(actionToPerform.equalsIgnoreCase("Not an administrator"))
            {
                alertDialog.setTitle("Uh Oh!");
                alertDialog.setMessage("You are not registered as an administrator. You can't use these features.");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("There was an unknown error...");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }

    }

    public void setActionToPerform(String actionToPerform) {
        this.actionToPerform = actionToPerform;
    }

    @Override
    protected void onProgressUpdate(Void...values) {
        super.onProgressUpdate(values);
    }
}
