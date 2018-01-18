package com.example.testwebrtc;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    protected WebView mWebView;

    protected final String TAG = getClass().getSimpleName();

    public static final String APPRTC_URL_LOOPBACK_VP8="https://appr.tc/r/%s?vrc=VP8&debug=loopback&vsc=VP8";

    public static final String APPRTC_URL_LOOPBACK_H264="https://appr.tc/r/%s?vrc=H264&debug=loopback&vsc=H264";

    public static final String APPRTC_URL_P2P="https://appr.tc/r/%s";

    public static final String APPRTC_URL_MP4="http://techslides.com/demos/sample-videos/small.mp4";

    boolean mWebviewInitialized = false;


    View.OnClickListener mBtClickListenerVP8 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText editText = (EditText)findViewById(R.id.roomId);
            String url = String.format(APPRTC_URL_LOOPBACK_VP8,editText.getText());
            mWebView.loadUrl(url);
        }
    };

    View.OnClickListener mBtClickListenerH264 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText editText = (EditText)findViewById(R.id.roomId);
            String url = String.format(APPRTC_URL_LOOPBACK_H264,editText.getText());
            mWebView.loadUrl(url);
        }
    };

    View.OnClickListener mBtClickListenerP2P = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText editText = (EditText)findViewById(R.id.roomId);
            String url = String.format(APPRTC_URL_P2P,editText.getText());
            mWebView.loadUrl(url);
        }
    };

    View.OnClickListener mBtClickListenerMP4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mWebView.loadUrl(APPRTC_URL_MP4);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webpage);

        EditText editText = (EditText)findViewById(R.id.roomId);
        editText.bringToFront();

        Button buttonVP8 = (Button) findViewById(R.id.buttonLoadVP8);
        buttonVP8.setOnClickListener(mBtClickListenerVP8);

        Button buttonH264 = (Button) findViewById(R.id.buttonLoadH264);
        buttonH264.setOnClickListener(mBtClickListenerH264);

        Button buttonP2P = (Button) findViewById(R.id.buttonLoadP2P);
        buttonP2P.setOnClickListener(mBtClickListenerP2P);

        Button buttonMP4 = (Button) findViewById(R.id.buttonLoadMP4);
        buttonMP4.setOnClickListener(mBtClickListenerMP4);

    }

    private void askPermissions() {
        Set<String> permissions = PermissionUtil.getNotGrantedPermissions(this);
        PermissionUtil.askPermissions(this, permissions, 10001);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionUtil.isAllPermissionsGranted(this)) {
            askPermissions();
        } else {
            if (!mWebviewInitialized) {
                initWebView();
            }
        }
    }

    private void initWebView() {
        if(!mWebviewInitialized) {
            mWebView = (WebView) findViewById(R.id.webpage);
            mWebView.setWebChromeClient(new WebChromeClient() {
                // Need to accept permissions to use the camera
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    Log.w(TAG, "Request permission");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            request.grant(request.getResources());
                        }
                    });
                }

                public boolean onConsoleMessage(ConsoleMessage cm) {
                    Log.w(TAG, "Webview log: " + cm.message());
                    return true;
                }
            });
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            setWebViewSettings();
        }
    }

    private void setWebViewSettings() {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setAllowContentAccess(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        WebView.setWebContentsDebuggingEnabled(true);

        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getCacheDir().toString());

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(mWebView, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10001:
                if (PermissionUtil.isAllPermissionsGranted(this)) {
                    if (!mWebviewInitialized) {
                        initWebView();
                    }
                }
                break;
            default:
                break;
        }
    }
}

