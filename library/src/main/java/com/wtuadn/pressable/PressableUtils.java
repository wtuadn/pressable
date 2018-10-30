package com.wtuadn.pressable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by wtuadn on 16-1-7.
 */
public class PressableUtils {
    public static void init(IPressable iPressable, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = iPressable.getContext().obtainStyledAttributes(attrs, R.styleable.Pressable);
            int color = ta.getInt(R.styleable.Pressable_pressed_color, -1);
            boolean borderless = ta.getBoolean(R.styleable.Pressable_borderless, false);
            int mask_radius = ta.getDimensionPixelSize(R.styleable.Pressable_mask_radius, 0);
            float colorAlpha = ta.getFloat(R.styleable.Pressable_pressed_color_alpha, -1);
            if (color != -1) {
                setPressableDrawable(iPressable, color, borderless, mask_radius, colorAlpha);
            }
            ta.recycle();
        }
    }

    public static void setPressableDrawable(IPressable iPressable, int pressColor) {
        setPressableDrawable(iPressable, pressColor, false, 0, -1);
    }

    /**
     * @param pressColor 按下去的颜色，尽量不要使用带透明的颜色，如#4c000000，透明度通过colorAlpha参数来调节
     * @param borderless 无边框模式，按下去变色的范围会超出view自身
     * @param mask_radius 圆角大小
     * @param colorAlpha 按下去的颜色的透明度
     */
    public static void setPressableDrawable(IPressable iPressable, int pressColor, boolean borderless, int mask_radius, float colorAlpha) {
        Drawable background = iPressable.getBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (colorAlpha > 1 || colorAlpha < 0) {
                colorAlpha = 0.2f;
            }
            pressColor = adjustAlpha(pressColor, colorAlpha);
            GradientDrawable mask = null;
            if (!borderless) {
                if (background instanceof GradientDrawable) {
                    mask = (GradientDrawable) background.getConstantState().newDrawable().mutate();
                    mask.setColor(Color.WHITE);
                } else {
                    mask = getRippleMask(mask_radius);
                }
            }
            setRippleDrawable(iPressable, pressColor, borderless, mask);
        } else {
            if (colorAlpha > 1 || colorAlpha < 0) {
                colorAlpha = 0.12f;
            }
            pressColor = adjustAlpha(pressColor, colorAlpha);
            StateListDrawable stateListDrawable = new StateListDrawable();
            GradientDrawable mask;
            if (background instanceof GradientDrawable) {
                mask = (GradientDrawable) background.getConstantState().newDrawable().mutate();
                mask.setColor(pressColor);
            } else {
                mask = new GradientDrawable();
                mask.setColor(pressColor);
                mask.setCornerRadius(mask_radius);
            }
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, mask);
            stateListDrawable.setCallback(iPressable);
            iPressable.setPressableDrawable(stateListDrawable);
        }
    }

    private static GradientDrawable getRippleMask(int mask_radius) {
        GradientDrawable mask = new GradientDrawable();
        if (mask_radius > 0) {
            mask.setCornerRadius(mask_radius);
        }
        mask.setColor(Color.WHITE);
        return mask;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setRippleDrawable(IPressable iPressable, int pressColor, boolean borderless, GradientDrawable mask) {
        RippleDrawable rippleDrawable = new RippleDrawable(
                new ColorStateList(
                        new int[][]{new int[]{}},
                        new int[]{pressColor}
                ), null, mask);
        if (borderless) {
            iPressable.setBackgroundDrawable(rippleDrawable);
        } else {
            rippleDrawable.setCallback(iPressable);
            iPressable.setPressableDrawable(rippleDrawable);
        }
    }

    public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
