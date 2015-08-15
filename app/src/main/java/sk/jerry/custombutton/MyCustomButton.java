package sk.jerry.custombutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by jerry on 03/07/15.
 */
public class MyCustomButton extends Button {

    private String mText;
    private String mRadius;
    private int mColorNormal;
    private int mColorPressed;
    private ColorStateList mColorStateList;
    private StateListDrawable mPressedDrawable;

    public MyCustomButton(Context context) {
        super(context);
        init(context, null);
    }

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyCustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.MyCustomButton);
        if (attr == null)
            return;

        try {
            mText = attr.getString(R.styleable.MyCustomButton_mcb_text);
            mRadius = attr.getString(R.styleable.MyCustomButton_mcb_radius);
            mColorNormal = attr.getColor(R.styleable.MyCustomButton_mcb_colorNormal, 0);
            mColorPressed = attr.getColor(R.styleable.MyCustomButton_mcb_colorPressed, 0);
        } finally {
            attr.recycle();
        }

        setText(mText);

        GradientDrawable normalDrawable = createDrawable(mColorNormal);
        GradientDrawable pressedDrawable = createDrawable(mColorPressed);

        mPressedDrawable = new StateListDrawable();
        mPressedDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        mPressedDrawable.addState(new int[]{}, normalDrawable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(mPressedDrawable);
        } else {
            setBackgroundDrawable(mPressedDrawable);
        }

        setTextAppearance(getContext(), android.R.style.TextAppearance_DeviceDefault_Widget_Button);
        setAllCaps(true);
        setMinimumWidth(88);
        setMinimumHeight(48);

    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private GradientDrawable createDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(Float.valueOf(mRadius));
        return drawable;
    }


}
