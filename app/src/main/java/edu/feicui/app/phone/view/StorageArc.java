package edu.feicui.app.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.biz.StorageMent;

/**
 * Created by Administrator on 2017/2/7.
 */
public class StorageArc extends View {
    int mViewSize;
    RectF mRectF;
    Timer mTimer;
    Paint mPaint;
    int mNaturalAngle;
    int mExternalAngle;
    boolean mEnd;
    boolean mNoMore;
    public StorageArc(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNaturalAngle=180;
        mEnd=false;
        mNoMore=false;
    }
    public void drawAnimation(){
        double proportion= StorageMent.getSDandInternalProportion();
        final int prop=180-(int)(proportion*360);
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!mEnd){
                    mNaturalAngle--;
                }else{
                    mExternalAngle--;
                }
                if(mNaturalAngle<=prop){
                    mEnd=true;
                }
                postInvalidate();
                if(mExternalAngle<=-(180+prop)){
                    mTimer.cancel();
                }
            }
        },0,1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewSize=Math.min(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
        mRectF=new RectF(0,0,mViewSize,mViewSize);
        setMeasuredDimension(mViewSize,mViewSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.progressbar));
        canvas.drawArc(mRectF,180,mNaturalAngle-180,true,mPaint);
        if(mEnd){
            mPaint.setColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            canvas.drawArc(mRectF,mNaturalAngle,mExternalAngle,true,mPaint);
        }
    }
}
