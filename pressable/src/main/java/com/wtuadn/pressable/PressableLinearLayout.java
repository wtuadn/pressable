package com.wtuadn.pressable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wtuadn on 20161112.
 */

public class PressableLinearLayout extends LinearLayout implements IPressable {
    private Drawable pressableDrawable;

    public PressableLinearLayout(Context context) {
        super(context, null);
    }

    public PressableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        PressableUtils.init(this, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (pressableDrawable != null) {
            pressableDrawable.setHotspot(x, y);
            invalidate();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (pressableDrawable != null) {
            pressableDrawable.setState(getDrawableState());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (pressableDrawable != null) {
            pressableDrawable.setBounds(0,0,w,h);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (pressableDrawable != null) {
            pressableDrawable.draw(canvas);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (pressableDrawable != null) {
            return super.verifyDrawable(who) || who == pressableDrawable;
        }
        return super.verifyDrawable(who);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (pressableDrawable != null) {
            pressableDrawable.jumpToCurrentState();
        }
    }

    @Override
    public void setPressableDrawable(Drawable pressableDrawable) {
        this.pressableDrawable = pressableDrawable;
    }
}
