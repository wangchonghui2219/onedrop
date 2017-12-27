package com.dlwx.onedrop.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/12/4/004.
 */


@SuppressLint("AppCompatCustomView")
public class CircleImageViews extends ImageView {
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private Bitmap mBitmap;
    private Paint paint = new Paint();
    private Paint borderPaint = new Paint();
    private RectF mRect;//矩形凹行大小
    private int mRoudSize;//圆角半径
    private int type;//设置圆角矩形或者圆
    public static int RECT = 2;//矩形
    public static int  CIRCLE = 1 ;//圆

    public CircleImageViews(Context context) {
        super(context);
    }

    public CircleImageViews(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleImageViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public void setmRoudSize(int type,int mRoudSize){
        this.mRoudSize = mRoudSize;
        this.type = type;
        setUp();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        if (type == 1) {
            canvas.drawCircle(w / 2, h / 2, w/2, paint);
        canvas.drawCircle(w / 2, h / 2, (w-3)/2, borderPaint);
        }else{
            canvas.drawRoundRect(mRect, 20, 20, paint);
        }
    }
    private int w;
    private int h;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        mRect = new RectF(0,0, getWidth(), w/4*3);
       setUp();
    }




    @Override
    public void setImageDrawable( Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setUp();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setUp();
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
    private void setUp(){
        if (mBitmap == null) {
            return;
        }
        paint.setAntiAlias(true);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scalewidth = w/width;
        float scaleheight = h/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scalewidth,scalewidth);
        Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3);
        borderPaint.setFilterBitmap(true);
        borderPaint.setColor(Color.parseColor("#4188f9"));
        invalidate();
    }

}
