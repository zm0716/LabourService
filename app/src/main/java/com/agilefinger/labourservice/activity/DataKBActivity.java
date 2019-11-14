package com.agilefinger.labourservice.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.BaseWebView;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataKBActivity extends BaseActivity {

    @BindView(R.id.wb)
    BaseWebView wb;
    private String compyId;
    private int type;
    private String url;
    private String title;
    private String url2;

    @Override
    public int initLayoutView() {
        return R.layout.activity_data_kb;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("数据看板", false, false, "");
        url2 = "";

        compyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        type = getIntent().getExtras().getInt("type");
//        title = getIntent().getExtras().getString("title");
        if (type != 0) {
            url = getIntent().getExtras().getString("url") + "&uid=" + loginBean.getUser_id() + "&cid=" + compyId;
        } else {
//            setToolbar("数据看板", false, false, "");
            url = URL.WEBVIEW_URL + "/index.php/chart/Panel/index.html?uid=" + loginBean.getUser_id() + "&cid=" + compyId;
        }


        LoadingDialog.showLoading(DataKBActivity.this);
        // wb = findViewById(R.id.wb);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // rm_mi_id=21EF8DAA0D64390BA900DA0301152781&rm_mi_no=1&rm_score=100
        wb.loadUrl(url);
        Log.d("quuuu", url);


        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadingDialog.disDialog();
                url2 = url;

//                String title = view.getTitle();

//                setToolbar(title, false, false, "");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Uri parse = Uri.parse(url);
                Log.e("Uri", parse.getPath());

                if (url2 != null && !url2.equals("")) {
                    DataKBActivity.this.type += 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("url", String.valueOf(parse));
//                    bundle.putString("title", title);
                    bundle.putInt("type", DataKBActivity.this.type);
                    bundle.putString(Constants.EXTRA_DATA_COMPANY, compyId);
                    IntentUtils.startActivity(DataKBActivity.this, DataKBActivity.class, bundle);

                }
                return true;
            }
        });


//        wb.addJavascriptInterface(new Object());
    }

}
