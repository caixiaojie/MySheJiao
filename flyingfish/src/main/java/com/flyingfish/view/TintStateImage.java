package com.flyingfish.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.flyingfish.R;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class TintStateImage extends ImageView {

    private static final int IMAGE_DEFAULT_COLOR = Color.GRAY;

    private ColorStateList mColorStateList;

    public TintStateImage(Context context) {
        this(context,null);
    }

    public TintStateImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TintStateImage);

        mColorStateList = ta.getColorStateList(R.styleable.TintStateImage_colorStateList);
    }

    public TintStateImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TintStateImage);

        mColorStateList = ta.getColorStateList(R.styleable.TintStateImage_colorStateList);
        ta.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mColorStateList != null && mColorStateList.isStateful()){
            int color = mColorStateList.getColorForState(getDrawableState(),
                    IMAGE_DEFAULT_COLOR);
            super.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

}