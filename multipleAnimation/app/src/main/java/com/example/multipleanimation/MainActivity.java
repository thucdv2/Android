package com.example.multipleanimation;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    TextView text1;
    TextView text2;
    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayout = findViewById(R.id.mainview);
        linearLayout.setOnTouchListener(mTouchListener);

        linearLayout2 = findViewById(R.id.move);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        float startPosX = 0f;
        float xOffset = 0f;
        float xOffset1 = 0f;
        float xOffset2 = 0f;
        float xOffset3 = 0f;


        float startPosY = 0f;
        float yOffset = 0f;

        long startTime = 0;
        long timeOffset = 0;

        int SEAT_HEAD_IMAGE_WIDTH = 279;
        int MAX_LEVEL = 10000;

        int X_OFFSET_MIN = 5;
        int Y_OFFSET_MIN = 20;
        int TIME_OFFSET_MIN = 220;

        Handler handler = new Handler();

        boolean cancel;

        Runnable longClickHandle = new Runnable() {

            @Override
            public void run() {
                if (!cancel) {

                }
            }
        };
        private int selected = 2;
        private int newSelected = 2;
        private TextView selectedView;
        private TextView prevSelectedView;
        private TextView nextSelectedView;
        private float text_width = 60f;

        float textSize1 = 0f;
        float textSize2 = 0f;
        float textSize3 = 0f;
        float linearOffset = 0f;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            boolean ret = false;
            if (linearLayout != null) {
//                Log.d("thucdv", "linearLayout2 x = " + linearLayout2.getX());
            }

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPosX = event.getX();
                    startPosY = event.getY();
                    startTime = System.currentTimeMillis();
                    cancel = false;
                    handler.postDelayed(longClickHandle, TIME_OFFSET_MIN);

                    xOffset1 = linearLayout2.getX();
                    prevSelectedView = (TextView) linearLayout2.getChildAt(selected - 2);
                    selectedView = (TextView) linearLayout2.getChildAt(selected - 1);
                    nextSelectedView = (TextView) linearLayout2.getChildAt(selected);
                    selectedView.setTextSize(65);
                    ret = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    xOffset = event.getX() - startPosX;
                    linearLayout2.setX(xOffset1 - xOffset);

                    newSelected = 2 - (int) (linearLayout2.getX() / text_width);
                    if (newSelected != selected) {
                        selected = newSelected;
                        prevSelectedView = (TextView) linearLayout2.getChildAt(selected - 2);
                        selectedView = (TextView) linearLayout2.getChildAt(selected - 1);
                        nextSelectedView = (TextView) linearLayout2.getChildAt(selected);

                        if (prevSelectedView != null) {
                            prevSelectedView.setTextSize(30);
                        }
                        if (selectedView != null) {
                            selectedView.setTextSize(65);
                        }
                        if (nextSelectedView != null) {
                            nextSelectedView.setTextSize(30);
                        }
                    }

                    linearOffset = Math.abs((int) linearLayout2.getX() % 60);

                    textSize1 = 30 - 35f * linearOffset / text_width;
                    if (textSize1 < 30) {
                        textSize1 = 30;
                    }
                    textSize2 = 65 - 35f * linearOffset / text_width;
                    textSize3 = 30 + 35f * linearOffset / text_width;

                    if (textSize2 < 30) {
                        textSize2 = 30;
                    }
                    if (textSize3 < 30) {
                        textSize3 = 30;
                    }

                    Log.d("thucdv", "textSize1 = " + textSize1 + ", textSize2 = " + textSize2 + ", textSize3 = " + textSize3);
                    if (prevSelectedView != null) {
                        prevSelectedView.setTextSize(textSize1);
                    }
                    if (selectedView != null) {
                        selectedView.setTextSize(textSize2);
                    }
                    if (nextSelectedView != null) {
                        nextSelectedView.setTextSize(textSize3);
                    }

                    ret = true;
                    break;
                case MotionEvent.ACTION_UP:
                    cancel = true;
                    ret = false;
                    break;
                default:
                    break;
            }
            return ret;
        }
    };
}
