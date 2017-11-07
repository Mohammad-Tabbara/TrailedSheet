package com.tabbara.mohammad.trailedsheet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import java.util.Date;

/**
 * Created by Espfish on 11/6/2017.
 */

public class TrailedSheet extends RelativeLayout {

    //Debug TAGS
    private final String DEBUG_TAG = "TAG YOUR IT";
    private final String ERROR_TAG = "BOOM";

    //Configuration
    private boolean unlocked = true;

    //Interfaces
    private ActionController actionController;
    private AnimationController animationController;

    //Position Values
    private float startY = 0;
    private float endYP1 = 0;
    private float endYP2 = 0;
    private float diff = 0;
    private float position = 0;

    //Fling Values
    private ViewConfiguration vc;
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private double velocityY;
    private long startUnixTime;
    private long endUnixTime;

    /**
     * Constructors
     */

    public TrailedSheet(Context context) {
        super(context);
        init(context,null, 0);
    }

    public TrailedSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public TrailedSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TrailedSheet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs, defStyleAttr);
    }

    /**
     * Initialization
     * @param context
     * @param attr
     * @param defStyle
     */

    private void init(Context context, AttributeSet attr, int defStyle){
        //Fling Init
        vc = ViewConfiguration.get(context);
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
    }

    /**
     * Lock Touch of child Views
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(unlocked) {
            return super.onInterceptTouchEvent(ev);
        }else{
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(unlocked) {
            int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    startUnixTime = new Date().getTime();
                    startY = event.getRawY();
                    endYP1 = event.getRawY();
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    endYP2 = event.getRawY();
                    diff = endYP2 - endYP1;
                    position = getY() + diff;
                    this.setY(position);
                    endYP1 = endYP2;
                    if(animationController != null) {
                        animationController.animateOnDrag(getId());
                    }
                    float deltaY = event.getRawY() - startY;
//                    if (Math.abs(deltaY) > mSlop) {
                        endUnixTime = new Date().getTime();
                        velocityY = deltaY/((endUnixTime-startUnixTime)/100);
                        Log.d(DEBUG_TAG,"Delta: "+deltaY);
                        Log.d(DEBUG_TAG,velocityY+"");
//                    }
                    return true;
                case (MotionEvent.ACTION_UP):
                case (MotionEvent.ACTION_CANCEL):
                    if ((mMinFlingVelocity <= Math.abs(velocityY) && Math.abs(velocityY) <= mMaxFlingVelocity && this.getY()<-getHeight() / 20)||getY() < -getHeight() / 2) {
                        if(actionController != null) {
                            this.lock();
                            actionController.exitUp(getId());
                            animationController.animateOnExitUp(getId());
                        }
                        this.animate()
                                .translationY(-getHeight())
                                .setDuration(150) //400
                                .start();
                    } else if ((mMinFlingVelocity <= Math.abs(velocityY) && Math.abs(velocityY) <= mMaxFlingVelocity && this.getY()>getHeight() / 20)||getY() > getHeight() / 2) {
                        if(actionController != null) {
                            this.lock();
                            actionController.exitDown(getId());
                            animationController.animateOnExitDown(getId());
                        }
                        this.animate()
                                .translationY(getHeight())
                                .setDuration(150)//400
                                .start();
                    } else {
                        if (getY() < 0) {
                            this.animate()
                                    .translationY(0)
                                    .setDuration(300)
                                    .start();
                        } else {
                            this.animate()
                                    .translationY(0)
                                    .setDuration(300)
                                    .start();
                        }
                        if(animationController != null) {
                            animationController.animateOnUp(getId());
                        }
                    }
//                if (mIsBeingDragged) {
//                    final VelocityTracker velocityTracker = mVelocityTracker;
//                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
//                    int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);
//                    mPopulatePending = true;
//                    final int width = getClientWidth();
//                    final int scrollX = getScrollX();
//                    final ItemInfo ii = infoForCurrentScrollPosition();
//                    final float marginOffset = (float) mPageMargin / width;
//                    final int currentPage = ii.position;
//                    final float pageOffset = (((float) scrollX / width) - ii.offset)
//                            / (ii.widthFactor + marginOffset);
//                    final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
//                    final float x = ev.getX(activePointerIndex);
//                    final int totalDelta = (int) (x - mInitialMotionX);
//                    int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity,
//                            totalDelta);
//                    setCurrentItemInternal(nextPage, true, true, initialVelocity);
//
//                    needsInvalidate = resetTouch();
//                }
//                    Log.d(DEBUG_TAG, "Action was UP || Cancelled");
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
//                    Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
//                            "of current screen element");
                    return true;
                default:
                    unlocked = true;
                    return super.onTouchEvent(event);
            }
        }else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * Lock Views
     */

    public void lock(){
        unlocked = false;
    }

    /**
     * Unlock Views
     */

    public void unlock(){
        unlocked = true;
    }

    /**
     * Check Touch Lock status
     * @return is the view Unlocked
     */

    public boolean isUnLock(){
        return unlocked;
    }

    /**
     * Animate a push up Event
     * And give user the ability to handle
     */
    public void moveUp(){
        this.animate()
                .translationY(-getHeight())
                .setDuration(400).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((Activity)getContext()).finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
    }
    public void moveDown(){
        this.animate()
                .translationY(getHeight())
                .setDuration(400).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((Activity) getContext()).finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
    }

    /**
     * set ActionController Interface
     * @param actionController
     * @return
     */
    public TrailedSheet setActionController(ActionController actionController){
        this.actionController = actionController;
        return this;
    }

    /**
     * Set AnimationController Interface
     * @param animationController
     * @return
     */
    public TrailedSheet setAnimationController(AnimationController animationController){
        this.animationController = animationController;
        return this;
    }

}
