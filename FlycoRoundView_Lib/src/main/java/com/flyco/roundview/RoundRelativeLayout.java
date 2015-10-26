package com.flyco.roundview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/** 用于需要圆角矩形框背景的RelativeLayout的情况,减少直接使用RelativeLayout时引入的shape资源文件 */
public class RoundRelativeLayout extends RelativeLayout {
    private RoundViewDelegate delegate;

    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new RoundViewDelegate(this, context, attrs);
    }

    /** use delegate to set attr */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (delegate.isWidthHeightEqual()) {
            if (getWidth() > 0 && getHeight() > 0) {
                int max = Math.max(getWidth(), getHeight());
                setMeasuredDimension(max, max);
                invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        delegate.onDraw(canvas);
        //super.onDraw after our draw
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        delegate.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        delegate.setOnClickListener(l);
        super.setOnClickListener(l);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        delegate.setOnLongClickListener(l);
        super.setOnLongClickListener(l);
    }
}
