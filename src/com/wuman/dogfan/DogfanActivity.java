package com.wuman.dogfan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wuman.dogfan.utils.ExtraAnimationUtils;

public class DogfanActivity extends Activity {

    private static final String YOUTUBE_LINK = "http://www.youtube.com/watch?v=j_6qn9FDQno";

    private ImageView mFanView;
    private ImageView mFrontView;
    private ImageView mFaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mFanView = (ImageView) findViewById(R.id.df_fan);
        mFrontView = (ImageView) findViewById(R.id.df_front);
        mFaceView = (ImageView) findViewById(R.id.df_face);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFanView.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.fan_rotation));
        ExtraAnimationUtils.startRepeatingAnimationSet(mFrontView, 200L,
            R.anim.front_shake_0, R.anim.front_shake_1, R.anim.front_shake_2,
            R.anim.front_shake_3, R.anim.front_shake_4, R.anim.front_shake_5);
        ExtraAnimationUtils.startRepeatingAnimationSet(mFaceView, 200L,
            R.anim.face_shake_0, R.anim.face_shake_1, R.anim.face_shake_2,
            R.anim.face_shake_3, R.anim.face_shake_4, R.anim.face_shake_5);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExtraAnimationUtils.stopAnimation(mFanView);
        ExtraAnimationUtils.stopAnimation(mFrontView);
        ExtraAnimationUtils.stopAnimation(mFaceView);
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
