package com.example.administrator.loginapp.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/1/7 0007.
 */

public class FlowLayout extends FrameLayout{
    public FlowLayout(@NonNull Context context) {
        super(context);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return (LayoutParams) new LayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return (LayoutParams) new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasure=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasure=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        int count=getChildCount();

        int lineWidth=0;
        int lineHeight=0;
        int width=0;
        int height=0;


        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();

            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            if(lineWidth+childWidth>widthMeasure){
                width=Math.max(lineWidth,childWidth);
                height+=lineHeight;

                lineWidth=childWidth;
                lineHeight=childHeight;
            }else {
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            if(i==count-1){
                width=Math.max(width,lineWidth);
                height+=lineHeight;
            }

        }
    setMeasuredDimension((widthMode==MeasureSpec.EXACTLY)?widthMeasure:width,
            (heightMode==MeasureSpec.EXACTLY)?heightMeasure:height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int count=getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int Left=0;
        int Top=0;
        for(int i=0;i<count;i++){
            View child=getChildAt(i);

            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();

            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            if(lineWidth+childWidth>getMeasuredWidth()) {
                Top+=lineHeight;
                Left=0;
                lineWidth=childWidth;
                lineHeight=childHeight;

            }else {
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            int l= Left+lp.leftMargin;
            int t=Top+lp.topMargin;
            int r=l+child.getMeasuredWidth();
            int b=t+child.getMeasuredHeight();
            child.layout(l,t,r,b);
            Left+=childWidth;

        }
    }
}
