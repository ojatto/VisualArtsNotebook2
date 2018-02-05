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
import java.util.regex.Pattern;

public class ChoosePrivateProject extends AppCompatActivity {

    private String username;
    private String canvas;
    public String params = "";
    private ArrayList<ArtEntry> artList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_private_project);

        username = getIntent().getStringExtra("username");
        canvas = getIntent().getStringExtra("canvas");

        artList = new ArrayList<ArtEntry>();
        /**
        GetArt asyncTask = new GetArt(new GetArt.AsyncResponse() {

            @Override
            public void processFinish(String result) {
                Log.d("Art", "WE GOT HERE 2");
                ArrayList<Bitmap> array = new ArrayList<Bitmap>();
                String[] strValues = result.split("NEXT ART ENCODED STRING:");
                ArrayList<String> rset = new ArrayList<String>(Arrays.asList(strValues));

                for (int i = 0; i < rset.size(); i++) {

                    Log.d("Art", "Core Line: " + rset.get(i));
                    String line[] = rset.get(i).split(Pattern.quote("4--$--4"));
                    Log.d("Art", "ID: " + line[0]);
                    Log.d("Art", "String: " + line[1]);
                    byte[] b = Base64.decode(line[1], Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(b, 0, b.length);
                    artList.add(i, new ArtEntry(line[0], decodedByte));
                    array.add(i, decodedByte);
                }
                grid(array);

            }
        });

        String p = "private";
        asyncTask.execute(params, username, p);
         */
    }

    public void grid(ArrayList<Bitmap> array) {
        /**
        final ArrayList<Bitmap> here = array;
        GridView gridView = (GridView) findViewById(R.id.gridView3);

        gridView.setAdapter(new ImageAdapter(this, array));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
                here.get(position).compress(Bitmap.CompressFormat.JPEG, 100, ByteStream);
                byte[] b = ByteStream.toByteArray();
                Intent startActivity = new Intent(ChoosePrivateProject.this, PrivateJournal.class);
                String ref = artList.get(position).getTheID();
                startActivity.putExtra("bitmapImage", b);
                startActivity.putExtra("username", username);
                startActivity.putExtra("canvas", canvas);
                startActivity.putExtra("id", ref);
                startActivity(startActivity);
            }
        });
         */
    }


    public void backToCanvas2(View view) {
        Intent intent = new Intent(this, ChoosePrivateCanvas.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private class ArtEntry
    {
        String theID;
        Bitmap theBitmap;

        public ArtEntry(String id, Bitmap bit) {
            this.theID = id;
            this.theBitmap = bit;
        }

        public String getTheID() {
            return theID;
        }

        public Bitmap getTheBitmap(){
            return theBitmap;
        }
    }

}