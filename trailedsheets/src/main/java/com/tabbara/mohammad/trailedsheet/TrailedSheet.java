package com.tabbara.mohammad.trailedsheet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;

import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

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
    private TrailedSheetListeners.EventListener eventListener;
    private TrailedSheetListeners.DragListener dragListener;
    private TrailedSheetListeners.ReleaseListener releaseListener;
    private TrailedSheetListeners.WhileAnimatingListener whileAnimating;

    //Position Values
    private float defaultY = 0;
    private float startY = 0;
    private float tempYP1 = 0;
    private float endYP1 = 0;
    private float endYP2 = 0;
    private float diff = 0;
    private float position = 0;

    GestureDetectorCompat gestureDetectorCompat;
    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(tempYP1 < endYP2){
                lock();
                moveDown(200);
                return false;
            }
            if(tempYP1 > endYP2){
                lock();
                moveUp(200);
                return false;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }
    };



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
     * @param context The Activity context
     * @param attr The RelativeView attributes
     * @param defStyle The Defined Style
     */

    private void init(Context context, AttributeSet attr, int defStyle){

        gestureDetectorCompat = new GestureDetectorCompat(context,simpleOnGestureListener);
        //Default position Init
        defaultY = getY();
    }

    /**
     * Lock Touch of child Views
     * @param ev Child Related Events
     * @return true if locked
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(unlocked) {
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    /**
     * Handle Touch of parent View.
     * @param event Parent Related Events
     * @return true if locked
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(unlocked) {
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startY = event.getRawY();
                    endYP1 = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    endYP2 = event.getRawY();
                    diff = endYP2 - endYP1;
                    position = getY() + diff;
                    this.setY(position);
                    tempYP1 = endYP1;
                    endYP1 = endYP2;
                    if (dragListener != null) {
                        dragListener.onDrag(getId());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (getY() < -getHeight() / 2) {
                        if (eventListener != null) {
                            eventListener.onExitUp(getId());
                        }
                        this.animate()
                                .translationY(-getHeight())
                                .setListener(null)
                                .setDuration(400) //400
                                .start();
                    } else if (getY() > getHeight() / 2) {
                        if (eventListener != null) {
                            eventListener.onExitDown(getId());
                        }
                        this.animate()
                                .translationY(getHeight())
                                .setListener(null)
                                .setDuration(400)//400
                                .start();
                    } else {
                        if (getY() < 0) {
                            this.animate()
                                    .translationY(0)
                                    .setListener(null)
                                    .setDuration(300)
                                    .start();
                        } else {
                            this.animate()
                                    .translationY(0)
                                    .setListener(null)
                                    .setDuration(300)
                                    .start();
                        }
                        if (releaseListener != null) {
                            releaseListener.onUp(getId());
                        }
                    }
                    break;
                default:
                    return super.onTouchEvent(event);
            }
            if(!(getY() < -getHeight() / 2 || getY() > getHeight() / 2)) {
                gestureDetectorCompat.onTouchEvent(event);
            }
            return true;
        }else{
            return false;
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
        int duration = 400;
        moveUp(duration);
    }

    private void moveUp(int duration){
        this.animate()
                .translationY(-getHeight())
                .setDuration(duration).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                unlock();
                if(eventListener != null) {
                    eventListener.onExitUp(getId());
                }
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
                if(whileAnimating != null) {
                    whileAnimating.whileAnimatingUp(getId());
                }
            }
        });
    }

    /**
     * Animate a push down Event
     * And give user the ability to handle
     */
    public void moveDown(){
        int duration = 400;
        moveDown(duration);
    }

    private void moveDown(int duration){
        this.animate()
                .translationY(getHeight())
                .setDuration(duration).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                unlock();
                if(eventListener != null) {
                    eventListener.onExitDown(getId());
                }
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
                if(whileAnimating != null){
                    whileAnimating.whileAnimatingDown(getId());
                }
            }
        });
    }

    /**
     * set TrailedSheetListeners Interface
     * @param eventListener a TrailedSheetListeners
     * @return this
     */
    public TrailedSheet addEventListener(TrailedSheetListeners.EventListener eventListener){
        this.eventListener = eventListener;
        return this;
    }

    public TrailedSheet addReleaseListener(TrailedSheetListeners.ReleaseListener releaseListener){
        this.releaseListener = releaseListener;
        return this;
    }

    public TrailedSheet addDragListener(TrailedSheetListeners.DragListener dragListener){
        this.dragListener = dragListener;
        return this;
    }

    public TrailedSheet addWhileAnimatingListener(TrailedSheetListeners.WhileAnimatingListener whileAnimating){
        this.whileAnimating = whileAnimating;
        return this;
    }


    /**
     * Reset To default Position
     */

    public void reset(boolean animated){
        if(animated){
            animate().translationY(defaultY)
                    .setListener(null)
                    .setDuration(400)
                    .start();
        }else{
            setY(defaultY);
        }
    }

}
