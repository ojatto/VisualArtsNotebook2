package com.example.visualartsnotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the UploadPictures activity. It lets a user choose between taking a picture on the spot or
 * using a picture from memory.
 * @author Kersley Jatto
 */
public class UploadPictures extends AppCompatActivity {

    /**
     * This permission is used to get the device's camera
     */
    private static final int CAMERA_REQUEST = 1888;
    /**
     * This permission is used to get to the device's picture stash
     */
    private static final int CAMERA_ROLL = 1889;

    /**
     * This holds the photo chosen as a bitmap
     */
    private static Bitmap photo;
    /**
     * This holds the username
     */
    private String username;

    /**
     * This supports our multiple permissions
     */
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pictures);

        checkAndRequestPermissions();

        ///We instanitate our buttons and username
        ImageButton rollButton = (ImageButton) findViewById(R.id.cameraRoll);
        ImageButton photoButton = (ImageButton) findViewById(R.id.cameraButton);
        username = getIntent().getStringExtra("username");

        //We set the photo button to request the device's camera upon invocation
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        //We set the roll button to request the device's picture storage upon invocation
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select a Source"), CAMERA_ROLL);
            }
        });
    }

    /**
     * This boolean method verifies that our permissions for the various elements are valid
     * @return
     */
    private  boolean checkAndRequestPermissions() {


        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) data.getExtras().get("data");

        }
        else  {
            String [] path = {MediaStore.Images.Media.DATA};

            if(data != null) {
                Cursor c = getContentResolver().query((Uri) data.getData(), path, null, null, null);
                c.moveToFirst();

                String filePath = c.getString(c.getColumnIndex(path[0]));
                c.close();

                photo = BitmapFactory.decodeFile(filePath);
            }
        }

        if(photo != null) {
            /* convert photo to byte stream, then byte array, then encoded string */
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 10, stream);
            byte[] img = stream.toByteArray();
            String encodedImage = Base64.encodeToString(img, Base64.DEFAULT);


            /* upload photo to database as encoded string */
            SendPhoto upload = new SendPhoto(this);
            upload.execute(username, encodedImage);
        }

    }

    /**
     * Return to the Post Login activity
     */
    public void goBackPost(View view)
    {
        Intent intent = new Intent(this, PostLoginMenu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
