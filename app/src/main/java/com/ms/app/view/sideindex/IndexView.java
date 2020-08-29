package com.ms.app.view.sideindex;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.ms.app.R;;


public class IndexView extends View {

    //字体大小
    private float textSize;
    //字体颜色
    private int textColor;
    //画笔
    private Paint paint;

    /**
     * 字母数组
     */
    private final String[] letters = {
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z",
            "#"
    };

    public IndexView(Context context) {
        this(context, null);
    }


    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        //获取属性
        getProperty(typedArray);
        //初始化
        init();
    }

    private void getProperty(TypedArray typedArray) {
        textSize = typedArray.getDimension(R.styleable.IndexView_indexTextSize, 60);

        textColor = typedArray.getColor(R.styleable.IndexView_indexTextColor, Color.BLACK);
    }


    private void init() {

        //初始化画笔
        initPaint();

    }

    private void initPaint() {
        //实例化画笔
        paint = new Paint();
        //设置颜色
        paint.setColor(textColor);
        //设置字体大小
        paint.setTextSize(textSize);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置粗体字体
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    //条目的宽度
    private float itemWidth;
    //条目的高度
    private float itemHeight;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽度
        itemWidth = this.getMeasuredWidth();


        Log.e(TAG, "onMeasure:itemWidth  "+itemWidth );

        //每一个字母的高度
        itemHeight = this.getMeasuredHeight() / letters.length;

        Log.e(TAG, "onMeasure:itemHeight =  "+itemHeight  );
    }


    private static final String TAG = IndexView.class.getSimpleName();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < letters.length; i++) {

            String letter = letters[i];
            // 计算X坐标
            float x = itemWidth / 2 - paint.measureText(letter) / 2;
            //计算Y坐标
            float y = itemHeight / 2 + itemHeight / 2 + i * itemHeight;
            //画
            canvas.drawText(letter, x, y, paint);
        }

    }

    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float y = event.getY();

                int index = (int) (y / itemHeight);

                if (index != touchIndex) {
                    touchIndex = index;
                    invalidate();
                    if (indexViewLetterChangeListener != null && touchIndex >= 0 && touchIndex < letters.length) {
                        indexViewLetterChangeListener.onIndexLetterChange(letters[touchIndex]);
                    }
                }


                break;
            case MotionEvent.ACTION_UP:

                touchIndex = -1;
                invalidate();

                break;

        }
        return true;
    }


    public interface IndexViewLetterChangeListener {

        void onIndexLetterChange(String letter);


    }

    private IndexViewLetterChangeListener indexViewLetterChangeListener;


    /**
     * @param indexViewLetterChangeListener
     */
    public void setIndexViewLetterChangeListener(IndexViewLetterChangeListener indexViewLetterChangeListener) {
        this.indexViewLetterChangeListener = indexViewLetterChangeListener;
    }
}
