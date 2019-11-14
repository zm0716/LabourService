package com.agilefinger.labourservice.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;

import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildMoreImageBean;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BigImageActivity extends BaseActivity {
    @BindView(R.id.m_photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.m_img_name)
    TextView mImgName;
    @BindView(R.id.m_jd)
    TextView mJd;
    @BindView(R.id.m_wd)
    TextView mWd;
    @BindView(R.id.m_height)
    TextView mHeight;
    @BindView(R.id.m_wd_line)
    LinearLayout mWdLine;
    //    @BindView(R.id.m_psr)
//    TextView mPsr;
    @BindView(R.id.m_address)
    TextView mAddress;
    @BindView(R.id.m_type)
    TextView mType;
    @BindView(R.id.m_time)
    TextView mTime;
    @BindView(R.id.m_line)
    LinearLayout mLine;
    private int aClass = 0;
    private KanBanDetailBean.ProjectImgBean bean;
    private String compyId;
    private boolean a = true;


    @Override
    public int initLayoutView() {
        return R.layout.activity_big_image;
    }

    private SlidrConfig mSlidrConfig;
    private SlidrConfig.Builder mBuilder;

    @Override
    public void initView() {
        super.initView();

//        int primary = getResources().getColor(R.color.colorCoolGreen);
//        int secondary = getResources().getColor(R.color.colorCoolGreen);
        mBuilder = new SlidrConfig.Builder()
//                .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
//                .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                .position(SlidrPosition.VERTICAL)//从左边滑动
//                .position(SlidrPosition.HORIZONTAL)//从左边滑动
                .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
                .scrimEndAlpha(0.2f)//滑动结束时两个Activity之间的透明度
                .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                .distanceThreshold(.35f);//滑动位移占屏幕的百分比，超过这个间距就切换Activity
        mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);

//
        final RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.portrait)//图片加载出来前，显示的图片
                .fallback(R.mipmap.portrait) //url为空的时候,显示的图片
                .error(R.mipmap.portrait);//图片加载失败后，显示的图片
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        bean = (KanBanDetailBean.ProjectImgBean) getIntent().getExtras().getSerializable("Bean");
        compyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);

        Glide.with(this).load(URL.IMAGE_URL + bean.getImg_url()).apply(options).into(mPhotoView);

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a) {
                    mLine.setVisibility(View.VISIBLE);
                    a = false;
                } else {
                    mLine.setVisibility(View.GONE);
                    a = true;
                }
            }
        });





//        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Glide.with(BigImageActivity.this).load(URL.IMAGE_URL + bean.getImg_url()).apply(options).into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@android.support.annotation.NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                        BitmapDrawable bd = (BitmapDrawable) resource;
//                        Bitmap bitmap = bd.getBitmap();
//                        saveImage(bitmap);
//                    }
//                });
//                return true;
//            }
//        });

        mImgName.setText(bean.getProject_name());
        if (bean.getType() == 1) {
            mType.setText("巡检任务");
            mTime.setText(bean.getTime());
            mJd.setText("经度：" + bean.getLongitude() + ",");
            mWd.setText("纬度：" + bean.getLatitude() + ",");
            mHeight.setText("高度：" + bean.getHeight());
//            mPsr.setText(bean.getUser_name());
            mAddress.setText(bean.getUser_name() + "拍摄于" + bean.getPosition());

        } else {
            mType.setText("整改派单");
            mWdLine.setVisibility(View.GONE);
            mTime.setText(bean.getTime());
//            mJd.setText("经度：" + bean.getLongitude() + ",");
//            mWd.setText("纬度：" + bean.getLatitude() + ",");
//            mHeight.setText("高度：" + bean.getHeight());
//            mPsr.setText(bean.getUser_name());
            mAddress.setText(bean.getUser_name() + "拍摄于" + bean.getPosition());
        }

    }

    @OnClick({R.id.m_line})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.m_line:
                if (bean.getType() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("taskId", bean.getTarget_id());
                    IntentUtils.startActivity(this, InspectionDetailActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_DATA, bean.getTarget_id());
                    bundle.putString(Constants.EXTRA_DATA_COMPANY, compyId);
                    IntentUtils.startActivity(this, RectificationDetailActivity.class, bundle);
                }
                break;
        }
    }


    private void saveImage(Bitmap image) {
        String saveImagePath = null;
        Random random = new Random();
        String imageFileName = "JPEG_" + "down" + random.nextInt(10) + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES) + "test");

        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            saveImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fout = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(saveImagePath);
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        }
//        return saveImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

}
