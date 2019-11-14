package com.agilefinger.labourservice.base;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.LoginBean;
import com.agilefinger.labourservice.db.LoginDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSize;

public abstract class JBaseFragment extends Fragment implements View.OnClickListener {

    protected boolean isVisible;
    protected final String TAG = getClass().getSimpleName();
    private boolean isPrepared; // 标志位，标志控件已经初始化完成。
    protected boolean isFirst;//标志第一次加载
    protected Unbinder unbinder;
    protected View mView;
    protected LoginBean loginBean;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        isFirst = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView) {
            // 强制竖屏显示
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            int layoutResId = getCreateViewLayoutId();
            if (layoutResId > 0)
                mView = inflater.inflate(getCreateViewLayoutId(), container, false);

            ButterKnife.bind(this, mView);
            // 解决点击穿透问题
            mView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        initData(mView);
        initView(mView, savedInstanceState);
        initListener();
        return mView;
    }

    protected void initListener() {
    }

    protected abstract int getCreateViewLayoutId();


    protected abstract void initView(View mView, Bundle savedInstanceState);


    public void initData(View view) {
        LoginDao loginDao = new LoginDao(LSApplication.context);
        List<LoginBean> loginList = loginDao.queryLoginAll();
        if (loginList != null && loginList.size() > 0) {
            loginBean = loginList.get(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AutoSize.autoConvertDensity(getActivity(), 375, true);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (isPrepared) {
                if (isFirst) {
                    isFirst = false;
                    lazyLoadOnlyOne();
                }else{
                    lazyLoad();
                }
            }
        } else {
            isVisible = false;
        }
    }

    protected void lazyLoad() {
    }

    /**
     * 懒加载，有且加载一次
     */
    protected abstract void lazyLoadOnlyOne();

    /**
     * 通过泛型来简化findViewById
     * <E extends View>申明此方法为范型方法
     */
    protected final <E extends View> E getView(View view, int id) {
        try {
            return (E) view.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
    }


    /**
     * 处理快速双击，多击事件，在TIME时间内只执行一次事件
     *
     * @return
     */
    private static final int TIME = 1000;
    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - lastClickTime;
        if (0 < timeInterval && timeInterval < TIME) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void setToolbar(Boolean isShowBack, String title) {
        LinearLayout llWrapper = mView.findViewById(R.id.layout_toolbar_ll_wrapper);
        TextView tvTitle = mView.findViewById(R.id.layout_toolbar_tv_title);
        tvTitle.setText(title);
        ImageView ivBack = mView.findViewById(R.id.layout_toolbar_iv_back);
        if (!isShowBack) {
            ivBack.setVisibility(View.GONE);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = getStatusBarHeight();
            llWrapper.setPadding(0, result+3, 0, 0);
        }
    }
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected void initSwipeRefreshLayout() {
        swipeRefreshLayout = mView.findViewById(R.id.refresh);
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
}
