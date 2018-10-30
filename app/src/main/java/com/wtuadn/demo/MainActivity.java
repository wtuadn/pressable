package com.wtuadn.demo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wtuadn.pressable.PressableTextView;
import com.wtuadn.pressable.PressableUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWithCode();
    }

    private void initWithCode() {
        //如果背景是GradientDrawable，并且在设置背景后再设置PressableDrawable，
        //按下去的形状就会和设置的GradientDrawable一样
        PressableTextView tv_code1 = findViewById(R.id.tv_code1);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.LTGRAY);
        gd.setShape(GradientDrawable.OVAL);
        tv_code1.setBackgroundDrawable(gd);
        PressableUtils.setPressableDrawable(tv_code1, getResources().getColor(R.color.default_black_pressed_color));

        //如果背景不是GradientDrawable，就只能自己手动控制圆角大小，透明度等
        PressableTextView tv_code2 = findViewById(R.id.tv_code2);
        PressableUtils.setPressableDrawable(tv_code2, getResources().getColor(R.color.default_black_pressed_color), false, 100, 0.6f);
    }

    public void onClick(View view) {

    }
}
