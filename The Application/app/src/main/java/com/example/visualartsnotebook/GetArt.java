package com.example.visualartsnotebook;

/**
 * Created by okjat on 1/27/2018.
 */

import android.os.AsyncTask;

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


public class GetArt extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate;

    public interface AsyncResponse {
        void processFinish(String result);
    }

    GetArt(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    public String doInBackground (String... params)
    {
        String download_url = "http://kersleyojatto.com/projects/visualartsnotebook2/downloadArt.php";
        String result = "";
        String username = params[1];
        String type = params[2];

        try {
            URL url = new URL(download_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String postData = URLEncoder.encode("UserName", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8") + "&" +
                    URLEncoder.encode("Private", "UTF-8")+"="+URLEncoder.encode(type, "UTF-8");
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
