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
 * This is ValidateUSer. This Java class extends AsyncTask and takes care of all the issues concerning the user's account
 * Created by Kersley Jatto on 3/19/2017.
 */
public class ValidateUser extends AsyncTask<String, Void, String>
{
    /**
     * This gets the context of the activity trying to access the database
     */
    Context context;
    /**
     * This is our AlertDialog
     */
    AlertDialog alertDialog;
    /**
     * This holds what type of task is at hand
     */
    String type;
    /**
     * This holds the result of the PHP
     */
    private String actionToPerform;
    /**
     * This holds the username and allows it to be easily sent via intents
     */
    private String username;
    /**
     * This is the constructor for the Constructor class
     * @param con
     */
    public ValidateUser(Context con)
    {
        context = con;
    }

    /**
     * This function connnects to one of 4 PHP files depending on the task at hand
     * @param params The set of fields given by execute
     * @return The result from the PHP
     */
    public String doInBackground(String... params)
    {
        //Get the value of type from Register.java. If it equals register, the following code accesses the PHP files.
        type = params[0];

        String registerURL = "http://kersleyojatto.com/projects/visualartsnotebook2/registerUser.php";
        String verifyLoginURL = "http://kersleyojatto.com/projects/visualartsnotebook2/verifyLogin.php";
        String checkEmailURL = "http://kersleyojatto.com/projects/visualartsnotebook2/checkEmail.php";
        String findLoginURL = "http://kersleyojatto.com/projects/visualartsnotebook2/findLogin.php";
        String yourInfoURL = "http://kersleyojatto.com/projects/visualartsnotebook2/yourInfo.php";

        if(type.equals("register"))
        {
            //If type equals register we send the potential new account information
            try
            {
                String username = params[1];
                String email = params[2];
                String password = params[3];
                String confirmPassword = params[4];
                String birthyear = params[5];
                String squestion = params[6];
                String answer = params[7];

                //Create a URL reference
                URL url = new URL(registerURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("UserName", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        + URLEncoder.encode("BirthYear", "UTF-8")+"="+URLEncoder.encode(birthyear, "UTF-8")+"&"
                        + URLEncoder.encode("Email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"
                        + URLEncoder.encode("Password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8")+"&"
                        + URLEncoder.encode("ConfirmPassword", "UTF-8")+"="+URLEncoder.encode(confirmPassword, "UTF-8")+"&"
                        + URLEncoder.encode("SQuestion", "UTF-8")+"="+URLEncoder.encode(squestion, "UTF-8")+"&"
                        + URLEncoder.encode("Answer", "UTF-8")+"="+URLEncoder.encode(answer, "UTF-8");
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
        else if(type.equals("login"))
        {
            //If type equals Login we see if the combination of username and password is in the table
            try
            {
                username = params[1];
                String password = params[2];
                URL url = new URL(verifyLoginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("Username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        +URLEncoder.encode("Password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
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
                return result;
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else if(type.equals("search"))
        {
            //If type equals email we see if the email is in the table
            try
            {
                String email = params[1];

                URL url = new URL(checkEmailURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("Email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8");
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
                return result;

            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        else if(type.equals("retrieve"))
        {
            //If type equals retrieve, we check if the provided answer matches the security question.
            try
            {
                String email = params[1];
                String squestion = params[2];
                String answer = params[3];

                URL url = new URL(findLoginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("Email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"
                        +URLEncoder.encode("SQuestion", "UTF-8")+"="+URLEncoder.encode(squestion, "UTF-8") +"&"
                        + URLEncoder.encode("Answer", "UTF-8")+"="+URLEncoder.encode(answer, "UTF-8");
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
                return result;
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else if(type.equalsIgnoreCase("account"))
        {
            //If type equals "account" we must present the user with their information.
            try
            {
                String username = params[1];

                URL url = new URL(yourInfoURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("Username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                String line = "";
                String result = "";
                while((line = bufferedReader.readLine()) != null){
                    result = result+line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                setActionToPerform(result);
                Log.d("Error:", "Result: " + result);
                return result;

            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else if(type.equalsIgnoreCase("update"))
        {

        }
        else if(type.equalsIgnoreCase("delete"))
        {

        }
        return null;
    }

    /**
     * A getter for actionToPerform
     * @return actionToPerform
     */
    public String fetchStatus() {
        System.out.println(actionToPerform);
        return actionToPerform;
    }

    /**
     * Create the Alert Dialog prior to begining thread activity
     */
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(String result)
    {
        actionToPerform = fetchStatus();

        //If type equals register, we check if the PHP successfully created their account. If not, it hopefully tells the user
        //what went wrong.
        if(type.equals("register"))
        {
            //If the registration worked, we go back to Main to let the user login.
            if(actionToPerform.equalsIgnoreCase("Successfully added")) {
                alertDialog.setMessage("Registration successful");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Here we make an intent on the spot and proceed
                        context.startActivity(new Intent(context, MainActivity.class));

                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("exists"))
            {
                //We output this message if the username is taken
                alertDialog.setMessage("This username is already in use, please select a different one.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("email taken"))
            {
                //We output this message if the email is taken
                alertDialog.setMessage("This email is already in use, please select a different one.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Passwords don't match"))
            {
                //We output this message if user didn't type their email in twice
                alertDialog.setMessage("Passwords don't match.");
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
                //This is a blanket message for unforeseen issues
                Log.d("Unknown Error-->",result);
                alertDialog.setMessage("There was an error with registration...");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("login"))
        {
            //If the Login is successful, we got to the Post Login menu. Otherwise we output an error mesage.
            if (actionToPerform.equalsIgnoreCase("Login Successful")) {
                Intent loginActivity = new Intent(context, PostLoginMenu.class);
                loginActivity.putExtra("username", username); //We send the username to as future tasks depend on it

                context.startActivity(loginActivity);
            } else {
                alertDialog.setMessage("Invalid username or password");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("search"))
        {
            //If the PHP script does NOT return Not found, we get the user's row of data and parse it into an ArrayList
            if (!actionToPerform.equalsIgnoreCase("Not found"))
            {
                alertDialog.setMessage("This email is in the system. Please answer your security question");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

                //We need to print the security question for the user to answer.
                String squestion = "";
                String answer = "";
                String userEmail = "";
                String userName = "";

                //Now we make a JSON to decode the row tuple sent from the PHP.
                try
                {
                    //Create a JSON array out of the PHP query.
                    JSONArray jsonArray = new JSONArray(result);

                    //This array list will store the user tuple.
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        list.add(jsonArray.getString(i));
                    }
                    //Get the user's security question.
                    userName = list.get(1);
                    userEmail = list.get(3);
                    squestion = list.get(5);
                    answer = list.get(6);

                    //Since the email is correct we create an intent that goes to Answer question
                    Intent answerActivity = new Intent(context, AnswerQuestion.class);
                    answerActivity.putExtra("username", userName);
                    answerActivity.putExtra("email", userEmail);
                    answerActivity.putExtra("squestion", squestion);
                    answerActivity.putExtra("answer", answer);
                    context.startActivity(answerActivity);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                //If the email is completely invalid we tell them as such
                alertDialog.setMessage("Invalid email.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("retrieve"))
        {
            //If the user correctly answered their question and result is NOT "Not found", we once again, get the row's values
            //as a JSON array. Except this time, we send the values to an activity that will print the for the user and let them
            //send an email with the login to themselves if they so desire.
            if(!actionToPerform.equalsIgnoreCase("Not found"))
            {
                alertDialog.setMessage("Your account was found!");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

                String userName = "";
                String userPassword = "";
                String userEmail = "";

                //Now we make a JSON to decode the row tuple sent from the PHP.
                try
                {
                    //Create a JSON array out of the PHP query.
                    JSONArray jsonArray = new JSONArray(result);

                    //This array list will store the user tuple.
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        list.add(jsonArray.getString(i));
                    }
                    //Get the user's email, username, and password.
                    userEmail = list.get(3);
                    userName = list.get(1);
                    userPassword = list.get(4);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                //With the username and email retrieved, we now send the user their login information via email.
                Intent emailActivity = new Intent(context, EmailLoginActivity.class);
                emailActivity.putExtra("username", userName);
                emailActivity.putExtra("email", userEmail);
                emailActivity.putExtra("password", userPassword);
                context.startActivity(emailActivity);
            }
            else
            {
                alertDialog.setMessage("Invalid email or incorrect Security Question/Answer. Please try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("account"))
        {
            //If type equals "account" this means the user wants to view the account information so we parse the tuple
            //from the database and give it to UserAccountIInfo to display.
            if(!actionToPerform.equalsIgnoreCase("Not Found"))
            {
                String username = "";
                String password = "";
                String email = "";
                String birthyear = "";
                String squestion = "";
                String answer = "";

                try
                {
                    //Now we make a JSON to decode the row tuple sent from the PHP.
                    JSONArray jsonArray = new JSONArray(result);

                    //This array list will store the user tuple.
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    //Get the user's username, email, password, birth year, security question, and answer
                    username = list.get(1);
                    password = list.get(4);
                    email = list.get(3);
                    birthyear = list.get(2);
                    squestion = list.get(5);
                    answer = list.get(6);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                //With the user information retreived, we can now initiate UserAccountInfo and instantiate the XML fields with the information.
                Intent infoActivity = new Intent(context, UserAccountInfo.class);
                infoActivity.putExtra("username",username);
                infoActivity.putExtra("password", password);
                infoActivity.putExtra("email",email);
                infoActivity.putExtra("birthyear",birthyear);
                infoActivity.putExtra("squestion",squestion);
                infoActivity.putExtra("answer",answer);
                context.startActivity(infoActivity);
            }
        }
        else if(type.equalsIgnoreCase("update"))
        {
            //If type equals "update" this means the user wants to update their account information.
        }
        else if(type.equalsIgnoreCase("delete"))
        {
            //If type equals "delete" this means the user wants to delete their account information.
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
