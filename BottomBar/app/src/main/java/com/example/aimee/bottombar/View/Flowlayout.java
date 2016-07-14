package com.example.aimee.bottombar.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimee on 2016/5/2.
 */
public class Flowlayout extends ViewGroup {
    //存储所有的View，一行一行的存
    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();
    public Flowlayout(Context context) {
        this(context,null);
    }

    public Flowlayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Flowlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //实现设置所有的view的位置
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight= 0;
        List<View> lineView = new ArrayList<>();
        int cCount = getChildCount();

        for(int i = 0;i<cCount;i++)
        {
            View child = getChildAt(i);
            //测量子view的高度长度
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth+lineWidth+lp.leftMargin+lp.rightMargin >width-getPaddingLeft()-getPaddingRight())
            {
                //需要换行
                mLineHeight.add(lineHeight);
                mAllViews.add(lineView);
                lineWidth = 0;
                lineHeight = childHeight +lp.topMargin+lp.bottomMargin;
                lineView = new ArrayList<>();
            }
            lineWidth += childWidth +lp.leftMargin +lp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight
                            +lp.topMargin+lp.bottomMargin);
            lineView.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineView);
        //设置位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum=mAllViews.size();//行数
        for(int i=0;i<lineNum;i++)
        {
            lineView = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for(int j=0;j<lineView.size();j++)
            {
                View child = lineView.get(j);
                //如果gone消失
                if(child.getVisibility() == View.GONE)
                {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                //为子view布局
                child.layout(lc,tc,rc,bc);
                left += child.getMeasuredWidth()+lp.leftMargin +lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHight = MeasureSpec.getMode(heightMeasureSpec);

        //如果是wrap_content
        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        for(int i =0;i<cCount;i++)
        {
            View child = getChildAt(i);
            //测量子view的高度长度
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = child.getMeasuredHeight()+lp.bottomMargin+lp.topMargin;

            if(lineWidth + childWidth >sizeWidth-getPaddingLeft()-getPaddingRight())
            {
                //换行
                width = Math.max(width,lineWidth);
                lineWidth = childWidth;
                lineHeight +=childHeight;
            }
            else
            {
                //不换行
                lineWidth +=childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }

            if(i==cCount-1)
            {
                //最后一个,不然的话最后一行没有被记录
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }

        }
        setMeasuredDimension(
                modeWidth==MeasureSpec.EXACTLY?sizeWidth:width+getPaddingRight()+getPaddingLeft(),
                modeHight==MeasureSpec.EXACTLY?sizeHight:height+getPaddingBottom()+getPaddingTop()
        );
    }








































//    与当前对应的params
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
