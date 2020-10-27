package com.crazyandcoder.top.customeview.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.crazyandcoder.top.customeview.R;
import com.crazyandcoder.top.customeview.utils.DimenUtil;

import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;

/**
 * 圆环进度条
 */
public class CircleProgressView extends View {

    private int progressColor;    //进度的颜色
    private float circleRadius;    //外圆半径大小
    private int bgColor;    //背景颜色
    private int progressTextColor;   //圆环内文字颜色
    private float progressTextSize;    //圆环内文字大小
    private float progressWidth;    //圆环的宽度
    private int maxProgress;    //最大进度
    private float progress = 0f;    //当前进度
    private int direction;    //进度从哪里开始(设置了4个值,上左下右)

    private Paint paint;
    private String progressText;     //圆环内文字
    private Rect rect;

    /**
     * 圆环内显示的文字
     */
    private String text;

    private ValueAnimator animator;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);
        progressColor = a.getColor(R.styleable.CircleProgressBar_progress_color, ContextCompat.getColor(getContext(), R.color.colorPrimary));
        circleRadius = a.getDimension(R.styleable.CircleProgressBar_circle_radius, DimenUtil.dp2px(getContext(), 60.0f));
        bgColor = a.getColor(R.styleable.CircleProgressBar_bg_color, ContextCompat.getColor(getContext(), R.color.inside_color));
        progressTextColor = a.getColor(R.styleable.CircleProgressBar_progress_text_color, ContextCompat.getColor(getContext(), R.color.colorPrimary));
        progressTextSize = a.getDimension(R.styleable.CircleProgressBar_progress_text_size, DimenUtil.dp2px(getContext(), 14.0f));
        progressWidth = a.getDimension(R.styleable.CircleProgressBar_progress_width, DimenUtil.dp2px(getContext(), 10.0f));
        progress = a.getFloat(R.styleable.CircleProgressBar_progress, 50.0f);
        maxProgress = a.getInt(R.styleable.CircleProgressBar_max_progress, 100);
        direction = a.getInt(R.styleable.CircleProgressBar_direction, 3);

        a.recycle();

        paint = new Paint();
    }


    /**
     * 水平右边横向表示0度，一次顺时针，90，180，270
     */
    enum DirectionEnum {
        LEFT(0, 180.0f),
        TOP(1, 270.0f),
        RIGHT(2, 0.0f),
        BOTTOM(3, 90.0f);

        private final int direction;
        private final float degree;

        DirectionEnum(int direction, float degree) {
            this.direction = direction;
            this.degree = degree;
        }

        public int getDirection() {
            return direction;
        }

        public float getDegree() {
            return degree;
        }

        public boolean equalsDescription(int direction) {
            return this.direction == direction;
        }

        public static DirectionEnum getDirection(int direction) {
            for (DirectionEnum enumObject : values()) {
                if (enumObject.equalsDescription(direction)) {
                    return enumObject;
                }
            }
            return RIGHT;
        }

        public static float getDegree(int direction) {
            DirectionEnum enumObject = getDirection(direction);
            if (enumObject == null) {
                return 0;
            }
            return enumObject.getDegree();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int circlePoint = getWidth() / 2;
        paint.setStyle(STROKE); //设置空心
        paint.setColor(bgColor);
        paint.setStrokeWidth(progressWidth); //设置圆的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(circlePoint, circlePoint, circleRadius, paint);

        //画进度(圆弧)
        paint.setColor(progressColor);  //设置进度的颜色
        RectF oval = new RectF(circlePoint - circleRadius, circlePoint - circleRadius, circlePoint + circleRadius, circlePoint + circleRadius);  //用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, DirectionEnum.getDegree(direction), 360 * (progress / maxProgress), false, paint);  //根据进度画圆弧

        //画圆环内文字
        rect = new Rect();
        paint.setColor(progressTextColor);
        paint.setTextSize(progressTextSize);
        paint.setStyle(FILL);
        progressText = getProgressText();
        paint.getTextBounds(progressText, 0, progressText.length(), rect);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  //获得文字的基准线
        canvas.drawText(progressText, getMeasuredWidth() / 2 - rect.width() / 2, baseline, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = (int) ((2 * circleRadius) + progressWidth);
        }
        size = MeasureSpec.getSize(heightMeasureSpec);
        mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = (int) ((2 * circleRadius) + progressWidth);
        }
        setMeasuredDimension(width, height);
    }

    //中间的进度百分比
    private String getProgressText() {
        return TextUtils.isEmpty(text) ? (int) ((progress / maxProgress) * 100) + "%" : "识别中";
    }

    public void setTxt(String text) {
        this.text = text;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getProgressTextColor() {
        return progressTextColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        this.progressTextColor = progressTextColor;
    }

    public float getProgressTextSize() {
        return progressTextSize;
    }

    public void setProgressTextSize(float progressTextSize) {
        this.progressTextSize = progressTextSize;
    }

    public float getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(float progressWidth) {
        this.progressWidth = progressWidth;
    }

    public synchronized int getMaxProgress() {
        return maxProgress;
    }

    public synchronized void setMaxProgress(int maxProgress) {
        if (maxProgress < 0) {
            //此为传递非法参数异常
            throw new IllegalArgumentException("maxProgress should not be less than 0");
        }
        this.maxProgress = maxProgress;
    }

    public synchronized float getProgress() {
        return progress;
    }

    //加锁保证线程安全,能在线程中使用
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress should not be less than 0");
        }
        if (progress > maxProgress) {
            progress = maxProgress;
        }
        startAnim(progress);
    }

    private void startAnim(float startProgress) {
        animator = ObjectAnimator.ofFloat(progress, startProgress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                CircleProgressView.this.progress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setStartDelay(500);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void release() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
