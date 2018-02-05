package com.example.visualartsnotebook;

/**
 * Created by okjat on 1/27/2018.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by okjat on 4/24/2017.
 * This is GetPhoto. It retrieves all the images the user has uploaded to the database.
 * @author Kersley Jatto
 */

public class GetPhoto extends AsyncTask<String, Void, String> {

    /**
     * This delegate will run processFinsih
     */
    public AsyncResponse delegate;

    /**
     * We implement the AsyncResponse interface
     */
    public interface AsyncResponse {
        void processFinish(String result);
    }

    /**
     *Get Photo constructor
     * @param delegate
     */
    GetPhoto(AsyncResponse delegate) {
        this.delegate = delegate;
    }


    public String doInBackground (String... params)
    {
        //The URL for the php that downloads pictures.
        String download_url = "http://kersleyojatto.com/projects/visualartsnotebook2/downloadPhoto.php";
        String result = "";
        String username = params[1];

        try {
            URL url = new URL(download_url);
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

            String line;

            while((line = bufferedReader.readLine()) != null){
                result = result+line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = "ERROR";
        } catch (IOException e) {
            e.printStackTrace();
            result = "ERROR";
        }
        return result;
    }

    @Override
    public void onPostExecute(String result){
        delegate.processFinish(result);
    }
}
