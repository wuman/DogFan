package com.wuman.dogfan;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class HtmlAboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.html_about_view);
        setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
            R.drawable.ic_launcher_dogfan);
        WebView wv = (WebView) findViewById(R.id.wv);

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(
                getPackageName(), 0);
            InputStream is = getResources().openRawResource(R.raw.about);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            wv.loadDataWithBaseURL(null,
                text.replaceFirst("0.0.0", pi.versionName), "text/html",
                "utf-8", null);
        } catch ( NameNotFoundException e ) {
            throw new RuntimeException(e);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }

        findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                finish();
            }

        });

    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

}
