package com.example.visualartsnotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the class ViewPhotos. It lets a user see the pictures they've uploaded
 * @author Kersley Jatto
 */
public class ViewPhotos extends AppCompatActivity {

    /**
     * This holds username
     */
    private String username;
    /**
     * An array of params
     */
    public String params = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

        //Get the username
        username = getIntent().getStringExtra("username");

        //The following AsyncTask will take giant string of image strings delimited by the message
        //NEXT IMAGE ENCODED STRING, and decode them back to bitmaps which are stored in an array.
        GetPhoto asyncTask = new GetPhoto(new GetPhoto.AsyncResponse() {

            @Override
            public void processFinish(String result) {
                ArrayList<Bitmap> array = new ArrayList<Bitmap>();
                String[] strValues = result.split("NEXT IMAGE ENCODED STRING:");
                ArrayList<String> rset = new ArrayList<String>(Arrays.asList(strValues));

                for (int i = 0; i < rset.size(); i++) {
                    Log.d("Photo", "String: " + rset.get(i));
                    byte[] b = Base64.decode(rset.get(i), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);
                    array.add(i, decodedByte);
                }
                grid(array);

            }
        });

        asyncTask.execute(params, username);
    }

    /**
     * Thi method sets all the user's pictures to the database
     * @param array
     */
    public void grid(ArrayList<Bitmap> array) {
        final ArrayList<Bitmap> here = array;

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, array));

    }

    /**
     * This method lets the user return to ManagePHotos
     * @param view
     */
    public void backToMain(View view)
    {
        Intent intent = new Intent(this, ManagePhotos.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
