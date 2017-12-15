package com.joshpayne1.androidartist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by joshpayne1 on 12/14/17.
 */


public class CustomView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint bitmapPaint;
    private Paint paint;
    private float x, y;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void clearCanvas() {
        canvas.drawColor(getResources().getColor(R.color.default_color));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eX = event.getX();
        float eY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(eX, eY);
                x = eX;
                y = eY;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(x, y, (eX+x)/2, (eY+y)/2);
                x = eX;
                y = eY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                canvas.drawPath(path, paint);
                path.reset();
                invalidate();
                break;
        }
        return true;
    }

    public Bitmap getBitmap() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

    public void setPathColor(int color) {
        paint.setColor(color);
    }
}
