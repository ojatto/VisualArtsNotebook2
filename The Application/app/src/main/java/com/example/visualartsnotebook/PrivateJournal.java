package com.example.visualartsnotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static java.security.AccessController.getContext;

/**
 * This is the PrivateJournal class. The class simulates a drawing canvas by creating a custom view and invoking
 * the Paint and Canvas classes. First, it sets the visual background based on the user's choice of a blank canvas,
 * canvas with a photo, or existing art project. From their the use can draw with various colors, adjust how they show
 * on the picture, erase, save, or go back.
 */
public class PrivateJournal extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener{

    /**
     * This is the username
     */
    private String username;
    /**
     * This refers to the art being saved as new or old
     */
    private String type;
    /**
     * This is referes to the variety of canvas
     */
    private String canvas;
    /**
     * This refers to the background type
     */
    private byte[] background;
    /**
     * The ID for prexisting/existing art
     */
    String artid;

    /**
     * The reference to our custom drawing view
     */
    MyView mv;

    /**
     * A reference to the paint class
     */
    private Paint       mPaint;
    /**
     * A variable that'll let us use emboss filter on the painting
     */
    private MaskFilter  mEmboss;
    /**
     * A variable that'll let use blur filer on the painting
     */
    private MaskFilter mBlur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_private_journal);

        //Get the username and canvas type
        username = getIntent().getStringExtra("username");
        canvas = getIntent().getStringExtra("canvas");

        //Instantiate the view
        mv= new MyView(this);

        mv.setDrawingCacheEnabled(true);

        //If canvas does not equal blank, we instantiate it with the provided bitmap which is either a photo
        //or an old art project
        if(!canvas.equalsIgnoreCase("blank"))
        {
            background = getIntent().getByteArrayExtra("bitmapImage");
            Bitmap decodedByte = BitmapFactory.decodeByteArray(background, 0, background.length);
            BitmapDrawable d = new BitmapDrawable(getResources(),decodedByte);
            mv.setBackgroundDrawable(d);
            //If an existing canvas is picked, we need to get the ID.
            if(canvas.equalsIgnoreCase("private"))
            {
                artid = getIntent().getStringExtra("id");
            }
        }
        setContentView(mv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
                0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    public class MyView extends View {

        private static final float MINP = 0.25f;
        private static final float MAXP = 0.75f;
        private Bitmap  mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint   mBitmapPaint;
        Context context;

        public MyView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            //showDialog();
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;

        }
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
            //mPaint.setMaskFilter(null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:

                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
    private static final int BLUR_MENU_ID = Menu.FIRST + 2;
    private static final int ERASE_MENU_ID = Menu.FIRST + 3;
    private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;
    private static final int Save = Menu.FIRST + 5;
    private static final int Save_As = Menu.FIRST + 6;
    private static final int Back = Menu.FIRST + 7;
    private static final int Help = Menu.FIRST + 8;

    /**
     * This creates the option menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
        menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 'e');
        menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'l');
        menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'r');
        menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');
        menu.add(0, Save, 0, "Save").setShortcut('1', 's');
        menu.add(0, Save_As, 0, "Save As").setShortcut('2', 'n');
        menu.add(0, Back, 0, "Back").setShortcut('8', 'b');
        menu.add(0, Help, 0, "Help").setShortcut('9', 'h');

        return true;
    }

    /**
     * This prepares the menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    /**
     * This creates a menu of drawing options in the upper right corner. The user can draw with various colors,
     * emboss, blur, put the cache above what's been drawn, erase, save new art, and save old art.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case COLOR_MENU_ID: {
                new ColorPickerDialog(this, this, mPaint.getColor()).show();
                return true;
            }
            case EMBOSS_MENU_ID: {
                if (mPaint.getMaskFilter() != mEmboss) {
                    mPaint.setMaskFilter(mEmboss);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            }
            case BLUR_MENU_ID:
            {
                if (mPaint.getMaskFilter() != mBlur) {
                    mPaint.setMaskFilter(mBlur);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            }
            case ERASE_MENU_ID:
            {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mPaint.setAlpha(0x80);
                return true;
            }
            case SRCATOP_MENU_ID:
            {
                mPaint.setXfermode(new PorterDuffXfermode(
                        PorterDuff.Mode.SRC_ATOP));
                mPaint.setAlpha(0x80);
                return true;
            }
            case Save_As: {
                AlertDialog.Builder editalert = new AlertDialog.Builder(PrivateJournal.this);
                editalert.setTitle("Please Enter the name with which you want to Save this new file");
                final EditText input = new EditText(PrivateJournal.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                editalert.setView(input);
                editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(input.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Input is empty!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Get the current cache and save it as a bitma
                            Bitmap bitmap2 = mv.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] yourByteArray;
                            yourByteArray = baos.toByteArray();
                            String artName = input.getText().toString();
                            //Encode the new art image as a string
                            String encodedImage = Base64.encodeToString(yourByteArray, Base64.DEFAULT);
                            username = getIntent().getStringExtra("username");
                            saveNewImage(artName, encodedImage, username);

                        }
                    }
                });
                editalert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                editalert.show();
                return true;
            }
            case Save:
            {
                AlertDialog.Builder editalert = new AlertDialog.Builder(PrivateJournal.this);
                editalert.setTitle("Please Enter the name with which you want to Save");
                final EditText input = new EditText(PrivateJournal.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                editalert.setView(input);
                editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Input is empty!", Toast.LENGTH_LONG).show();
                        } else {
                            Bitmap bitmap2 = mv.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                            byte[] yourByteArray;
                            yourByteArray = baos.toByteArray();
                            //Log.d("Art", "------------> Length: " + yourByteArray.length);
                            String artName = input.getText().toString();


                            String encodedImage = Base64.encodeToString(yourByteArray, Base64.DEFAULT);
                            saveOldImage(artName, encodedImage, username, artid);
                        }
                    }
                });
                editalert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


                editalert.show();
                return true;
            }
            case Back:
            {
                //Make sure we clean up.
                try {
                    mv.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mv.setDrawingCacheEnabled(false);
                }
                Intent intent = new Intent(this, ChoosePrivateCanvas.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            case Help:
            {
                AlertDialog.Builder helpalert = new AlertDialog.Builder(PrivateJournal.this);
                helpalert.setTitle("Using the Artbook:");
                helpalert.setMessage("Color: The initial drawing color is red. You can choose a color buy clicking the " +
                        "color button. This brings up the color wheel. To change the color, click a new color on the wheel " +
                        "and then click the ball in the center to establish it.\n" +
                        "Emboss: This make the draw line to pop to the front above everything else. You can cancel it by " +
                        "clicking it again.\n" +
                        "Blur: This makes the coloring line blurry and fuzzy. You can cancel it by clicking it again.\n" +
                        "Erase: This makes a black line appear where you draw. Once you let go, everything you drew will " +
                        "be erased.\n" +
                        "SrcAtop: This makes your next line disappear below the canvas. Good for planning your art without needed to erase. " +
                        "Save: This lets you save your existing project. \n" +
                        "Save As: This lets you save your work as a new project. \n" +
                        "Back: This lets you go back and choose a different canvas to work on");
                helpalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                helpalert.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This saves the current cache as a new art project
     * @param artName The new art title
     * @param encodedImage Tehe encoded image
     * @param usernameL The username of the creator
     */
    public void saveNewImage(String artName, String encodedImage, String usernameL)
    {
        //Only invoke our AsyncTask if the filename given isn't empty.
        if(artName != null && !artName.isEmpty())
        {
            //Now we upload our image.
            type = "new";

            SaveArt save = new SaveArt(this, new SaveArt.AsyncResponse() {
                @Override
                public void processFinish(String result) {
                    Log.d("Art", "RESULT: " + result);
                    artid = result;
                    Log.d("Art", "New ID: " + artid);
                }
            });
            save.execute(type, artName, encodedImage, usernameL);
        }
    }

    /**
     * This method saves an existing art project
     * @param artName The art title
     * @param encodedImage The encoded canvas
     * @param usernameL The user name
     * @param id The project ID
     */
    public void saveOldImage(String artName, String encodedImage, String usernameL, String id)
    {
        //Only invoke our AsyncTask if the filename given isn't empty.
        if(artName != null && !artName.isEmpty())
        {
            //Now we upload our image.
            type = "old";

            SaveArt save = new SaveArt(this, new SaveArt.AsyncResponse() {
                @Override
                public void processFinish(String result) {
                    Log.d("Art", "Art ID: " + artid);
                }
            });
            if(artid != null && !artid.isEmpty())
            {
                save.execute(type, artName, encodedImage, usernameL, id);
            }
            else
            {
                AlertDialog.Builder emptyalert = new AlertDialog.Builder(PrivateJournal.this);
                emptyalert.setMessage("You haven't saved this project yet! Use Save As first to save the project." +
                        " Then you can update it with Save.");
                emptyalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                emptyalert.show();
            }
        }
    }



}
