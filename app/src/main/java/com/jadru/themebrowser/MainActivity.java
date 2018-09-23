package com.jadru.themebrowser;

import java.io.File;
import java.net.URISyntaxException;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean pop1vib = false;
    boolean pop2vib = false;
    boolean pop3vib = false;

    private View decorView;

    TextView btn_url;
    Button btn_menu;
    Button btn_tab;
    FloatingActionButton btn_expand;
    LinearLayout actionbtns;
    RelativeLayout menu;
    RelativeLayout bg;

    SharedPreferences pref;
    String myOrigin;
    String homepagelink;
    GeolocationPermissions.Callback myCallback;
    EditText url_edit;
    WebView webView;
    ProgressBar progress;
    private int uiOption;
    boolean yesorno = false;
    String searchengineurl;


    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;

    Animation fadepopon;
    Animation fadepopoff;
    boolean black;
    RelativeLayout popview;
    RelativeLayout popbottom;
    WebView popwebView;
    Button pop1;
    Button pop2;
    Button pop3;
    TextView poptext;
    TextView popsitetitle;
    RelativeLayout popviewui;
    Bitmap mBitmap;
    int bgcolor;
    int everyx;
    int everyy;
    int oldx;
    int oldy;
    int statusbarcolor;
    private View mCustomView;
    View view;
    Canvas cv;
    String longurl;
    String deviceName;
    SharedPreferences prefsdef;
    boolean blackstatustrue = false;

    Vibrator vibe;
    private int mOriginalOrientation;
    private FullscreenHolder mFullscreenContainer;
    private CustomViewCallback mCustomViewCollback;

    private static final int RC_FILE_CHOOSE = 1;
    private ValueCallback<Uri> mUploadMsg = null;
    public static final String TAG = null;
    InputMethodManager imm;

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;
    private final int MY_PERMISSION_REQUEST_LOCATION = 101;

    @JavascriptInterface
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefsdef = PreferenceManager.getDefaultSharedPreferences(this);
        webView = (WebView) findViewById(R.id.webView);
        setsettingsnow();

        deviceName = android.os.Build.MODEL;

        checkStoragePermission();

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b1.setOnClickListener(mClickListener);
        b2.setOnClickListener(mClickListener);
        b3.setOnClickListener(mClickListener);
        b4.setOnClickListener(mClickListener);
        b5.setOnClickListener(mClickListener);
        b6.setOnClickListener(mClickListener);
        b7.setOnClickListener(mClickListener);
        b8.setOnClickListener(mClickListener);
        b9.setOnClickListener(mClickListener);

        view = findViewById(android.R.id.content).getRootView();
        url_edit = (EditText) findViewById(R.id.url_bar);
        bg = (RelativeLayout) findViewById(R.id.bg);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_url = (TextView) findViewById(R.id.btn_url);
        btn_tab = (Button) findViewById(R.id.btn_tab);
        btn_tab.setVisibility(View.GONE);
        btn_expand = (FloatingActionButton) findViewById(R.id.btn_expand);
        actionbtns = (LinearLayout) findViewById(R.id.actionbtns);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        menu = (RelativeLayout) findViewById(R.id.menu);
        popview = (RelativeLayout) findViewById(R.id.popview);
        popwebView = (WebView) findViewById(R.id.popwebView);
        pop1 = (Button) findViewById(R.id.pop1);
        pop2 = (Button) findViewById(R.id.pop2);
        pop3 = (Button) findViewById(R.id.pop3);
        poptext = (TextView) findViewById(R.id.poptext);
        popbottom = (RelativeLayout) findViewById(R.id.popBottom);
        popsitetitle = (TextView) findViewById(R.id.popsitetitle);
        popviewui = (RelativeLayout) findViewById(R.id.popviewui);

        fadepopon = AnimationUtils.loadAnimation(this, R.anim.fadeanim_popviewon);
        fadepopoff = AnimationUtils.loadAnimation(this, R.anim.fadeanim_popviewoff);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        black = pref.getBoolean("t-black", false);
        url_edit.setVisibility(View.INVISIBLE);
        menu.setVisibility(View.INVISIBLE);
        popview.setVisibility(View.INVISIBLE);
        actionbtns.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        popwebView.loadUrl("about:blank");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, getStatusBarHeight(), 0, 0);
        webView.setLayoutParams(params);
        setWebView();
        setClickable();
        setPopView();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        String url_before = pref.getString("url_before", "http://www.google.com");

        Uri url = getIntent().getData();
        if (!(url == null)) {
            webView.loadUrl(url.toString());
            Toast.makeText(this, getResources().getString(R.string.connectlink), Toast.LENGTH_SHORT).show();
        } else {
            webView.loadUrl(homepagelink);
        }

        fadepopoff.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popview.setVisibility(View.INVISIBLE);
                popview.setBackgroundDrawable(null);
                mBitmap = null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                popview.setVisibility(View.INVISIBLE);
                popview.setBackgroundDrawable(null);
                mBitmap = null;
            }
        });

        themeSkin();
        getWindow().addFlags(16777216);

    }

    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    public void setWebView() {
        WebSettings webSettings = webView.getSettings();  // 웹세팅 객체 생성
        webSettings.setSaveFormData(true);  // 캐시 사용 허용a
        webSettings.setSupportZoom(true);  // 줌 지원
        webSettings.setBuiltInZoomControls(true); // 줌컨트롤 활성화
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);  // 자바스크립트 활성화
        webView.setHapticFeedbackEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; " + deviceName + "Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
        //    }else{
        //        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; " + deviceName + "Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
        //    }
        //} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //    webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; " + deviceName + " Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
        //} else {
        //    webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30");
        //}
        webView.setWebViewClient(new WebBrowserClient());  // 웹뷰에 브라우저 클라이언트 기능 활성화
        webView.requestFocus();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }

        registerForContextMenu(webView);
        Context context = webView.getContext();
        PackageManager packageManager = context.getPackageManager();
        String appName = "";
        String appVersion = "";
        String userAgent = webSettings.getUserAgentString();

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                myOrigin = origin;
                myCallback = callback;
                checkLocationPermission();
            }

            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
                openFileChooser(uploadFile);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMsg = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, getResources().getString(R.string.filechooser)), RC_FILE_CHOOSE);
            }

            public boolean onCreateWindow(WebView view, boolean dialog,
                                          boolean userGesture, Message resultMsg) {
                // TODO Auto-generated method stub
                return super.onCreateWindow(view, dialog, userGesture, resultMsg);
            }

            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                // TODO Auto-generated method stub
                //return super.onJsConfirm(view, url, message, result);
                new AlertDialog.Builder(view.getContext())
                        .setTitle(getResources().getString(R.string.alert))
                        .setMessage(message)
                        .setPositiveButton(getResources().getString(R.string.btn_ok),
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.btn_no),
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {

                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                    return;
                }

                mOriginalOrientation = MainActivity.this.getRequestedOrientation();

                FrameLayout decor = (FrameLayout) MainActivity.this.getWindow().getDecorView();

                mFullscreenContainer = new FullscreenHolder(MainActivity.this);
                mFullscreenContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT);
                decor.addView(mFullscreenContainer, ViewGroup.LayoutParams.MATCH_PARENT);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mCustomView = view;
                mCustomViewCollback = callback;
                MainActivity.this.setRequestedOrientation(mOriginalOrientation);

            }

            @Override
            public void onHideCustomView() {
                if (mCustomView == null) {
                    return;
                }

                FrameLayout decor = (FrameLayout) MainActivity.this.getWindow().getDecorView();
                decor.removeView(mFullscreenContainer);
                mFullscreenContainer = null;
                mCustomView = null;
                mCustomViewCollback.onCustomViewHidden();
                MainActivity.this.setRequestedOrientation(mOriginalOrientation);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            }

        });

        webView.setDownloadListener(new DownloadListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                MimeTypeMap mtm = MimeTypeMap.getSingleton();
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(url);

                String fileName = downloadUri.getLastPathSegment();
                int pos;
                if ((pos = contentDisposition.toLowerCase().lastIndexOf("filename=")) >= 0) {
                    fileName = contentDisposition.substring(pos + 9);
                    pos = fileName.lastIndexOf(";");
                    if (pos > 0) {
                        fileName = fileName.substring(0, pos - 1);
                    }
                }

                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
                String mimeType = mtm.getMimeTypeFromExtension(fileExtension);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setTitle(fileName);
                request.setDescription(url);
                request.setMimeType(mimeType);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                downloadManager.enqueue(request);

            }

        });

        webView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                HitTestResult result = webView.getHitTestResult();
                if (result.getType() == HitTestResult.IMAGE_TYPE ||
                        result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE ||
                        result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
                    Message msg = mHandler.obtainMessage();
                    webView.requestFocusNodeHref(msg);
                    vibe.vibrate(40);
                }
                return false;
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Rect pop1rec = new Rect();
                final Rect pop2rec = new Rect();
                final Rect pop3rec = new Rect();
                pop1.getGlobalVisibleRect(pop1rec);
                pop2.getGlobalVisibleRect(pop2rec);
                pop3.getGlobalVisibleRect(pop3rec);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    oldx = (int) event.getRawX();
                    oldy = (int) event.getRawY();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (popview.getVisibility() == View.VISIBLE) {
                        everyx = (int) event.getRawX();
                        everyy = (int) event.getRawY();
                        if (pop1rec.contains(everyx, everyy)) {
                            if (!pop1vib) {
                                poptext.setVisibility(View.VISIBLE);
                                poptext.setText(getResources().getString(R.string.link_copy));
                                vibe.vibrate(40);
                                pop1vib = true;
                            }
                        } else if (pop2rec.contains(everyx, everyy)) {
                            if (!pop2vib) {
                                poptext.setVisibility(View.VISIBLE);
                                poptext.setText(getResources().getString(R.string.link_open));
                                vibe.vibrate(40);
                                pop2vib = true;
                            }
                        } else if (pop3rec.contains(everyx, everyy)) {
                            if (!pop3vib) {
                                poptext.setVisibility(View.VISIBLE);
                                poptext.setText(getResources().getString(R.string.link_share));
                                vibe.vibrate(40);
                                pop3vib = true;

                            }
                        } else {
                            poptext.setVisibility(View.INVISIBLE);
                            pop1vib = false;
                            pop2vib = false;
                            pop3vib = false;
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (popview.getVisibility() == View.VISIBLE) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            int initialRadius = popviewui.getWidth();
                            Animator animm = ViewAnimationUtils.createCircularReveal(popviewui, oldx, oldy, initialRadius, 0);
                            animm.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    popview.setVisibility(View.INVISIBLE);
                                    popwebView.loadUrl("about:blank");
                                    if (popsitetitle.getText() == "about:blank") {
                                        popsitetitle.setText("waiting...");
                                    }
                                }

                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    popview.startAnimation(fadepopoff);
                                    popwebView.setClickable(true);
                                }
                            });
                            animm.start();
                        } else {
                            popview.setVisibility(View.INVISIBLE);
                            popwebView.loadUrl("about:blank");
                            popview.startAnimation(fadepopoff);
                            popwebView.setClickable(true);
                        }
                        if (pop1rec.contains(everyx, everyy)) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("url", longurl);
                            clipboard.setPrimaryClip(clip);
                        } else if (pop2rec.contains(everyx, everyy)) {
                            webView.loadUrl(longurl);
                        } else if (pop3rec.contains(everyx, everyy)) {
                            Intent msgg = new Intent(Intent.ACTION_SEND);
                            msgg.addCategory(Intent.CATEGORY_DEFAULT);
                            msgg.putExtra(Intent.EXTRA_TEXT, longurl);
                            msgg.setType("text/plain");
                            startActivity(Intent.createChooser(msgg, getResources().getString(R.string.share) + " "));
                        }
                        btn_expand.setVisibility(View.VISIBLE);
                        if (black) {
                            btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                        } else {
                            btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                        }
                    }
                }
                if (popview.getVisibility() == View.VISIBLE) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    final class WebBrowserClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http")) {
                webView.loadUrl(url);
            } else if (url.startsWith("copy")) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(url);
            } else if (url.startsWith("sms")) {
                if (url.split("=").length > 1) {
                    url = url.split("=")[1];
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                intent.putExtra("sms_body", url);
                startActivity(intent);
            } else if (url.startsWith("kakolink:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("mailto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("tel")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (url.contains("play.google.com")) {
                // play.google.com 도메인이면서 App 링크인 경우에는 market:// 로 변경
                String[] params = url.split("details");
                if (params.length > 1) {
                    String uurl = "market://details" + params[1];
                    webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uurl)));
                }
            } else {
                whenError();
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            boolean sslagree = prefsdef.getBoolean("sslagree", false);
            if (sslagree) {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
                alt_bld.setMessage(getResources().getString(R.string.ssltitle)).setCancelable(
                        false)
                        .setPositiveButton(getResources().getString(R.string.btn_no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        handler.cancel();
                                        webView.goBack();
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.btn_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        handler.proceed();  //SSL 에러가 발생해도 계속 진행!
                                        Snackbar.make(bg, getResources().getString(R.string.recommendout), Snackbar.LENGTH_LONG)
                                                .setAction(getResources().getString(R.string.back), new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        webView.goBack();
                                                    }
                                                }).show();
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("SSL Error");
                alert.setCanceledOnTouchOutside(false);
                // Icon f
                alert.show();
            } else {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
                alt_bld.setMessage(getResources().getString(R.string.ssltitle)).setCancelable(
                        false)
                        .setPositiveButton(getResources().getString(R.string.back),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        webView.goBack();
                                    }
                                });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("SSL Error");
                alert.setCanceledOnTouchOutside(false);
                // Icon f
                alert.show();
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {  // 페이지로딩이 시작되면
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);  // 프로그레스바 보이기
            if (url_edit.isFocused()) {
                url_edit.clearFocus();
                url_edit.setVisibility(View.INVISIBLE);
            }
            if (btn_expand.getVisibility() == View.INVISIBLE) {
                btn_expand.setVisibility(View.VISIBLE);
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress.setVisibility(View.INVISIBLE);
            webView.setScrollContainer(true);
            if (!(url_edit.isFocused())) {
                String ed = webView.getUrl();
                url_edit.setText(ed);
            }
            btn_url.setText(webView.getTitle());
            CookieSyncManager.getInstance().sync();
        }

        public void whenError() {
            webView.loadUrl("file:///android_asset/error.html");
            Snackbar.make(bg, getResources().getString(R.string.errorloadpage), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.btn_ok), new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            webView.loadUrl(homepagelink);
                        }
                    }).show();
        }

    }

    public void setPopView() {
        popwebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        WebSettings webSettingss = popwebView.getSettings();  // 웹세팅 객체 생성
        webSettingss.setSaveFormData(true);
        webSettingss.setSupportZoom(false);
        webSettingss.setDisplayZoomControls(false);
        webSettingss.setDomStorageEnabled(true);
        webSettingss.setSupportMultipleWindows(true);
        webSettingss.setPluginState(WebSettings.PluginState.ON);
        webSettingss.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettingss.setUseWideViewPort(true);
        webSettingss.setLoadWithOverviewMode(true);
        webSettingss.setLoadsImagesAutomatically(true);
        webSettingss.setJavaScriptEnabled(true);
        popwebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        popwebView.setVerticalScrollBarEnabled(false);
        popwebView.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popwebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popwebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
        } else {
            popwebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30");
        }
        popwebView.setWebViewClient(new WebBrowserrClient());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final String url = (String) msg.getData().get("url");
            if (url != null) {
                touch3dShowing(url);
            }
        }
    };

    final class WebBrowserrClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            popsitetitle.setText("waiting...");
            if (url.startsWith("http")) {
                popwebView.loadUrl(url);
            } else if (url.startsWith("copy")) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(url);
            } else if (url.startsWith("sms")) {
                if (url.split("=").length > 1) {
                    url = url.split("=")[1];
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                intent.putExtra("sms_body", url);
                startActivity(intent);
            } else if (url.startsWith("kakolink:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("mailto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("tel")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else if (url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (url.contains("play.google.com")) {
                // play.google.com 도메인이면서 App 링크인 경우에는 market:// 로 변경
                String[] params = url.split("details");
                if (params.length > 1) {
                    String uurl = "market://details" + params[1];
                    popwebView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uurl)));
                }
            } else {
                whenError();
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {  // 페이지로딩이 시작되면
            super.onPageStarted(view, url, favicon);
            popsitetitle.setText("waiting...");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            popsitetitle.setText(popwebView.getTitle());
            CookieSyncManager.getInstance().sync();
        }

        public void whenError() {
            popwebView.loadUrl("file:///android_asset/error.html");
        }

    }

    public void setClickable() {
        url_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    url_edit.setVisibility(View.INVISIBLE);
                    String ed = webView.getUrl();
                    url_edit.setText(ed);
                    imm.hideSoftInputFromWindow(url_edit.getWindowToken(), 0);
                }
            }
        });

        btn_expand.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (actionbtns.getVisibility() == View.INVISIBLE) {
                    actionbtns.setVisibility(View.VISIBLE);
                    btn_url.setText(webView.getTitle());
                    actionbtns.setVisibility(View.VISIBLE);
                    imm.hideSoftInputFromWindow(url_edit.getWindowToken(), 0);
                    if (black == true) {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#464646")));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteiccancel));
                    } else {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackiccancel));
                    }
                } else {
                    actionbtns.setVisibility(View.INVISIBLE);
                    if (black == true) {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                    } else {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                    }
                }
            }
        });
        btn_menu.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (menu.getVisibility() == View.INVISIBLE) {
                    menuShowing(btn_menu);
                } else {
                    menuHiding();
                }
            }
        });

        menu.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            }
        });

        btn_url.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int cx = (btn_url.getLeft() + btn_url.getRight()) / 2;
                    int cy = (btn_url.getTop() + btn_url.getBottom()) / 2;
                    int finalRadius = Math.max(url_edit.getWidth(), url_edit.getHeight());
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(url_edit, cx, cy, 0, finalRadius);
                    url_edit.setVisibility(View.VISIBLE);
                    actionbtns.setVisibility(View.INVISIBLE);
                    if (black == true) {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                    } else {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                    }
                    anim.start();
                } else {
                    url_edit.setVisibility(View.VISIBLE);
                    actionbtns.setVisibility(View.INVISIBLE);
                    if (black == true) {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                    } else {
                        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                        btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                    }
                }
                url_edit.requestFocus();

                //키보드 보이게 하는 부분
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        btn_url.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(bg, getResources().getString(R.string.scrolltop), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.btn_ok), new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                webView.scrollTo(0, 0);
                            }
                        }).show();
                return true;
            }
        });

        btn_expand.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuShowing(btn_expand);
                return true;
            }
        });

        url_edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        String strUrl = url_edit.getText().toString();
                        if (strUrl.startsWith("http")) {
                            webView.loadUrl(strUrl);
                        } else if (strUrl.contains(".")) {
                            strUrl = "http://" + strUrl;
                            webView.loadUrl(strUrl);
                        } else {
                            strUrl = searchengineurl + strUrl;
                            webView.loadUrl(strUrl);
                        }
                        imm.hideSoftInputFromWindow(url_edit.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void menuHiding() {
        if (menu.getVisibility() == View.VISIBLE) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int initialRadius = menu.getWidth();
                int cxx = (btn_menu.getRootView().getLeft() + btn_menu.getRootView().getRight()) / 2;
                int cyy = (btn_menu.getRootView().getTop() + btn_menu.getRootView().getBottom());
                Animator animm = ViewAnimationUtils.createCircularReveal(menu, cxx, cyy, initialRadius, 0);
                animm.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        menu.setVisibility(View.INVISIBLE);
                        btn_url.setVisibility(View.VISIBLE);
                        actionbtns.setVisibility(View.INVISIBLE);
                        btn_expand.setVisibility(View.VISIBLE);
                        if (black) {
                            btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                            btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                        } else {
                            btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                            btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                        }
                    }
                });
                animm.start();
            } else {
                menu.setVisibility(View.INVISIBLE);
                btn_url.setVisibility(View.VISIBLE);
                actionbtns.setVisibility(View.INVISIBLE);
                btn_expand.setVisibility(View.VISIBLE);
                if (black) {
                    btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
                } else {
                    btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
                }
            }
        }
    }

    public void menuShowing(View vview) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] locc = {1, 1};
            vview.getLocationInWindow(locc);
            int cxxxxx = locc[0];
            int cyxxyy = locc[1];
            int finalRadius = Math.max(menu.getWidth(), menu.getHeight());
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(menu, cxxxxx, cyxxyy, 0, finalRadius);
            menu.setVisibility(View.VISIBLE);
            btn_url.setVisibility(View.INVISIBLE);
            actionbtns.setVisibility(View.INVISIBLE);
            btn_expand.setVisibility(View.INVISIBLE);
            if (black) {
                btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
            } else {
                btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
            }
            imm.hideSoftInputFromWindow(url_edit.getWindowToken(), 0);
            anim.start();
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(bgcolor);
            getWindow().setNavigationBarColor(bgcolor);
        } else {
            menu.setVisibility(View.VISIBLE);
            btn_url.setVisibility(View.INVISIBLE);
            actionbtns.setVisibility(View.INVISIBLE);
            btn_expand.setVisibility(View.INVISIBLE);
            if (black) {
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
            } else {
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
            }
            imm.hideSoftInputFromWindow(url_edit.getWindowToken(), 0);
        }

    }

    public void touch3dShowing(final String url) {
        popwebView.setClickable(false);
        popview.setBackgroundColor(Color.parseColor("#90FFFFFF"));
        actionbtns.setVisibility(View.INVISIBLE);
        btn_expand.setVisibility(View.INVISIBLE);
        popwebView.loadUrl(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int finalRadius = Math.max(popviewui.getWidth(), popviewui.getHeight());
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(popviewui, oldx, oldy, 0, finalRadius);
            popview.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            popview.setVisibility(View.VISIBLE);
        }
        popviewui.startAnimation(fadepopon);
        poptext.setVisibility(View.INVISIBLE);
        longurl = url;
    }

    public void screenShotBlur() {
        View blurview = webView;
        blurview.setDrawingCacheEnabled(true);
        blurview.buildDrawingCache();
        mBitmap = Bitmap.createBitmap(blurview.getMeasuredWidth(),
                blurview.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        cv = new Canvas(mBitmap);
        cv.drawBitmap(mBitmap, blurview.getMeasuredWidth(),
                blurview.getMeasuredHeight(), paint);
        blurview.draw(cv);
        blurview.setDrawingCacheEnabled(false);
        blurview.destroyDrawingCache();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            final RenderScript rs = RenderScript.create(MainActivity.this);
            final Allocation input = Allocation.createFromBitmap(rs, mBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(23.f);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(mBitmap);
        }
        //popviewbg.setImageBitmap(mBitmap);
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, getResources().getString(R.string.permissionfordownload), Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);
            }
        }
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                }
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            } else {
                myCallback.invoke(myOrigin, true, false);
            }
        } else {
            boolean geoaccessno = pref.getBoolean("geono", false);
            boolean newgeoaccess = pref.getBoolean("geonew", true);
            if (newgeoaccess) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.loactionagree_title));
                builder.setMessage(getResources().getString(R.string.locationagree_text));
                builder.setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myCallback.invoke(myOrigin, true, false);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("geono", false);
                        editor.putBoolean("geonew", false);
                        editor.commit();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.btn_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myCallback.invoke(myOrigin, false, false);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("geono", true);
                        editor.putBoolean("geonew", false);
                        editor.commit();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                if (geoaccessno) {
                    myCallback.invoke(myOrigin, false, false);
                } else {
                    myCallback.invoke(myOrigin, true, false);
                }
            }
        }
    }

    public void themeSkin() {
        int b1bg;
        int b2bg;
        int b3bg;
        int b4bg;
        int b5bg;
        int b6bg;
        int b7bg;
        int b8bg;
        int b9bg;
        int expandbtncolor;
        int btnurlbg;
        int btnmenuimg;
        int btntabimg;
        Drawable d;
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/Themebrowser/themenow");
        if (bitmap == null) {
            d = null;
        } else {
            d = new BitmapDrawable(getResources(), bitmap);
        }
        //Bitmap resize = Bitmap.createScaledBitmap(bitmap, 300, 400, true);//사이즈 조절
        black = prefsdef.getBoolean("t-black", false);
        bgcolor = pref.getInt("t-color", Color.parseColor("#4dd0e1"));
        if (black) {
            btn_url.setTextColor(Color.parseColor("#FFFFFF"));
            b1bg = R.drawable.blackb1;
            b2bg = R.drawable.blackb2;
            b3bg = R.drawable.blackb3;
            b4bg = R.drawable.blackb4;
            b5bg = R.drawable.blackb5;
            b6bg = R.drawable.blackb6;
            b7bg = R.drawable.blackb7;
            b8bg = R.drawable.blackb8;
            b9bg = R.drawable.blackb9;
            expandbtncolor = R.drawable.blackicexpand;
            btnmenuimg = R.drawable.blackicmenu;
            btntabimg = R.drawable.blackictab;
            btnurlbg = R.drawable.blackbgurl;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            btn_url.setTextColor(Color.parseColor("#000000"));
            b1bg = R.drawable.whiteb1;
            b2bg = R.drawable.whiteb2;
            b3bg = R.drawable.whiteb3;
            b4bg = R.drawable.whiteb4;
            b5bg = R.drawable.whiteb5;
            b6bg = R.drawable.whiteb6;
            b7bg = R.drawable.whiteb7;
            b8bg = R.drawable.whiteb8;
            b9bg = R.drawable.whiteb9;
            expandbtncolor = R.drawable.whiteicexpand;
            btnmenuimg = R.drawable.whiteicmenu;
            btntabimg = R.drawable.whiteictab;
            btnurlbg = R.drawable.whitebgurl;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
        btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
        actionbtns.setBackgroundColor(bgcolor);
        poptext.setBackgroundColor(Color.parseColor("#FFFFFF"));
        poptext.setTextColor(Color.parseColor("#464646"));
        popsitetitle.setTextColor(Color.parseColor("#000000"));
        if (d == null) {
            bg.setBackgroundColor(Color.parseColor("#4dd0e1"));
            menu.setBackgroundColor(Color.parseColor("#4dd0e1"));
        } else {
            bg.setBackgroundDrawable(d);
            menu.setBackgroundDrawable(d);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ;
            pop1.setBackground(ContextCompat.getDrawable(this, R.drawable.content_copy_black_108x108));
            pop2.setBackground(ContextCompat.getDrawable(this, R.drawable.open_in_browser_black_108x108));
            pop3.setBackground(ContextCompat.getDrawable(this, R.drawable.share_black_108x108));
            btn_menu.setBackground(ContextCompat.getDrawable(this, btnmenuimg));
            btn_tab.setBackground(ContextCompat.getDrawable(this, btntabimg));
            btn_url.setBackground(ContextCompat.getDrawable(this, btnurlbg));
        } else {
            pop1.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_copy_black_108x108));
            pop2.setBackgroundDrawable(getResources().getDrawable(R.drawable.open_in_browser_black_108x108));
            pop3.setBackgroundDrawable(getResources().getDrawable(R.drawable.share_black_108x108));
            btn_menu.setBackgroundDrawable(getResources().getDrawable(btnmenuimg));
            btn_tab.setBackgroundDrawable(getResources().getDrawable(btntabimg));
            btn_url.setBackgroundDrawable(getResources().getDrawable(btnurlbg));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean blackorwhite = pref.getBoolean("t-black", false);
            if (blackorwhite) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
        btn_expand.setImageDrawable(getResources().getDrawable(expandbtncolor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(bgcolor);
            getWindow().setNavigationBarColor(bgcolor);
            b1.setBackground(ContextCompat.getDrawable(this, b1bg));
            b2.setBackground(ContextCompat.getDrawable(this, b2bg));
            b3.setBackground(ContextCompat.getDrawable(this, b3bg));
            b4.setBackground(ContextCompat.getDrawable(this, b4bg));
            b5.setBackground(ContextCompat.getDrawable(this, b5bg));
            b6.setBackground(ContextCompat.getDrawable(this, b6bg));
            b7.setBackground(ContextCompat.getDrawable(this, b7bg));
            b8.setBackground(ContextCompat.getDrawable(this, b8bg));
            b9.setBackground(ContextCompat.getDrawable(this, b9bg));
        } else {
            b1.setBackgroundDrawable(getResources().getDrawable(b1bg));
            b2.setBackgroundDrawable(getResources().getDrawable(b2bg));
            b3.setBackgroundDrawable(getResources().getDrawable(b3bg));
            b4.setBackgroundDrawable(getResources().getDrawable(b4bg));
            b5.setBackgroundDrawable(getResources().getDrawable(b5bg));
            b6.setBackgroundDrawable(getResources().getDrawable(b6bg));
            b7.setBackgroundDrawable(getResources().getDrawable(b7bg));
            b8.setBackgroundDrawable(getResources().getDrawable(b8bg));
            b9.setBackgroundDrawable(getResources().getDrawable(b9bg));
        }
    }

    public void downloadFile(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Download");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(uRl)
                .setDescription("Image")
                .setDestinationInExternalPublicDir("/Download", uRl + "jpg");
        mgr.enqueue(request);
    }

    private BroadcastReceiver completeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Resources res = context.getResources();
            Snackbar.make(bg, getResources().getString(R.string.downloaded), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.btn_ok), new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                        }
                    }).show();

        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, getResources().getString(R.string.problemdownload), Toast.LENGTH_SHORT).show();
                }
                break;
            case MY_PERMISSION_REQUEST_LOCATION:
                if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setsettingsnow();
        themeSkin();
        CookieSyncManager.createInstance(this);
        if (!(getIntent().getData() == null)) {
            webView.loadUrl(getIntent().getData().toString());
        }
        menuHiding();
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
        webView.pauseTimers();
        unregisterReceiver(completeReceiver);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String url_before = webView.getUrl();
        editor.putString("url_before", url_before);
        editor.putInt("status_before", statusbarcolor);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
        IntentFilter completeFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(completeReceiver, completeFilter);
        CookieSyncManager.getInstance().startSync();
        if (!(getIntent().getData() == null)) {
            webView.loadUrl(getIntent().getData().toString());
        }
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("status_before", statusbarcolor);
        editor.apply();
        setsettingsnow();
        themeSkin();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String url_before = webView.getUrl();
        editor.putString("url_before", url_before);
        editor.putInt("status_before", statusbarcolor);
        editor.apply();
        setsettingsnow();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if (!(getIntent().getData() == null)) {
            webView.loadUrl(getIntent().getData().toString());
        }
        menuHiding();
        setsettingsnow();
        themeSkin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_FILE_CHOOSE && mUploadMsg != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            mUploadMsg.onReceiveValue(result);
            mUploadMsg = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MediaPlayer player = new MediaPlayer();
        if (player.isPlaying()) {
            player.stop();
        } else if (popview.getVisibility() == View.VISIBLE) {
            popview.setVisibility(View.INVISIBLE);
        } else if (menu.getVisibility() == View.VISIBLE) {
            menuHiding();
            return true;
        } else if (actionbtns.getVisibility() == View.VISIBLE) {
            actionbtns.setVisibility(View.INVISIBLE);
            if (black) {
                btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.blackicexpand));
            } else {
                btn_expand.setBackgroundTintList(ColorStateList.valueOf(bgcolor));
                btn_expand.setImageDrawable(getResources().getDrawable(R.drawable.whiteicexpand));
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            if (url_edit.getVisibility() == View.VISIBLE) {
                url_edit.setVisibility(View.INVISIBLE);
                String ed = webView.getUrl().toString();
                url_edit.setText(ed);
                return false;
            } else {
                webView.goBack();
                Handler mHandler;
                Runnable mRunnable;
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String ed = webView.getUrl();
                        url_edit.setText(ed);
                    }
                };
                mHandler = new Handler();
                mHandler.postDelayed(mRunnable, 300);
                return false;
            }
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            menuShowing(menu);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setsettingsnow() {
        homepagelink = prefsdef.getString("homepage", "http://www.google.com");
        searchengineurl = prefsdef.getString("searchengine", "https://www.google.co.kr/search?q=");
        if (!(homepagelink.contains("http"))) {
            homepagelink = "http://" + homepagelink;
        }

        if (prefsdef.getBoolean("fullscreen", false)) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(0, 0, 0, 0);
            webView.setLayoutParams(params);
            decorView = getWindow().getDecorView();
            uiOption = getWindow().getDecorView().getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        } else {
            uiOption |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(0, getStatusBarHeight(), 0, 0);
            webView.setLayoutParams(params);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.b1:
                    webView.goBack();
                    Handler mHandler;
                    Runnable mRunnable;
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String ed = webView.getUrl();
                            url_edit.setText(ed);
                        }
                    };
                    mHandler = new Handler();
                    mHandler.postDelayed(mRunnable, 300);
                    menuHiding();
                    Snackbar.make(bg, getResources().getString(R.string.back), Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.b2:
                    webView.reload();
                    menuHiding();
                    Snackbar.make(bg, getResources().getString(R.string.reload), Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.b3:
                    webView.goForward();
                    Handler mmHandler;
                    Runnable mmRunnable;
                    mmRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String ed = webView.getUrl();
                            url_edit.setText(ed);
                            menuHiding();
                        }
                    };
                    mmHandler = new Handler();
                    mmHandler.postDelayed(mmRunnable, 1000);
                    menuHiding();
                    Snackbar.make(bg, getResources().getString(R.string.forward), Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.b4:
                    webView.loadUrl(homepagelink);
                    menuHiding();
                    Snackbar.make(bg, getResources().getString(R.string.loadhomepage), Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.b5:
                    String srursh = webView.getUrl();
                    Intent msg = new Intent(Intent.ACTION_SEND);
                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                    msg.putExtra(Intent.EXTRA_TEXT, srursh);
                    msg.setType("text/plain");
                    startActivity(Intent.createChooser(msg, getResources().getString(R.string.share)));
                    break;
                case R.id.b6:
                    if (!yesorno) {
                        webView.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
                        yesorno = true;
                        webView.reload();
                        menuHiding();
                        Snackbar.make(bg, getResources().getString(R.string.desktopmodeon), Snackbar.LENGTH_SHORT).show();
                        b6.setAlpha(0.5f);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; " + deviceName + "Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; " + deviceName + " Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
                        } else {
                            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30");
                        }
                        yesorno = false;
                        webView.reload();
                        menuHiding();
                        Snackbar.make(bg, getResources().getString(R.string.desktopmodeoff), Snackbar.LENGTH_SHORT).show();
                        b6.setAlpha(1f);
                    }
                    break;
                case R.id.b7:
                    startActivity(new Intent(MainActivity.this, ThemeActivity.class));
                    break;
                case R.id.b8:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
                case R.id.b9:
                    Snackbar.make(bg, getResources().getString(R.string.finish), Snackbar.LENGTH_LONG)
                            .setAction(getResources().getString(R.string.btn_ok), new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            }).show();
                    break;
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        // super.onWindowFocusChanged(hasFocus);

        if (prefsdef.getBoolean("fullscreen", false)) {
            if (hasFocus) {
                decorView.setSystemUiVisibility(uiOption);
            }
        }
    }
}
