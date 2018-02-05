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
 * This is ChoosePicture. This class creates an activity that
 */
public class ChoosePicture extends AppCompatActivity {

    /**
     * This holds the username
     */
    private String username;
    /**
     * This holds the canvas type
     */
    private String canvas;
    /**
     * This holds the artbook type
     */
    private String artbook;
    /**
     * This holds the parameter list
     */
    public String params = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_picture);

        //Instantiate our fields with the intent values
        artbook = getIntent().getStringExtra("artbook");
        username = getIntent().getStringExtra("username");
        canvas = getIntent().getStringExtra("canvas");


        //Create an instance of GetPhoto with an AsyncResponse. The code inside processFinish will be executed
        //each time an AsyncTask session finishes

        GetPhoto asyncTask = new GetPhoto(new GetPhoto.AsyncResponse() {

            //Async gives us access to the Photo strings by being invoking in GetPhoto with result as an argument
            @Override
            public void processFinish(String result) {
                ArrayList<Bitmap> array = new ArrayList<Bitmap>(); //This will hold the decoded bitmpas.
                String[] strValues = result.split("NEXT IMAGE ENCODED STRING:"); //This gets encoded bitmaps
                ArrayList<String> rset = new ArrayList<String>(Arrays.asList(strValues));

                //We loop through and decode each bitmap, placing it in the Bitmap ArrayList.
                for (int i = 0; i < rset.size(); i++) {
                    byte[] b = Base64.decode(rset.get(i), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);
                    array.add(i, decodedByte);
                }
                //Call our method.
                grid(array);

            }
        });
        //Without our AsyncResponse defined we, execute.
        asyncTask.execute(params, username);

    }

    /**
     * This method fills the grid sectors in our XML's grid view with equal size pictures.
     * @param array The array of bitmaps to be decoded as pictures
     */
    public void grid(ArrayList<Bitmap> array) {
        final ArrayList<Bitmap> here = array;
        //Get a reference to the GridView
        GridView gridView = (GridView) findViewById(R.id.gridView2);
        //Adapt the bitmaps into the grid view
        gridView.setAdapter(new ImageAdapter(this, array));

        //We essentially make a button out of each picture. When piciked, it'll be sent to PrivateJournal
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ByteArrayOutputStream ByteStream=new ByteArrayOutputStream();
                here.get(position).compress(Bitmap.CompressFormat.JPEG,25, ByteStream);
                byte [] b = ByteStream.toByteArray();
                Intent startActivity = new Intent(ChoosePicture.this, PrivateJournal.class);
                startActivity.putExtra("bitmapImage", b);
                startActivity.putExtra("artbook", artbook);
                startActivity.putExtra("username", username);
                startActivity.putExtra("canvas", canvas);
                startActivity(startActivity);
            }
        });
    }

    /**
     * This sends gets us back to a choice of canvas
     * @param view
     */
    public void backToCanvas(View view)
    {
        Intent intent = new Intent(this, ChoosePrivateCanvas.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


}
