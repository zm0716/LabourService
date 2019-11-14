package com.agilefinger.labourservice.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agilefinger.labourservice.R;


public class LoadingDialog {

    private static Dialog mLoadingDialog;

    public static Dialog showLoading(Activity context, String msg, boolean cancelable) {
        try {
            disDialog();
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);

            loadingText.setText(msg);
            mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            ProgressBar pro_bar = (ProgressBar) view.findViewById(R.id.pro_bar);
            if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
                final Drawable drawable =  context.getApplicationContext().
                        getResources().getDrawable(R.drawable.loading_dialog_progressbar);
                pro_bar.setIndeterminateDrawable(drawable);
            }

            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();

        }catch (Exception e){
            disDialog();
        }
        return mLoadingDialog;
    }

    public static Dialog showLoading(Activity context) {
        try {
            disDialog();
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
            loadingText.setText("加载中...");
            mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            ProgressBar pro_bar = (ProgressBar) view.findViewById(R.id.pro_bar);
            if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
                final Drawable drawable =  context.getApplicationContext().
                        getResources().getDrawable(R.drawable.loading_dialog_progressbar);
                pro_bar.setIndeterminateDrawable(drawable);
            }
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();
        }catch (Exception e){
            disDialog();
        }

        return mLoadingDialog;
    }
    public static Dialog showLoading2(Activity context) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
            loadingText.setText("加载中...");
            mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            ProgressBar pro_bar = (ProgressBar) view.findViewById(R.id.pro_bar);
            if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
                final Drawable drawable =  context.getApplicationContext().
                        getResources().getDrawable(R.drawable.loading_dialog_progressbar);
                pro_bar.setIndeterminateDrawable(drawable);
            }
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();
        }catch (Exception e){
            disDialog();
        }

        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void disDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
    }
}