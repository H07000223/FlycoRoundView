package com.flyco.roundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RoundViewDelegate {
    private View view;
    private Context context;
    private GradientDrawable gd_background = new GradientDrawable();
    private GradientDrawable gd_background_press = new GradientDrawable();
    private int backgroundColor;
    private int backgroundPressColor;
    private int cornerRadius;
    private int cornerRadius_TL;
    private int cornerRadius_TR;
    private int cornerRadius_BL;
    private int cornerRadius_BR;
    private int strokeWidth;
    private int strokeColor;
    private int strokePressColor;
    private int textPressColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isTouchDown;
    private boolean isClickSet;
    private ColorStateList textColor;
    private float[] radiusArr = new float[8];

    public RoundViewDelegate(View view, Context context, AttributeSet attrs) {
        this.view = view;
        this.context = context;
        if (view instanceof ViewGroup) {
            view.setWillNotDraw(false);
        }

        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        backgroundColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT);
        backgroundPressColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Integer.MAX_VALUE);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.RoundTextView_rv_strokeColor, Color.TRANSPARENT);
        strokePressColor = ta.getColor(R.styleable.RoundTextView_rv_strokePressColor, Integer.MAX_VALUE);
        textPressColor = ta.getColor(R.styleable.RoundTextView_rv_textPressColor, Integer.MAX_VALUE);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual, false);
        cornerRadius_TL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL, 0);
        cornerRadius_TR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR, 0);
        cornerRadius_BL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL, 0);
        cornerRadius_BR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR, 0);

        ta.recycle();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        view.invalidate();
    }

    public void setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        view.invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dp2px(cornerRadius);
        view.invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = dp2px(strokeWidth);
        view.invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        view.invalidate();
    }

    public void setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        view.invalidate();
    }

    public void setTextPressColor(int textPressColor) {
        this.textPressColor = textPressColor;
        view.invalidate();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        view.invalidate();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        view.requestLayout();
    }

    public void setCornerRadius_TL(int cornerRadius_TL) {
        this.cornerRadius_TL = cornerRadius_TL;
        view.invalidate();
    }

    public void setCornerRadius_TR(int cornerRadius_TR) {
        this.cornerRadius_TR = cornerRadius_TR;
        view.invalidate();
    }

    public void setCornerRadius_BL(int cornerRadius_BL) {
        this.cornerRadius_BL = cornerRadius_BL;
        view.invalidate();
    }

    public void setCornerRadius_BR(int cornerRadius_BR) {
        this.cornerRadius_BR = cornerRadius_BR;
        view.invalidate();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public int getCornerRadius_TL() {
        return cornerRadius_TL;
    }

    public int getCornerRadius_TR() {
        return cornerRadius_TR;
    }

    public int getCornerRadius_BL() {
        return cornerRadius_BL;
    }

    public int getCornerRadius_BR() {
        return cornerRadius_BR;
    }

    protected void onDraw(Canvas canvas) {
        int width = view.getWidth();
        int height = view.getHeight();

        /**draw round rect*/
        if (isRadiusHalfHeight) {
            cornerRadius = height / 2;
        }

        if (!isTouchDown) {
            gd_background.setColor(backgroundColor);

            if (cornerRadius_TL > 0 || cornerRadius_TR > 0 || cornerRadius_BR > 0 || cornerRadius_BL > 0) {
                /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
                radiusArr[0] = cornerRadius_TL;
                radiusArr[1] = cornerRadius_TL;
                radiusArr[2] = cornerRadius_TR;
                radiusArr[3] = cornerRadius_TR;
                radiusArr[4] = cornerRadius_BR;
                radiusArr[5] = cornerRadius_BR;
                radiusArr[6] = cornerRadius_BL;
                radiusArr[7] = cornerRadius_BL;
                gd_background.setCornerRadii(radiusArr);
            } else {
                gd_background.setCornerRadius(cornerRadius);
            }

            gd_background.setStroke(strokeWidth, strokeColor);
            gd_background.setBounds(0, 0, width, height);
            gd_background.draw(canvas);
        } else {
            if (backgroundPressColor == Integer.MAX_VALUE) {
                backgroundPressColor = backgroundColor;
            }
            gd_background_press.setColor(backgroundPressColor);
            if (cornerRadius_TL > 0 || cornerRadius_TR > 0 || cornerRadius_BR > 0 || cornerRadius_BL > 0) {
                /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
                radiusArr[0] = cornerRadius_TL;
                radiusArr[1] = cornerRadius_TL;
                radiusArr[2] = cornerRadius_TR;
                radiusArr[3] = cornerRadius_TR;
                radiusArr[4] = cornerRadius_BR;
                radiusArr[5] = cornerRadius_BR;
                radiusArr[6] = cornerRadius_BL;
                radiusArr[7] = cornerRadius_BL;
                gd_background_press.setCornerRadii(radiusArr);
            } else {
                gd_background_press.setCornerRadius(cornerRadius);
            }
            if (strokePressColor == Integer.MAX_VALUE) {
                strokePressColor = strokeColor;
            }
            gd_background_press.setStroke(strokeWidth, strokePressColor);
            gd_background_press.setBounds(0, 0, width, height);
            gd_background_press.draw(canvas);
        }
    }

    protected void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isClickSet) {
                isTouchDown = true;

                if (view instanceof TextView) {
                    if (textPressColor != Integer.MAX_VALUE) {
                        textColor = ((TextView) view).getTextColors();
                        ((TextView) view).setTextColor(textPressColor);
                    }
                }

                view.invalidate();
            }
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP || !view.isPressed()) {
            if (isClickSet) {
                isTouchDown = false;
                if (view instanceof TextView) {
                    if (textPressColor != Integer.MAX_VALUE) {
                        ((TextView) view).setTextColor(textColor);
                    }
                }
                view.invalidate();
            }
        }
    }

    protected void setOnClickListener(View.OnClickListener l) {
        isClickSet = view.isEnabled() && l != null;
    }

    protected void setOnLongClickListener(View.OnLongClickListener l) {
        isClickSet = view.isEnabled() && l != null;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
