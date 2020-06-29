package com.example.treealtimeter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;


public class PaintView extends View {


    private Paint p;

    private Bitmap mBitmap;

    boolean log = true;

    //массив координат точек
    float[] x = {200,200,200,200};
    float[] y = {200,200,200,200};



    int m = 0;
    int w,h;


    //конструктор
    public PaintView(Context context) {
        this(context, null);
    }

    //конструктор
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        //mPaint.setAntiAlias(true);
        //mPaint.setDither(true);
        //mPaint.setColor(DEFAULT_COLOR);
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeJoin(Paint.Join.ROUND);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
        //mPaint.setXfermode(null);
        //mPaint.setAlpha(0xff);
        //mEmboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 6, 3.5f);
        //mBlur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }

    //загрузка картинки из файла
    public static Bitmap loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) { return null; }
            Bitmap tmp = BitmapFactory.decodeFile(filename);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }


    public void init(int w, int h, String path) {

        //загрузка картинки из файла
        Bitmap test = loadFromFile(path);

        this.w = w;
        this.h = h;

        //создание bitmap для отрисовки фото
        mBitmap = Bitmap.createScaledBitmap(test, (int) (w*0.9), (int) (w*0.9*1.3333), false);

    }



    //метод для отрисовки точек и фото
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //фото
        canvas.drawBitmap(mBitmap, (int) (w*0.05), 0, p);


        p.setStrokeWidth(3);

        //точки
        for(int k=0; k<=m; k++) {
            if(k==0||k==1){p.setColor(Color.RED);}
            else{p.setColor(Color.GREEN);}

            canvas.drawCircle(x[k], y[k], 20, p);
        }
        canvas.restore();
    }

    //нажатие пальца по экрану
    private void touchStart(float x, float y) {
        if(log) {
            this.x[m] = x;
            this.y[m] = y;
        }
    }
    //движение пальца по экрану
    private void touchMove(float x, float y) {
        if(log) {
            this.x[m] = x;
            this.y[m] = y;
        }
    }

    private void touchUp() {
        //mPath.lineTo(mX, mY);
    }

    //обработка нажатий
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = (float) event.getX();
        float y = (float) event.getY();


        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    //добавление точки
    public void add() {
        m++;
        invalidate();
    }

    //подсчет высоты
    public float calculate() {
        log = false;
        return (float) (Math.round((Math.abs(y[3]-y[2])/Math.abs(y[1]-y[0]))*100.0)/100.0);

    }
}
