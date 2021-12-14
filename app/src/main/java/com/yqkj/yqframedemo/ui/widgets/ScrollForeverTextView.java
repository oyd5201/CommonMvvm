package com.yqkj.yqframedemo.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;


//跑马灯textview
@SuppressLint("AppCompatCustomView")
public class ScrollForeverTextView extends TextView {
    public ScrollForeverTextView(Context context) {
        super(context);
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    // 转载请注明出处：http://blog.csdn.net/u014071694/article/details/52004542
    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
        // 重点
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect
            previouslyFocusedRect) {        // TODO Auto-generated method stub
        super.onFocusChanged(true, direction, previouslyFocusedRect);
        // 重点
    }
}
