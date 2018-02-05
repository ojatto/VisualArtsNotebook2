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

/**
 * Created by okjat on 4/30/2017.
 * The GetCourses class is a Java Class that utilizes AsyncResponse to retrieve all the courses the user
 * is teaching.
 * @author Kersley Jatto
 */

public class GetCourses extends AsyncTask<String, Void, String>
{
    /**
     * The delegate that invokes processFinish
     */
    public AsyncResponse delegate;

    /**
     * We implement the AsyncResponse interface
     */
    public interface AsyncResponse {
        void processFinish(String result);
    }

    /**
     * The constructor for GetCourses
     * @param delegate
     */
    GetCourses(GetCourses.AsyncResponse delegate) {
        this.delegate = delegate;
    }

    /**
     * doInBackground connects to the database to get the courses
     * @param params
     * @return
     */
    public String doInBackground (String... params)
    {
        String upload_url = "http://kersleyojatto.com/projects/visualartsnotebook2/getCourse.php";
        String result = "";
        String username = params[1];

        try {
            URL url = new URL(upload_url);
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

    /**
     * Invokes processFinish with result once the AsyncTask thread is done.
     * @param result
     */
    @Override
    public void onPostExecute(String result){
        delegate.processFinish(result);
    }
}
