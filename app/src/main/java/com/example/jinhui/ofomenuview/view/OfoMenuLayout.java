package com.example.jinhui.ofomenuview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by jinhui on 2018/1/26.
 * Email:1004260403@qq.com
 */

public class OfoMenuLayout extends RelativeLayout {

    private View titleView;
    private View contentView;
    //title起始和终止坐标，主要为动画做准备
    private int titleStartY, titleEndY;
    //content起始和终止坐标，主要为动画做准备
    private int contentStartY, contentEndY;
    //动画对象
    private ObjectAnimator titleAnimator, contentAnimator;
    //title动画标志，为事件分发做准备
    private boolean titleAnimationing;
    //content动画标志，为事件分发做准备
    private boolean contentAnimationing;
    private boolean isOpen;
    //content中列表内容布局，它里面也有自己的动画
    private OfoContentLayout ofoContentLayout;


    public OfoMenuLayout(Context context) {
        super(context);
    }

    public OfoMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfoMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        titleView = getChildAt(0);
        contentView = getChildAt(1);
    }

    //菜单打开的动画
    public void open() {
        int titleHeight = titleView.getLayoutParams().height;
        titleStartY = -titleHeight;
        titleEndY = 0;

        contentStartY = getHeight() + contentView.getHeight();
        contentEndY = 0;
        // 默认动画
        definitAnimation();
        titleAnimator.start();
        contentAnimator.start();
        ofoContentLayout.open();
    }

    //菜单关闭的动画
    public void close() {
        int titleHeight = titleView.getLayoutParams().height;
        titleStartY = 0;
        titleEndY = -titleHeight;

        contentStartY = 0;
        contentEndY = getHeight() + contentView.getHeight();

        definitAnimation();

        titleAnimator.start();
        contentAnimator.start();
    }
    //定义动画部分
    private void definitAnimation() {
        PropertyValuesHolder titlePropertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", titleStartY, titleEndY);
        titleAnimator = ObjectAnimator.ofPropertyValuesHolder(titleView, titlePropertyValuesHolder);
        titleAnimator.setDuration(300);

        contentAnimator = ObjectAnimator.ofFloat(contentView, "translationY", contentStartY, contentEndY);
        contentAnimator.setDuration(500);

        titleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                titleAnimationing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                titleAnimationing = false;
            }
        });

        contentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                contentAnimationing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                contentAnimationing = false;
                isOpen = !isOpen;
                setVisibility(isOpen ? VISIBLE : INVISIBLE);
                if (isOpen) {
                    if (ofoMenuStatusListener != null) {
                        ofoMenuStatusListener.onOpen();
                    }
                } else {
                    if (ofoMenuStatusListener != null) {
                        ofoMenuStatusListener.onClose();
                    }
                }
            }
        });
    }

    /**
     * 这个方法终于看到了，但是起到什么作用，分析下
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return titleAnimationing || contentAnimationing || ofoContentLayout.isAnimationing();
    }

    public void setOfoContentLayout(OfoContentLayout ofoContentLayout) {
        this.ofoContentLayout = ofoContentLayout;
    }

    public boolean isOpen() {
        return isOpen;
    }

    // 接口回调
    public interface OfoMenuStatusListener {
        void onOpen();
        void onClose();
    }
    private OfoMenuStatusListener ofoMenuStatusListener;

    public void setOfoMenuStatusListener(OfoMenuStatusListener ofoMenuStatusListener) {
        this.ofoMenuStatusListener = ofoMenuStatusListener;
    }
}
