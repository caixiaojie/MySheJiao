package com.flyingfish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class RecyclerLinearLayout extends LinearLayout{


    public RecyclerLinearLayout(Context context) {
        super(context);
    }

    public RecyclerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int itemCount = getChildCount();
        if (itemCount == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        final int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int width = getPaddingLeft() + getPaddingRight();
        for (int i = 0; i < itemCount; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            width += view.getMeasuredWidth();
        }
        setMeasuredDimension(width, heightSize);
    }
}
