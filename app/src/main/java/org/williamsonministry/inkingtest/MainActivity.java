package org.williamsonministry.inkingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

    //From: https://jerrybanfield.com/program-your-own-drawing-application-in-android-studio/
    //Maybe this: https://www.geeksforgeeks.org/how-to-create-a-paint-application-in-android/

    //Pen distinction: https://stackoverflow.com/questions/11563191/is-there-any-way-to-check-if-device-supports-stylus-input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PaintView paintView = findViewById(R.id.paintView);

        // pass the height and width of the custom view
        // to the init method of the PaintView object
        ViewTreeObserver vto = paintView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paintView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paintView.getMeasuredWidth();
                int height = paintView.getMeasuredHeight();
                paintView.init(height, width);
            }
        });
    }
}