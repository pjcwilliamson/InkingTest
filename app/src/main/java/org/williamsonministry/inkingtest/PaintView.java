package org.williamsonministry.inkingtest;

import static android.view.MotionEvent.TOOL_TYPE_ERASER;
import static android.view.MotionEvent.TOOL_TYPE_FINGER;
import static android.view.MotionEvent.TOOL_TYPE_STYLUS;

import static org.williamsonministry.inkingtest.PathWithParams.ERASER;
import static org.williamsonministry.inkingtest.PathWithParams.MAGENTA;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PaintView extends View {
    private static final String TAG = "PaintView";

    public ViewGroup.LayoutParams params;
    private Paint brush = new Paint();
    private Paint eraser = new Paint();
    private int toolType = 0;
    private PathWithParams mPath;
    private ArrayList<PathWithParams> paths = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public PaintView(Context context) {
        super(context);

        brush.setAntiAlias(true);
        brush.setColor(Color.MAGENTA);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(3f);

        eraser.setAntiAlias(true);
        eraser.setColor(ContextCompat.getColor(context, R.color.gray));
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeWidth(50f);

//        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    // TODO: 11/23/2021 Add an eraser. This might help: https://stackoverflow.com/questions/18592169/drawing-multiple-paths-in-android 


    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        brush.setAntiAlias(true);
        brush.setColor(Color.MAGENTA);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(3f);

        eraser.setAntiAlias(true);
        eraser.setColor(Color.WHITE);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeWidth(50f);
    }

    public void init(int height, int width)  {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        toolType = event.getToolType(0);

//        int buttonState = event.getButtonState();
//
//        switch (buttonState) {
//            case BUTTON_PRIMARY:
//                Log.d(TAG, "onTouchEvent: BUTTON_PRIMARY");
//                break;
//            case BUTTON_SECONDARY:
//                Log.d(TAG, "onTouchEvent: BUTTON_SECONDARY");
//                break;
//            case BUTTON_TERTIARY:
//                Log.d(TAG, "onTouchEvent: BUTTON_TERTIARY");
//                break;
//            case BUTTON_STYLUS_PRIMARY:
//                Log.d(TAG, "onTouchEvent: BUTTON_STYLUS_PRIMARY");
//                break;
//            case BUTTON_STYLUS_SECONDARY:
//                Log.d(TAG, "onTouchEvent: BUTTON_STYLUS_SECONDARY");
//                break;
//            default:
//                break;
//        }

        if (toolType == TOOL_TYPE_STYLUS
                || toolType == TOOL_TYPE_FINGER
        ) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath = new PathWithParams(MAGENTA);
                    paths.add(mPath);
                    mPath.reset();
                    mPath.moveTo(pointX, pointY);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(pointX, pointY);
                    break;
                default:
                    return false;
            }
        }

        if (toolType == TOOL_TYPE_ERASER) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath = new PathWithParams(PathWithParams.ERASER);
                    paths.add(mPath);
                    mPath.reset();
                    mPath.moveTo(pointX, pointY);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(pointX, pointY);
                    invalidate();
                    break;
                default:
                    return false;
            }
        }
        postInvalidate();
        return false;
    }



    @Override
    protected void onDraw(Canvas canvas) {
//        switch (toolType) {
//            case TOOL_TYPE_STYLUS:
//                canvas.drawPath(drawPath, brush);
//                break;
//            case TOOL_TYPE_ERASER:
//                canvas.drawPath(erasePath, eraser);
//                break;
//            default:
//                break;
//        }
        canvas.save();

        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);

        for (PathWithParams p: paths) {
            switch (p.getType())    {
                case MAGENTA:
                    mCanvas.drawPath(p, brush);
                    break;
                case ERASER:
                    mCanvas.drawPath(p, eraser);
                    break;
                default:
                    break;
            }
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();

    }
}
