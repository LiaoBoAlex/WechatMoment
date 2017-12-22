package android.support.v4.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by liaobo on 2017/12/22.
 */

public class WechatMomentProgressDrawable extends MaterialProgressDrawable implements Animatable {
    private static final int ROTATION_FACTOR = 5*360;
    private Animation mAnimation;
    private View mParent;
    private Bitmap mBitmap;
    private float rotation;
    private Paint paint;

    public WechatMomentProgressDrawable(Context context, View parent) {
        super(context, parent);
        mParent = parent;
        paint = new Paint();
        setupAnimation();
        setBackgroundColor(Color.WHITE);
    }

    private void setupAnimation() {
        mAnimation = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                setProgressRotation(-interpolatedTime);
            }
        };
        mAnimation.setDuration(5000);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void start() {
        mParent.startAnimation(mAnimation);
    }
    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }


    @Override
    public void setProgressRotation(float rotation) {
        this.rotation = -rotation*ROTATION_FACTOR;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas c) {
        Rect bound = getBounds();
        c.rotate(rotation,bound.exactCenterX(),bound.exactCenterY());
        Rect src = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        c.drawBitmap(mBitmap,src,bound,paint);
    }
}
