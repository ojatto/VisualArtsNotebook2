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
 * Created by okjat on 4/23/2017.
 * This is SendPhoto. When a Photo is chosen from the user's storage/takes a picture, this class sends
 * and encoded version of it to the MySQL database
 */

public class SendPhoto extends AsyncTask<String, Void, String>
{
    /**
     * The context
     */
    Context context;
    /**
     * The alert dialog
     */
    AlertDialog alertDialog;
    /**
     * This encoded image
     */
    String img;
    /**
     * The username of the uploader
     */
    String owner;
    /**
     * The result of the task
     */
    private String actionToPerform;

    /**
     * A constructor
     * @param ctx
     */
    SendPhoto(Context ctx) {
        context = ctx;
    }

    /**
     * We connect to the database and send in the string
     * @param params
     * @return
     */
    public String doInBackground (String... params)
    {
        String upload_url = "http://kersleyojatto.com/projects/visualartsnotebook2/uploadPhoto.php";

        try
        {
            owner = params[0];
            img = params[1];



            URL url = new URL(upload_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("Owner", "UTF-8")+"="+URLEncoder.encode(owner, "UTF-8")+"&"
                    + URLEncoder.encode("Image", "UTF-8")+"="+URLEncoder.encode(img, "UTF-8");
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
            Log.d("Photo", "Here's the result:" + result);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mutator for actionToPerform
     * @param actionToPerform
     */
    public void setActionToPerform(String actionToPerform) {
        this.actionToPerform = actionToPerform;
    }

    /**
     * Current progress
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    /**
     * Getter for actionToPerform
     * @return
     */
    public String fetchStatus() {

        System.out.println(actionToPerform);
        return actionToPerform;
    }

    /**
     * Build the alert dialog on the prexecution
     */
    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();

    }

    /**
     * Post Execute tells the user if their upload was successful or not
     * @param result
     */
    @Override
    protected void onPostExecute(String result){
        actionToPerform = fetchStatus();

        if(actionToPerform.equalsIgnoreCase("Successfully added")){
            alertDialog.setMessage("Your picture has been added to your Photo Library. You can use it for art now!");

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    //String username = owner;
                    Intent intent = new Intent(context, ManagePhotos.class);
                    intent.putExtra("username", owner);
                    context.startActivity(intent);
                }
            });
            alertDialog.show();

        }

        else {
            alertDialog.setMessage("Image upload unsuccessful.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();}
            });
            alertDialog.show();
        }

    }
}
