package com.agilefinger.labourservice.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.agilefinger.labourservice.R;
import com.flyco.roundview.RoundTextView;

import me.jessyan.autosize.utils.AutoSizeUtils;
import me.jessyan.autosize.utils.ScreenUtils;

public class PhotoPopupWindow extends PopupWindow {
    private static final String TAG = "PhotoPopupWindow";
    private View mView; // PopupWindow 菜单布局
    private Context mContext; // 上下文参数
    private View.OnClickListener mSelectListener; // 相册选取的点击监听器
    private View.OnClickListener mCaptureListener; // 拍照的点击监听器

    public PhotoPopupWindow(Activity context, View.OnClickListener captureListener, View.OnClickListener selectListener) {
        super(context);
        this.mContext = context;
        this.mSelectListener = selectListener;
        this.mCaptureListener = captureListener;
        Init();
    }

    /**
     * 设置布局以及点击事件
     */
    private void Init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.pop_photo, null);
        RoundTextView rtvCamera = (RoundTextView) mView.findViewById(R.id.pop_photo_rtv_camera);
        RoundTextView rtvAlbum = (RoundTextView) mView.findViewById(R.id.pop_photo_rtv_album);
        RoundTextView rtvCancel = (RoundTextView) mView.findViewById(R.id.pop_photo_rtv_cancel);

        rtvCamera.setOnClickListener(mSelectListener);
        rtvAlbum.setOnClickListener(mCaptureListener);
        rtvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(ScreenUtils.getScreenSize(mContext)[1]);
        //sdk > 21 解决 标题栏没有办法遮罩的问题
        this.setClippingEnabled(false);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.ll_pop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}