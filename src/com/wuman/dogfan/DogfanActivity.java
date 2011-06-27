package com.wuman.dogfan;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wuman.dogfan.utils.ExtraAnimationUtils;

public class DogfanActivity extends Activity {

    private static final String YOUTUBE_LINK = "http://www.youtube.com/watch?v=j_6qn9FDQno";

    private boolean mPower;

    private ImageView mPlugView;
    private ImageView mFanView;
    private ImageView mFrontView;
    private ImageView mFaceView;
    private SoundPool mSoundPool;
    private int mSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mPower = false;

        mPlugView = (ImageView) findViewById(R.id.df_plug);
        mFanView = (ImageView) findViewById(R.id.df_fan);
        mFrontView = (ImageView) findViewById(R.id.df_front);
        mFaceView = (ImageView) findViewById(R.id.df_face);

        mPlugView.setTag(true);
        mPlugView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View widget) {
                Animation currentAnimation = widget.getAnimation();
                if ( currentAnimation == null || currentAnimation.hasEnded() ) {
                    mPower = !mPower;
                    updateUIStates();
                }
            }

        });

        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mSoundId = mSoundPool.load(this, R.raw.ahahah, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIStates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateFans(false);
    }

    @Override
    protected void onDestroy() {
        mSoundPool.release();
        mSoundPool = null;
        super.onDestroy();
    }

    private int playSound() {
        return mSoundPool.play(mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    private void updateUIStates() {
        Animation currentPlugAnimation = mPlugView.getAnimation();
        if ( currentPlugAnimation == null || currentPlugAnimation.hasEnded() ) {
            Animation plugAnimation = AnimationUtils.loadAnimation(this, mPower
                ? R.anim.cord_down : R.anim.cord_up);
            plugAnimation.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationEnd(Animation animation) {
                    updateFans(mPower);
                    if ( mPower ) {
                        playSound();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub

                }

            });
            mPlugView.startAnimation(plugAnimation);
        }
    }

    private void updateFans(boolean start) {
        if ( start ) {
            mFanView.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.fan_rotation));
            ExtraAnimationUtils.startRepeatingAnimationSet(mFrontView, 200L,
                R.anim.front_shake_0, R.anim.front_shake_1,
                R.anim.front_shake_2, R.anim.front_shake_3,
                R.anim.front_shake_4, R.anim.front_shake_5);
            ExtraAnimationUtils.startRepeatingAnimationSet(mFaceView, 200L,
                R.anim.face_shake_0, R.anim.face_shake_1, R.anim.face_shake_2,
                R.anim.face_shake_3, R.anim.face_shake_4, R.anim.face_shake_5);
        } else {
            ExtraAnimationUtils.stopAnimation(mFanView);
            ExtraAnimationUtils.stopAnimation(mFrontView);
            ExtraAnimationUtils.stopAnimation(mFaceView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
        case R.id.options_youtube: {
            Uri uri = Uri.parse(YOUTUBE_LINK);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
            startActivity(intent);
            return true;
        }
        case R.id.options_about: {
            Intent intent = new Intent(this, HtmlAboutActivity.class);
            startActivity(intent);
            return true;
        }
        default: {
            return super.onOptionsItemSelected(item);
        }
        }
    }

}
