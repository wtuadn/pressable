# pressable
这是一个按钮效果库，解放双手，再也不用写selector

直接上图：

<img src="demo.gif" width="40%">

代码很简单，使用很方便

```xml
<com.wtuadn.pressable.PressableTextView
            android:layout_width="150dp"
            android:layout_height="50dp"
            android:gravity="center"
            android:onClick="onClick"
            android:text="no bg"
            app:pressed_color="@color/default_black_pressed_color" />
```

> 不管是用xml设置还是用代码设置，最终都会执行**PressableUtils.setPressableDrawable**方法

自带PressableFrameLayout、PressableLinearLayout、PressableRelativeLayout、PressableImageView、PressableTextView五种常用控件
有特殊需要的可以继承相应view并实现IPressable接口

## Tips
borderless模式与view的背景冲突，设置了borderless="true"就不要设置背景了