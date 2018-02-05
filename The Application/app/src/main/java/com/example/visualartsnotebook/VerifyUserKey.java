package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

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
import java.util.ArrayList;

/**
 * Created by okjat on 4/15/2017.
 * This is the class VerfiyUserKey. It uses AysncTask to check the MySQL database and verify the user's
 * account keys.
 * @author Kersley Jatto
 */
public class VerifyUserKey extends AsyncTask<String, Void, String>
{
    /**
     * This gets the context
     */
    Context context;
    /**
     * This is for alert dialogues
     */
    AlertDialog alertDialog;

    /**
     * This will hold the result of the thread
     */
    private String actionToPerform;

    /**
     * This is for the type of key being redeemed
     */
    String type;
    /**
     * This is for the username
     */
    private String username;


    /**
     * The Constructor
     * @param con
     */
    public VerifyUserKey(Context con)
    {
        context = con;
        //Log.i("Catch", "We got here 2");
    }

    /**
     * We send the type and key in to the PHP in the background
     * @param params
     * @return The PHP output
     */
    public String doInBackground(String... params)
    {
        //Get the value of type from EnterKey.java. If it equals either student, teacher, or administrator, we check
        //to see if the user is already that account type. If not, then we see if the key is valid.
        type = params[0];
        String keyCheckURL = "http://kersleyojatto.com/projects/visualartsnotebook2/keyChecker.php";
        //These files haven't been made yet but will reference this file.
        String courseKeyURL = "http://kersleyojatto.com/projects/visualartsnotebook2/courseKey.php";
        String galleryKeyURL = "http://kersleyojatto.com/projects/visualartsnotebook2/galleryKey.php";


        if(type.equals("student") || type.equals("teacher") || type.equals("administrator"))
        {
            try
            {
                username = params[1];
                String keyType = type;
                String userKey = params[2];


                //Create a URL reference
                URL url = new URL(keyCheckURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("UserName", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        + URLEncoder.encode("KeyType", "UTF-8")+"="+URLEncoder.encode(keyType, "UTF-8")+"&"
                        + URLEncoder.encode("UserKey", "UTF-8")+"="+URLEncoder.encode(userKey, "UTF-8");
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
            if(actionToPerform.equalsIgnoreCase("Successfully added")) {
                alertDialog.setTitle("SUCCESS");
                alertDialog.setMessage("Your key has been redeemed. You're now a student user!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(context, PostLoginMenu.class);
                        intent.putExtra("username", username);
                        context.startActivity(intent);

                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Exists"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("You're already a registered student user.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Invalid"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("The key you gave is invalid.");
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
                alertDialog.setMessage("There was an unknown error with redeeming the key...");
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
            if(actionToPerform.equalsIgnoreCase("Successfully added")) {
                alertDialog.setTitle("SUCCESS");
                alertDialog.setMessage("Your key has been redeemed. You're now a teacher user!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(context, PostLoginMenu.class);
                        intent.putExtra("username", username);
                        context.startActivity(intent);
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Exists"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("You're already a registered teacher user.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Invalid"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("The key you gave is invalid.");
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
                alertDialog.setMessage("There was an unknown error with redeeming the key...");
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
            if(actionToPerform.equalsIgnoreCase("Successfully added")) {
                alertDialog.setTitle("SUCCESS");
                alertDialog.setMessage("Your key has been redeemed. You're now an administrator user!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(context, PostLoginMenu.class);
                        intent.putExtra("username", username);
                        context.startActivity(intent);

                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Exists"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("You're already a registered administrator user.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Invalid"))
            {
                alertDialog.setTitle("Error:");
                alertDialog.setMessage("The key you gave is invalid.");
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
                alertDialog.setTitle("Error");
                alertDialog.setMessage("There was an unknown error with redeeming the key...");
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
