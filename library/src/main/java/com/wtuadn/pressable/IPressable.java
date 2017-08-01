package com.wtuadn.pressable;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface IPressable extends Drawable.Callback {
    Context getContext();

    Drawable getBackground();

    void setPressableDrawable(Drawable pressableDrawable);

    void setBackgroundDrawable(Drawable background);
}