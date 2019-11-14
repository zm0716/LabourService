package com.agilefinger.labourservice.utils;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BaseWebView extends WebView {

	public BaseWebView(Context context) {
		super(context);
		initSettings(context);
	}
	public BaseWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSettings(context);
	}
	public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initSettings(context);
		if (Build.VERSION.SDK_INT >= 11) {
			removeJavascriptInterface("searchBoxJavaBridge_");
			removeJavascriptInterface("accessibility");
			removeJavascriptInterface("accessibilityTraversal");
		}
	}
	private void initSettings(Context context) {
		WebSettings settings = getSettings();
		settings.setTextZoom(100);
		//settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setUseWideViewPort(true);
		//settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setJavaScriptEnabled(true);
		setVerticalScrollBarEnabled(false);
		setHorizontalScrollBarEnabled(false);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// 解决对某些标签的不支持出现白屏
		settings.setDomStorageEnabled(true);
		// 设置可以访问文件
		settings.setAllowFileAccess(true);


		/*settings.setDomStorageEnabled(true);
		settings.setAppCacheMaxSize(1024*1024*8);
		String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
		Log.d("lujing",appCachePath);
		settings.setAppCachePath(appCachePath);
		settings.setAllowFileAccess(true);
		settings.setAppCacheEnabled(true);*/

		//settings.setPluginsEnabled(true);
		//允许webview对文件的操作
		settings.setAllowUniversalAccessFromFileURLs(true);

		settings.setAllowFileAccessFromFileURLs(true);

		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
	}

}