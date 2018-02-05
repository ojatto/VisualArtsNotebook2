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
 * This is SaveArt. This Java Class using AsyncTask and implmeents AsyncResponse to save the user's artwork. Both new versions
 * * and distinct files.
 * Created by Kersley Jatto on 5/1/2017.
 */
public class SaveArt extends AsyncTask<String, Void, String>
{
    /**
     * This holds the context from the Journal classes
     */
    Context context;
    /**
     * This is the alert dialog.
     */
    AlertDialog alertDialog;
    /**
     * This holds whether we're holding a new image or old one.
     */
    String type;
    /**
     * This holds the art string
     */
    String art;
    /**
     * This holds the art name.
     */
    String artName;
    /**
     * This holds the username of the creator
     */
    private String creator;
    /**
     * This passes the artID back to the journal
     */
    String artID;
    /**
     * This holds the result of an AsyncTask session
     */
    private String actionToPerform;


    /**
     * This variable is used to invoke processFinish so that the Journals can access the ID returned from the PHP
     */
    public AsyncResponse delegate;

    /**
     * Here we declare the AsyncResponse interface and implement its key mehtod, processFinish which we code to
     * process the result of an AsyncTask session
     */
    public interface AsyncResponse {
        void processFinish(String result);
    }

    /**
     * The SaveArt constructor
     * @param ctx The context for the artbook activity
     * @param delegate This references the AsyncResult.
     */
    SaveArt(Context ctx, AsyncResponse delegate) {
        context = ctx;
        this.delegate = delegate;
    }

    /**
     * This method connects to the database without holding up the main thread
     * @param params The set of arguments given by the artbook
     * @return result This is the string echo'd by the PH{}
     */
    public String doInBackground (String... params)
    {
        //This PHP is for saving new art.
        String saveArt_url = "http://kersleyojatto.com/projects/visualartsnotebook2/saveArt.php";
        //This PHP is for updating existing art
        String updateArt_url = "http://kersleyojatto.com/projects/visualartsnotebook2/updateArt.php";

        //Get whether it's a new save or an update of old art
        type = params[0];

        try
        {
            if (type.equalsIgnoreCase("new")) {

                //Get the parameters from the execute method
                artName = params[1];
                art = params[2];
                creator = params[3];


                //Connnect to the database using the PHP URL. In doing so, we encode
                URL url = new URL(saveArt_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Art", "UTF-8") + "=" + URLEncoder.encode(art, "UTF-8") + "&"
                        + URLEncoder.encode("ArtTitle", "UTF-8") + "=" + URLEncoder.encode(artName, "UTF-8") + "&"
                        + URLEncoder.encode("Creator", "UTF-8") + "=" + URLEncoder.encode(creator, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                setActionToPerform(result);
                System.out.println(result + " HERE");
                return result;


            }
            else if(type.equalsIgnoreCase("old"))
            {
                //Here the user the art already exists. The field in the table must be updated, so we get the ID as well
                artName = params[1];
                art = params[2];
                creator = params[3];
                artID = params[4];


                URL url = new URL(updateArt_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Art", "UTF-8") + "=" + URLEncoder.encode(art, "UTF-8") + "&"
                        + URLEncoder.encode("ArtTitle", "UTF-8") + "=" + URLEncoder.encode(artName, "UTF-8") + "&"
                        + URLEncoder.encode("Creator", "UTF-8") + "=" + URLEncoder.encode(creator, "UTF-8") + "&"
                        + URLEncoder.encode("ArtID", "UTF-8") + "=" + URLEncoder.encode(artID, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                setActionToPerform(result);
                System.out.println(result + " HERE");
                return result;
            }
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This changes actionToPerform's value
     * @param actionToPerform
     */
    public void setActionToPerform(String actionToPerform) {
        this.actionToPerform = actionToPerform;
    }

    /**
     * A progress method for AsyncTask
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    /**
     * This prints actionToPerform and returns it
     * @return
     */
    public String fetchStatus() {

        System.out.println(actionToPerform);
        return actionToPerform;
    }

    /**
     * This method builds our AlertDialog prior to thread's work
     */
    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();

    }

    /**
     * This method takes the result of the appropriate PHP file and interacts with our app accordingly
     * @param result
     */
    @Override
    protected void onPostExecute(String result){
        actionToPerform = fetchStatus();

        if(type.equalsIgnoreCase("new")) {
            //If we saved a new project we first make sure a new ID was created with isNumberic. Otherwise, we output the corresponding
            //errors.
            if (isNumeric(actionToPerform)) {
                alertDialog.setMessage("Your art project as been saved to your journal!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //If the new project was successfully added, we use delegate to give the artID to the drawing activity
                        delegate.processFinish(actionToPerform);
                    }
                });
                alertDialog.show();

            } else if (actionToPerform.equalsIgnoreCase("The name exists")) {
                alertDialog.setMessage("You have an item with this title already. Please choose a different title.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            } else {
                alertDialog.setMessage("Image upload unsuccessful.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
        else if(type.equalsIgnoreCase("old"))
        {
            //In the case of updating a previous project, we make the PHP outputs "Saved" which shows a successful update.
            if (actionToPerform.equalsIgnoreCase("Saved")) {
                alertDialog.setMessage("Your art project and its title have been updated!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("Does not exist"))
            {
                alertDialog.setMessage("This project isn't in your journal yet. Use Save As first so you can update with Save.");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
            else
            {
                alertDialog.setMessage("The update was unsuccessful...");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        }
    }

    /**
     * This method checks that a string is a number
     * @param str The string to checked for numerical value
     * @return
     */
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
