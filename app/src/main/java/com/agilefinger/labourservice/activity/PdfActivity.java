package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.BaseWebView;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import butterknife.BindView;

public class PdfActivity extends BaseActivity {
    @BindView(R.id.wb)
    BaseWebView wb;
    private String m_score;
    private String mi_no;
    private String mi_id;

    @Override
    public int initLayoutView() {
        return R.layout.activity_pdf;
    }
    @Override
    public void initView() {
        super.initView();
        setToolbar("报告", false, false, "");
        Intent intent = getIntent();
        mi_id = intent.getStringExtra("mi_id");
        mi_no = intent.getStringExtra("mi_no");
        m_score = intent.getStringExtra("m_score");
        LoadingDialog.showLoading(PdfActivity.this);
       // wb = findViewById(R.id.wb);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // rm_mi_id=21EF8DAA0D64390BA900DA0301152781&rm_mi_no=1&rm_score=100
        wb.loadUrl(URL.BASE_URL_5 + "?rm_mi_id="+mi_id+"&rm_mi_no="+mi_no+"&rm_score="+m_score+"");

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
                Log.d("quuuu", url);

            }
        });
    }

}
