package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BuildingNoAdapter;
import com.agilefinger.labourservice.adapter.DirectionAdapter;
import com.agilefinger.labourservice.adapter.FloorAdapter;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class FloorDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private BuildingNoAdapter buildingNoAdapter = new BuildingNoAdapter();
    private FloorAdapter floorAdapter = new FloorAdapter();
    private DirectionAdapter directionAdapter = new DirectionAdapter();
    private List<BuildingNoBean> data;
    private int bPosition = -1, sPosition = -1, fPosition = -1;
    private BuildingNoBean buildingNoBean;
    private FloorBean floorBean;
    private DirectionBean directionBean;

    public FloorDialog(Context context, List<BuildingNoBean> data, BuildingNoBean buildingNoBean, DirectionBean directionBean, FloorBean floorBean, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.data = data;
        this.buildingNoBean = buildingNoBean;
        this.directionBean = directionBean;
        this.floorBean = floorBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_floor);
        initView();
    }

    private void initView() {
        findViewById(R.id.dialog_floor_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_floor_tv_complete).setOnClickListener(this);
        //楼号
        RecyclerView rvBuildingNo = findViewById(R.id.dialog_floor_rv_build_no);
        rvBuildingNo.setLayoutManager(new LinearLayoutManager(mContext));
        rvBuildingNo.setAdapter(buildingNoAdapter);
        List<BuildingNoBean> mList = new ArrayList<>();
        mList.addAll(this.data);
        Log.d("传递数据",GsonUtils.toJson(directionBean));
        buildingNoAdapter.setNewData(mList);
        buildingNoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BuildingNoBean buildingNo = (BuildingNoBean) adapter.getItem(position);
                buildingNoBean = buildingNo;
                if (bPosition != position && bPosition != -1) {
                    BuildingNoBean oldBuilding = (BuildingNoBean) adapter.getItem(bPosition);
                    oldBuilding.setCheck(false);
                    buildingNoAdapter.notifyItemChanged(bPosition);
                    sPosition = fPosition = -1;
                }
                buildingNo.setCheck(true);
                bPosition = position;
                buildingNoAdapter.notifyItemChanged(position);
                //directionAdapter.setNewData(buildingNo.getSurface());
                //floorAdapter.setNewData(buildingNo.getFloor());
                setDefault(position);
            }
        });
        //立面
        RecyclerView rvDirection = findViewById(R.id.dialog_floor_rv_direction);
        rvDirection.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvDirection.setAdapter(directionAdapter);
        directionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DirectionBean direction = (DirectionBean) adapter.getItem(position);
                directionBean = direction;
                if (sPosition != position && sPosition != -1) {
                    DirectionBean oldDirection = (DirectionBean) adapter.getItem(sPosition);
                    oldDirection.setCheck(false);
                    directionAdapter.notifyItemChanged(sPosition);
                }
                direction.setCheck(true);
                sPosition = position;
                directionAdapter.notifyItemChanged(position);
            }
        });
        //楼层
        RecyclerView rvFloor = findViewById(R.id.dialog_floor_rv_floor);
        rvFloor.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvFloor.setAdapter(floorAdapter);
        floorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FloorBean floor = (FloorBean) adapter.getItem(position);
                floorBean = floor;
                if (fPosition != position && fPosition != -1) {
                    FloorBean oldFloor = (FloorBean) adapter.getItem(fPosition);
                    oldFloor.setCheck(false);
                    floorAdapter.notifyItemChanged(fPosition);
                }
                floor.setCheck(true);
                fPosition = position;
                floorAdapter.notifyItemChanged(position);
            }
        });
        APPUtils.removeRecyclerViewDefaultAnimation(rvBuildingNo);
        APPUtils.removeRecyclerViewDefaultAnimation(rvDirection);
        APPUtils.removeRecyclerViewDefaultAnimation(rvFloor);
        //初始化
        if (this.data != null && this.data.size() > 0) {
            if (buildingNoBean != null && directionBean != null && floorBean != null) {
                for (int i = 0; i < this.data.size(); i++) {
                    BuildingNoBean buildingNo = this.data.get(i);
                    if (buildingNo.getId().equals(buildingNoBean.getId())) {
                        bPosition = i;
                        this.data.get(i).setCheck(true);
                        buildingNoBean.setName(buildingNo.getName());
                        for (int j = 0; j < buildingNo.getSurface().size(); j++) {
                            if (buildingNo.getSurface().get(j).getId().equals(directionBean.getId())) {
                                sPosition = j;
                                directionBean.setName(buildingNo.getSurface().get(j).getName());
                                this.data.get(i).getSurface().get(j).setCheck(true);
                            } else {
                                this.data.get(i).getSurface().get(j).setCheck(false);
                            }
                        }
                        for (int j = 0; j < buildingNo.getFloor().size(); j++) {
                            if (buildingNo.getFloor().get(j).getId().equals(floorBean.getId())) {
                                fPosition = j;
                                floorBean.setName(buildingNo.getFloor().get(j).getName());
                                this.data.get(i).getFloor().get(j).setCheck(true);
                            } else {
                                this.data.get(i).getFloor().get(j).setCheck(false);
                            }
                        }
                    } else {
                        this.data.get(i).setCheck(false);
                    }
                }
                buildingNoAdapter.setNewData(this.data);
                directionAdapter.setNewData(this.data.get(bPosition).getSurface());
                floorAdapter.setNewData(this.data.get(bPosition).getFloor());
            } else {
                bPosition = 0;
                buildingNoBean = buildingNoAdapter.getItem(bPosition);
                buildingNoAdapter.getItem(bPosition).setCheck(true);
                buildingNoAdapter.notifyItemChanged(bPosition);
                setDefault(0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_floor_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_floor_tv_complete:
                if (fPosition == -1) {
                    ToastUtils.showShortSafe("请选择楼层");
                    return;
                }
                if (sPosition == -1) {
                    ToastUtils.showShortSafe("请选择立面");
                    return;
                }
                listener.onClick(this, true, buildingNoBean, floorBean, directionBean);
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, Object... param);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, AutoSizeUtils.dp2px(mContext, 160), 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    private void setDefault(int position) {
        List<BuildingNoBean> mList = new ArrayList<>();
        mList.addAll(this.data);
        if (mList != null && mList.size() > 0) {
            BuildingNoBean buildingNo = mList.get(position);
            if (buildingNo.getSurface() != null && buildingNo.getSurface().size() > 0) {
                List<DirectionBean> dList = buildingNo.getSurface();
                for (int i = 0; i < dList.size(); i++) {
                    dList.get(i).setCheck(false);
                }
                directionBean = dList.get(0);
                dList.get(0).setCheck(true);
                directionAdapter.setNewData(dList);
                sPosition = 0;
            }
            if (buildingNo.getFloor() != null && buildingNo.getFloor().size() > 0) {
                List<FloorBean> fList = buildingNo.getFloor();
                for (int i = 0; i < fList.size(); i++) {
                    fList.get(i).setCheck(false);
                }
                floorBean = fList.get(0);
                fList.get(0).setCheck(true);
                floorAdapter.setNewData(fList);
                fPosition = 0;
            }
        }
    }
}