package com.wuman.dogfan.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class ExtraAnimationUtils {
    
    public static final void stopAnimation(final View view) {
        Animation animation = view.getAnimation();
        animation.setAnimationListener(null);
        view.setAnimation(null);
    }

    public static final void startRepeatingAnimationSet(final View view,
        long duration, Integer... animations) {
        if ( animations == null || animations.length <= 0 ) {
            return;
        }
        final int size = animations.length;
        Context context = view.getContext();
        final Animation[] anims = new Animation[size];

        final AnimationListener listener = new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                final int currentIndex = (Integer) view.getTag();
                final int nextIndex = ( currentIndex + 1 ) % size;
                view.setTag(nextIndex);
                view.startAnimation(anims[nextIndex]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

        };

        final long animDuration = duration / size;

        for ( int i = 0; i < size; i++ ) {
            Animation animation = AnimationUtils.loadAnimation(context,
                animations[i]);
            animation.setDuration(animDuration);
            animation.setAnimationListener(listener);
            anims[i] = animation;
        }

        view.setTag(0);
        view.startAnimation(anims[0]);
    }

}
