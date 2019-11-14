package com.agilefinger.labourservice.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CompletedXianView extends View {

    //-------------View相关-------------
    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    //-------------统计图相关-------------
    //x轴的条目
    private int xNum = 6;
    //y轴的条目
    private int yNum = 5;
    //y轴条目之间的距离
    private int ySize = 120;
    //x轴条目之间的距离
    private int xSize = 140;
    //y轴的长度,11个条目只有10段距离
    private int yLastSize = (yNum - 1) * ySize;

    //-------------必须给的资源相关-------------
    private String[] xStr = new String[]{"7/11", "7/12", "7/13", "7/14", "7/15", "7/16", "7/17"};
    private String[] yStr = new String[]{"0", "20", "40", "60", "80"};
    private String str = "新增整改";
    //折线表示的最大值,取yStr的最大值
    private int yMaxValue = Integer.parseInt(yStr[yStr.length - 1]);
    //折线真实值
    private int[] yValue = new int[]{8, 20, 40, 20, 30, 10, 80};

    //-------------画笔相关-------------
    //边框的画笔
    private Paint borderPaint;
    //文字的画笔
    private Paint textPaint;
    //折线的画笔
    private Paint linePaint;
    //蓝点的画笔
    private Paint pointPaint;
    //白点点的画笔
    private Paint pointPaint2;

    //-------------颜色相关-------------
    //边框颜色
    private int mColor = 0xFFe5e5e5;
    //文字颜色
    private int textColor = 0xFF333333;
    //折线颜色
    private int lineColor = 0xFF999999;
    //蓝点颜色
    private int pointColor = 0xFF33A0FF;
    //白点颜色
    private int pointColor2 = 0xFFFFFFFF;



    /**
     * View宽度
     */
    private int mViewWidth;
    /**
     * View高度
     */
    private int mViewHeight;

    /**
     * 边框的左边距
     */
    private float mBrokenLineLeft = 40;
    /**
     * 边框的上边距
     */
    private float mBrokenLineTop = 40;
    /**
     * 边框的下边距
     */
    private float mBrokenLineBottom = 40;
    /**
     * 边框的右边距
     */
    private float mBrokenLinerRight = 20;
    /**
     * 需要绘制的宽度
     */
    private float mNeedDrawWidth;
    /**
     * 需要绘制的高度
     */
    private float mNeedDrawHeight;

    /**
     * 图表的最大值
     */
    private int maxVlaue = 40;


    public CompletedXianView(Context context) {
        super(context);
    }

    public CompletedXianView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompletedXianView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        initNeedDrawWidthAndHeight();
    }

    /**
     * 初始化绘制折线图的宽高
     */
    private void initNeedDrawWidthAndHeight() {
        mNeedDrawWidth = mViewWidth - mBrokenLineLeft - mBrokenLinerRight;
        mNeedDrawHeight = mViewHeight - mBrokenLineTop - mBrokenLineBottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化画笔
        initPaint();
        //画布移到左下角，留出100的空间给予文字填充
        canvas.translate(100, mHeight - 100);
        //画边框
        drawBorder(canvas);
        //画黑点
//        drawPoint(canvas);
        //画文字
        drawText(canvas);
        //画折线
        drawLine(canvas);
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(mColor);
        borderPaint.setStrokeWidth(1);
        //文字画笔
        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        //区域画笔
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        //蓝点画笔
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.BLUE);


        //白点画笔
        pointPaint2 = new Paint();
        pointPaint2.setAntiAlias(true);
        pointPaint2.setStyle(Paint.Style.FILL);

        pointPaint2.setColor(Color.WHITE);
    }


    /**
     * 画边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < yNum; i++) {
            //一条竖直的线
            if (i == 0) {
                path.moveTo(0, -i * ySize);
                path.lineTo(0, -(yNum - 1) * ySize);
            }
            //循环水平的线
            path.moveTo(0, -i * ySize);
            path.lineTo(xNum * xSize, -i * ySize);
            canvas.drawPath(path, borderPaint);
        }
    }

    /**
     * 画黑点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        for (int i = 0; i <= xNum; i++) {

            canvas.drawCircle((i + 1) * xSize, 0, 5, pointPaint);

        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //事先说明：文字排版为了好看，这里的20，都为20px的边距
        //x轴的文字
        for (int i = 0; i < xStr.length; i++) {
            //测量文字的宽高
            Rect rect = new Rect();
            textPaint.getTextBounds(xStr[i], 0, xStr[i].length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(xStr[i], i * xSize - textWidth / 2, textHeight + 20, textPaint);
        }
        //y轴的文字
        for (int i = 0; i < yStr.length; i++) {
            //测量文字的宽高
            Rect rect = new Rect();
            textPaint.getTextBounds(yStr[i], 0, yStr[i].length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(yStr[i], -textWidth - 20, i * (-ySize) + (textHeight / 2), textPaint);
        }
        //顶部文字
        canvas.drawText(str, 0, (-ySize) * (yStr.length - 1) - 30, textPaint);
    }

    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < yValue.length; i++) {
            //计算折线的位置：（当前点的值/最大值）拿到百分比percent
            //用百分比percent乘与y轴总长，就获得了折线的位置
            //这里拿到的百分比一直为0，所以换一种方法，先乘与总长再除与最大值，而且记得加上负号
            float position = -(yValue[i] * yLastSize / yMaxValue);


            if (i == 0) {
                //第一个点需要移动
                path.moveTo(i * xSize, position);
            } else {
                //其余的点直接画线
                path.lineTo(i * xSize, position);
            }
            canvas.drawPath(path, linePaint);

            //画蓝点
            canvas.drawCircle(i * xSize, position, 8, pointPaint);
            //画白点
            canvas.drawCircle(i * xSize, position, 6, pointPaint2);

        }
    }
}
