package com.example.visualartsnotebook; /**
 * Created by okjat on 1/27/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

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
 * Created by okjat on 4/30/2017.
 */

public class CourseControl extends AsyncTask<String, Void, String>
{
    Context context;
    AlertDialog alertDialog;
    String type;
    private String actionToPerform;
    private String username;

    public CourseControl(Context con)
    {
        context = con;
    }

    public String doInBackground(String...params)
    {
        //Get the value of type from Register.java. If it equals register, the following code accesses the PHP files.
        type = params[0];

        String makeCourseURL = "http://kersleyojatto.com/projects/visualartsnotebook2/makeCourse.php";
        String getCourseURL = "http://kersleyojatto.com/projects/visualartsnotebook2/getCourse.php";

        if(type.equalsIgnoreCase("make")) {
            username = params[1];
            String courseName = params[2];
            try {
                //Create a URL reference
                URL url = new URL(makeCourseURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("CourseName", "UTF-8") + "=" + URLEncoder.encode(courseName, "UTF-8");
                bufferedWriter.write(postData);
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result)
    {
        actionToPerform = fetchStatus();

        if(type.equals("make"))
        {
            if(actionToPerform.equalsIgnoreCase("Successfully added")) {
                alertDialog.setMessage("Your course has been made!");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /**
                        Intent intent = new Intent(context, TeacherCourses.class);
                        intent.putExtra("username", username);
                        context.startActivity(intent);
                         */

                    }
                });
                alertDialog.show();
            }
            else if(actionToPerform.equalsIgnoreCase("exists"))
            {
                alertDialog.setMessage("You've already made a course with this title. ");
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
                alertDialog.setMessage("There was an error with making the course...");
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

    public String fetchStatus() {
        System.out.println(actionToPerform);
        return actionToPerform;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
    }




}