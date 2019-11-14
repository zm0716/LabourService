package com.agilefinger.labourservice.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.LoginBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.LoginDao;
import com.agilefinger.labourservice.utils.OSUtils;
import com.agilefinger.labourservice.utils.PictureUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.PhotoPopupWindow;
import com.agilefinger.labourservice.widget.PlusImage.MainConstant;
import com.agilefinger.labourservice.widget.PlusImage.PlusImageActivity;
import com.flyco.roundview.RoundLinearLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.utils.ScreenUtils;


public abstract class BaseActivity extends Activity implements BaseFuncIml, View.OnClickListener {
    protected String TAG = BaseActivity.class.getSimpleName();
    protected InputMethodManager inputMethodManager;
    private Unbinder unbinder;
    protected boolean isTransStatusBar = false;
    protected LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(initLayoutView());
        setTranslucentStatus();
        initParams(savedInstanceState);
        initData();
        initView();
        initListener();
        initLoad();
    }

    protected void initStatusBar() {

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //LoadingDialog.disDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();

    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private static final int TIME = 1000;
    private static long lastClickTime = 0;

    /**
     * 处理快速双击，多击事件，在TIME时间内只执行一次事件
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - lastClickTime;
        if (0 < timeInterval && timeInterval < TIME) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
    }

    @Override
    public void initParams(Bundle bundle) {

    }

    //public abstract int initLayoutView();
    @Override
    public int initLayoutView() {
        return 0;
    }

    @Override
    public void initData() {
        TAG = this.getClass().getSimpleName();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        LoginDao loginDao = new LoginDao(LSApplication.context);
        List<LoginBean> loginList = loginDao.queryLoginAll();
        if (loginList != null && loginList.size() > 0) {
            loginBean = loginList.get(0);
        }
    }

    protected boolean checkLogin() {
        if (loginBean == null) {
            showToast("请登录");
            return false;
        }
        return true;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    /**
     * 收起软键盘
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /*protected void setEnable(RoundTextView btn, boolean b) {
        btn.setEnabled(b);
        if (b) {
            btn.getDelegate().setBackgroundColor(getResources().getColor(R.color.bgBlue_2177FF));
        } else {
            btn.getDelegate().setBackgroundColor(getResources().getColor(R.color.bgGray_999999));
        }
    }*/

    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.transparent);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void setToolbar(String title, boolean showOp, boolean showTxtOp, String txtOp) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_ll_wrapper);
        TextView tvTitle = findViewById(R.id.layout_toolbar_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = findViewById(R.id.layout_toolbar_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                back();
            }
        });
        if (showOp) {
            ImageView ivOperate = findViewById(R.id.layout_toolbar_iv_operate);
            /*if (img != 0) {
                ivOperate.setImageResource(img);
            }*/
            ivOperate.setVisibility(View.VISIBLE);
            ivOperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toOperate();
                }
            });
        }
        if (showTxtOp) {
            TextView tvOperate = findViewById(R.id.layout_toolbar_tv_operate);
            tvOperate.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(txtOp)) {
                tvOperate.setText(txtOp);
            }
            tvOperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toOperate();
                }
            });
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            llWrapper.setPadding(0, result + 3, 0, 0);
        }
    }


    protected void setToolbar2(String title, boolean showOp, boolean showTxtOp, String txtOp) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_ll_wrapper);
        TextView tvTitle = findViewById(R.id.layout_toolbar_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = findViewById(R.id.layout_toolbar_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                back2();
            }
        });
        if (showOp) {
            ImageView ivOperate = findViewById(R.id.layout_toolbar_iv_operate);
            /*if (img != 0) {
                ivOperate.setImageResource(img);
            }*/
            ivOperate.setVisibility(View.VISIBLE);
            ivOperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toOperate();
                }
            });
        }
        if (showTxtOp) {
            TextView tvOperate = findViewById(R.id.layout_toolbar_tv_operate);
            tvOperate.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(txtOp)) {
                tvOperate.setText(txtOp);
            }
            tvOperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toOperate();
                }
            });
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            llWrapper.setPadding(0, result + 3, 0, 0);
        }
    }


    protected void back2() {

    }

    protected void back() {
        finish();
    }

    protected void toOperate() {
    }

    private final static int MIUI = 0;
    private final static int FLYME = 1;
    private final static int COMMON = 2;

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark) {
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarFontIconDark(COMMON);
            } else if (OSUtils.isMiui()) {
                setStatusBarFontIconDark(MIUI);
            } else if (OSUtils.isFlyme()) {
                setStatusBarFontIconDark(FLYME);
            }
        }
    }

    /**
     * 设置文字颜色
     */
    public void setStatusBarFontIconDark(int type) {
        switch (type) {
            case MIUI:
                setMiuiUI(true);
                break;
            case COMMON:
                setCommonUI();
                break;
            case FLYME:
                setFlymeUI(true);
                break;
        }
    }

    public void setCommonUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }

    public void setFlymeUI(boolean dark) {
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMiuiUI(boolean dark) {
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setToolbarSearch(String title) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_search_ll_wrapper);
        TextView tvTitle = findViewById(R.id.layout_toolbar_search_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = findViewById(R.id.layout_toolbar_search_iv_back);
        final RoundLinearLayout rllTxt = findViewById(R.id.layout_toolbar_search_rll_txt);
        final EditText editText = findViewById(R.id.layout_toolbar_search_et_search);
        final RoundLinearLayout rllSearch = findViewById(R.id.layout_toolbar_search_rll_search);
        rllTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rllTxt.setVisibility(View.GONE);
                editText.requestFocus();

                rllSearch.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            llWrapper.setPadding(0, result + 3, 0, 0);
        }
    }

    protected void setToolbarSearch2(String hint) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_search_2_ll_wrapper);
        TextView tvOperate = findViewById(R.id.layout_toolbar_search_2_tv_operate);
        EditText etSearch = findViewById(R.id.layout_toolbar_search_2_et_search);
        etSearch.setHint(hint);
        tvOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            llWrapper.setPadding(0, result + 3, 0, 0);
        }
    }

    protected void setToolbarTrans(String title) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_trans_ll_wrapper);
        TextView tvTitle = findViewById(R.id.layout_toolbar_trans_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = findViewById(R.id.layout_toolbar_trans_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = ScreenUtils.getStatusBarHeight();
            llWrapper.setPadding(0, result + 5, 0, 0);
        }
    }

    protected void setToolbarWhite(String title) {
        LinearLayout llWrapper = findViewById(R.id.layout_toolbar_white_ll_wrapper);
        TextView tvTitle = findViewById(R.id.layout_toolbar_white_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = findViewById(R.id.layout_toolbar_white_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = ScreenUtils.getStatusBarHeight();
            llWrapper.setPadding(0, result + 3, 0, 0);
        }
        setImmersiveStatusBar(true);
    }

    protected void closeDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    //////////////////////////////////////////////////////// 拍照、相册选取逻辑 /////////////////////////////////////////////////
    protected final int REQUEST_IMAGE_GET = 0;
    protected final int REQUEST_IMAGE_CAPTURE = 1;
    protected final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    PhotoPopupWindow mPhotoPopupWindow;
    protected String IMAGE_FILE_NAME = "";
    protected String IMAGE_PATH = "";

    protected void showPhotoPop() {
        PictureUtils.mkdirMyPetRootDirectory();
        IMAGE_FILE_NAME = System.currentTimeMillis() + ".jpg";
        mPhotoPopupWindow = new PhotoPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 文件权限申请
                if (ContextCompat.checkSelfPermission(BaseActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 权限还没有授予，进行申请
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200); // 申请的 requestCode 为 200
                } else {
                    // 如果权限已经申请过，直接进行图片选择
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        showToast("未找到图片查看器");
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 拍照及文件权限申请
                if (ContextCompat.checkSelfPermission(BaseActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(BaseActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 权限还没有授予，进行申请
                    ActivityCompat.requestPermissions(BaseActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300); // 申请的 requestCode 为 300
                } else {
                    // 权限已经申请，直接拍照
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                }
            }
        });
        View rootView = LayoutInflater.from(this).inflate(initLayoutView(), null);
        mPhotoPopupWindow.showAtLocation(rootView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        //getMyPetRootDirectory()得到的是Environment.getExternalStorageDirectory() + File.separator+"Labour"
        //也就是我之前创建的存放头像的文件夹（目录）
        File pictureFile = new File(PictureUtils.getMyPetRootDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //这一句非常重要
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //""中的内容是随意的，但最好用package名.provider名的形式，清晰明了
            pictureUri = FileProvider.getUriForFile(this, "com.agilefinger.labourservice.fileprovider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照,拍照的结果存到oictureUri对应的路径中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        Log.e(TAG, "before take photo" + pictureUri.toString());
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    Log.e(TAG, "before show");
                    File cropFile = new File(PictureUtils.getMyPetRootDirectory(), "crop.jpg");
                    Uri cropUri = Uri.fromFile(cropFile);
                    setPicToView(cropUri);
                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    Uri uri = PictureUtils.getImageUri(this, data);
                    //startPhotoZoom(uri);
                    setPicToView(uri);
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File pictureFile = new File(PictureUtils.getMyPetRootDirectory(), IMAGE_FILE_NAME);
                    Uri pictureUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pictureUri = FileProvider.getUriForFile(this, "com.agilefinger.labourservice.fileprovider", pictureFile);
                        Log.e(TAG, "picURI=" + pictureUri.toString());
                    } else {
                        pictureUri = Uri.fromFile(pictureFile);
                    }
                    setPicToView(pictureUri);
                    //startPhotoZoom(pictureUri);
                    break;
                default:
            }
        } else {
            Log.e(TAG, "result = " + resultCode + ",request = " + requestCode);
        }
    }

    public void setPicToView(Uri uri) {
        if (uri != null) {
            Bitmap photo = null;
            try {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dirFile = new File(PictureUtils.getMyPetRootDirectory(), "images");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.d(TAG, "in setPicToView->文件夹创建失败");
                    } else {
                        Log.d(TAG, "in setPicToView->文件夹创建成功");
                    }
                }
                File file = new File(dirFile, IMAGE_FILE_NAME);
                IMAGE_PATH = file.getPath();
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            showHeadImage();
            //circleImageView_user_head.setImageBitmap(InfoPrefs.getData(Constants.UserInfo.GEAD_IMAGE));
        }
    }

    private void startPhotoZoom(Uri uri) {
        Log.d(TAG, "Uri = " + uri.toString());
        //保存裁剪后的图片
        File cropFile = new File(PictureUtils.getMyPetRootDirectory(), "crop.jpg");
        try {
            if (cropFile.exists()) {
                cropFile.delete();
                Log.e(TAG, "delete");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri cropUri;
        cropUri = Uri.fromFile(cropFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        Log.e(TAG, "cropUri = " + cropUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        showToast("未找到图片查看器");
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showHeadImage() {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sdcard是否存在
        if (isSdCardExist) {
            File file = new File(IMAGE_PATH);
            if (file.exists()) {
                showImage(IMAGE_PATH);
            } else {
                Log.e(TAG, "no file");
                showImage(IMAGE_PATH);
            }
        } else {
            Log.e(TAG, "no SD card");
            showImage(IMAGE_PATH);
        }
    }

    protected void showImage(String image_path) {
    }

    private MediaPlayer mediaPlayer;
    private ImageView voiceImageView;
    private AnimationDrawable voiceAnimation;
    private String lastPath = "";

    protected void playVoice(String path, ImageView imagePlayer) {
        if (isFastDoubleClick() || TextUtils.isEmpty(path)) {
            return;
        }
        try {
            stopVoice();
            if (path.equals(lastPath)) {
                lastPath = "";
                return;
            }
            LoadingDialog.showLoading(this);
            lastPath = path;
            voiceImageView = imagePlayer;
            voiceImageView.setImageResource(R.drawable.voice_from_icon);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    LoadingDialog.disDialog();
                    voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
                    voiceAnimation.start();
                    mediaPlayer.start();

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LoadingDialog.disDialog();
                    stopVoice();
                }
            });
        } catch (IOException e) {
            LoadingDialog.disDialog();
            showToast(e.getMessage());
        }
    }

    protected void stopVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        if (voiceAnimation != null) {
            voiceAnimation.stop();
            voiceAnimation = null;
        }
        if (voiceImageView != null) {
            voiceImageView.setImageResource(R.mipmap.ic_voice);
        }
    }


    /////////////////////////////////////////
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected SwipeRefreshLayout swipeRefreshLayout2;

    protected void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    protected void refresh() {
    }

    protected void initSwipeRefreshLayout2() {
        swipeRefreshLayout2 = findViewById(R.id.refresh2);
        swipeRefreshLayout2.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh2();
            }
        });
    }

    protected void refresh2() {
    }

    protected View emptyView(RecyclerView rvList, String txt) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_wrap, (ViewGroup) rvList.getParent(), false);
        TextView tvTxt = view.findViewById(R.id.layout_empty_wrap_tv_txt);
        tvTxt.setText(txt);
        return view;
    }

    protected View emptySearchInspectionProjectView(RecyclerView rvList, String txt) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_search, (ViewGroup) rvList.getParent(), false);
        TextView tvTxt = view.findViewById(R.id.layout_empty_search_tv_txt);
        tvTxt.setText(txt);
        return view;
    }

    protected View emptyInspectionPositionView(RecyclerView rvList) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_inspection_position, (ViewGroup) rvList.getParent(), false);
        return view;
    }

    protected boolean mShouldScroll;
    //记录目标项位置
    protected int mToPosition;

    protected void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    protected void plusImage(List<String> imageList, int position) {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < imageList.size() - 1; i++) {
            String item = imageList.get(i);
            if (item.contains("/storage/")) {
                item = "file://" + item;
            } else if (!item.contains(URL.BASE_URL_)) {
                item = URL.BASE_URL_ + item;
            } else {
                item = URL.BASE_URL_ + item;
            }
            mList.add(item);
        }
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) mList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }


    /*
     *点击外部软键盘消失
     * **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                hideKeyboard(ev, view, this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
