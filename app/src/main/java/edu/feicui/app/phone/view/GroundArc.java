package edu.feicui.app.phone.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.biz.MemoryManagement;

/**
 * Created by 似水流年 on 2017/1/5.
 *
 * @author Nicholas.Lv
 */
public class GroundArc extends View {
    Paint mPaint;
    RectF mRectF;
    int mDiameter;
    float mAngle;
    boolean mSwitch;
    boolean mLocked;
    double mPercentageValue;
    Timer timer;
    MemoryManagement mMemory;

    public GroundArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMemory=MemoryManagement.getInstance(getContext());
        radianChanged(mMemory.getMemoryUsage());
    }

    public synchronized void radianChanged(double percentage) {
        final float angle=(float)(300*percentage);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mLocked = true;
                if (mAngle <= 0) {
                    mSwitch = true;
                }
                if (!mSwitch) {
                    mAngle--;
                } else {
                    mAngle++;
                }
                mPercentageValue = ((double) mAngle / 3) - 1;
                if (mPercentageValue < 0) {
                    mPercentageValue = 0;
                }
                postInvalidate();
                if (mSwitch && mAngle >= angle) {
                    mSwitch = false;
                    mLocked=false;
                    timer.cancel();
                }
            }
        }, 0, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rectWidth = MeasureSpec.getSize(widthMeasureSpec);
        int rectHeight = MeasureSpec.getSize(heightMeasureSpec);
        mDiameter = Math.min(rectWidth, rectHeight);
        mRectF = new RectF(0, 0, mDiameter, mDiameter);
        setMeasuredDimension(mDiameter, mDiameter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mDiameter * 0.1f);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.action_background));
        canvas.drawArc(mRectF, 0, 360, true, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        if(mAngle<100){
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.smooth));
        }else if(mAngle>100&&mAngle<200){
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.good));
        }else {
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.notEnoughStorage));
        }
        canvas.scale(0.8f, 0.8f, mDiameter * 0.5f, mDiameter * 0.5f);
        canvas.drawArc(mRectF, 120, mAngle, false, mPaint);
        mPaint.setStrokeWidth(mDiameter * (0.1F) + mDiameter / 100 + 3);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.action_background));
        canvas.drawArc(mRectF, 60, mAngle - 300, false, mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorFrame));
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(mDiameter / 2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(String.valueOf((int) mPercentageValue), mDiameter / 2, mDiameter / 2 + mDiameter / 6, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(mDiameter / 10);
        canvas.drawText("%", mDiameter / 2 + mDiameter / 3, mDiameter / 2 + mDiameter / 6, mPaint);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.accelerate));
        mPaint.setTextSize(mDiameter / 10);
        if (mDiameter < 100) {
            mRectF.set(mDiameter / 2 - mDiameter / 10, mDiameter / 2 - mDiameter / 6 + mDiameter / 2, mDiameter / 2 + mDiameter / 10, mDiameter / 2 + mDiameter / 2);
            canvas.drawArc(mRectF, 0, 360, true, mPaint);
            mRectF.set(0, 0, mDiameter, mDiameter);
        } else {
            canvas.drawText("点击加速", mDiameter / 2, mDiameter / 2 + mDiameter / 3, mPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mLocked) {
            timer.cancel();
            timer=null;
        }
        double x=event.getX();
        double y=event.getY();
        int min=mDiameter / 2 - mDiameter / 3;
        int max=mDiameter / 2 + mDiameter / 3;
        if(x<max&&x>min&&y<max&&y>min){
            mMemory.killAllProcesses();
            radianChanged(mMemory.getMemoryUsage());
        }
        return super.onTouchEvent(event);
    }
}
