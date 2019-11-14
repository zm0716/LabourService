package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CityModel;
import com.agilefinger.labourservice.bean.DistrictModel;
import com.agilefinger.labourservice.bean.ProvinceBean;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.view.custom.MyNumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class AddressDiaLog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private EditText m_add_editext;
    private Button m_back;
    private Button m_add;
    private boolean isShowCancel = false, isCanBack = false;
    MyNumberPicker mProvicePicker;
    MyNumberPicker mCityPicker;
    MyNumberPicker mDistrictPicker;

    List<ProvinceBean> mProvinceList;
    /**
     * 所有省集合
     */
    protected String[] mProvinceDatas;
    /**
     * 所有市的集合
     * key - 省 value - 市
     */
    protected Map<String, Object> mCitisDatasMap = new HashMap<>();

    protected Map<String, Object> mDistrictMap = new HashMap<>();

    protected List<Map<String, Object>> cityList = new ArrayList<>();
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    private String cityModelName;
    private String districtName;
    private String districtcode;
    private String provincecode;
    private String provinceName;
    private String cityModelCode;
    private ProvinceBean provinceBean;
    private CityModel cityModel;

    public AddressDiaLog(Context context, List<ProvinceBean> mProvinceList, OnCloseListener listener) {
        super(context, R.style.dialog2);
        this.mContext = context;
        this.listener = listener;
        this.mProvinceList = mProvinceList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_dialog);
        setCanceledOnTouchOutside(false);
        if (!isCanBack) {
            setCancelable(false);
        }
        initView();
    }

    private void initView() {
        findViewById(R.id.m_add_back).setOnClickListener(this);
        findViewById(R.id.m_add_address).setOnClickListener(this);

        mProvicePicker = (MyNumberPicker) findViewById(R.id.monthpicker);
        mCityPicker = (MyNumberPicker) findViewById(R.id.hourpicker);
        mDistrictPicker = (MyNumberPicker) findViewById(R.id.minuteicker);
        initProvinceDatas();
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_add_back:
                dismiss();
                break;

            case R.id.m_add_address:

                if (mProvicePicker.getValue() != 0) {
                    provinceBean = mProvinceList.get(mProvicePicker.getValue() - 1);
                    provincecode = String.valueOf(provinceBean.getCode());
                    provinceName = provinceBean.getName();
                } else {
                    ToastUtil.showShort(mContext, "请选择地址");
                    return;
                }

                if (mCityPicker.getValue() != 0) {
                    cityModel = provinceBean.getCity().get(mCityPicker.getValue() - 1);
                    cityModelName = cityModel.getName();
                    cityModelCode = String.valueOf(cityModel.getCode());
//                    cityModelName = provinceBean.getName();
                } else if (mCityPicker.getValue() == 0) {
                    cityModelName = "不限";
                    cityModelCode = "";
//                    ToastUtil.showShort(mContext, "请选择地址");
//                    return;
                }
                if (mDistrictPicker.getValue() != 0) {
                    DistrictModel districtModel = null;
                    if (cityModel.getCountry() != null) {
                        districtModel = cityModel.getCountry().get(mDistrictPicker.getValue() - 1);
                        districtName = districtModel.getName();
                        districtcode = String.valueOf(districtModel.getCode());
                    }
                } else {
                    districtName = "不限";
                    districtcode = "";
//                    ToastUtil.showShort(mContext, "请选择地址");
//                    return;
                }

                listener.onClick(this, provincecode, cityModelCode, districtcode, provinceName, cityModelName, districtName);

                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, String provincecode, String cityModelCode, String districtcode, String... param);

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);


    }

    void init() {
        mProvicePicker.setWrapSelectorWheel(false);
        mProvicePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, final int newVal) {
                String proName = mProvinceDatas[newVal];
//                Integer code = mCode[newVal];
                String[] pros = (String[]) mCitisDatasMap.get(proName);
                mCityPicker.setDisplayedValues(null);
                mDistrictPicker.setDisplayedValues(null);
                setCityPickerTextSize((String[]) mCitisDatasMap.get(mProvinceDatas[newVal]));

                setDisTrictPickerTextSize((String[]) (mDistrictMap.get(((String[]) mCitisDatasMap.get(mProvinceDatas[newVal]))[0] + mProvinceDatas[newVal])));
                mCityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        mDistrictPicker.setDisplayedValues(null);
                        setDisTrictPickerTextSize((String[]) mDistrictMap.get(mCityPicker.getDisplayedValues()[i1] + mProvinceDatas[newVal]));
                    }
                });
                mCityPicker.setWrapSelectorWheel(false);
            }
        });
        mCityPicker.setWrapSelectorWheel(false);
        mCityPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDistrictPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setCityPickerTextSize((String[]) mCitisDatasMap.get(mProvinceDatas[0]));
        setDisTrictPickerTextSize((String[]) mDistrictMap.get(((String[]) mCitisDatasMap.get(mProvinceDatas[0]))[0] + mProvinceDatas[0]));
    }

    private void setCityPickerTextSize(String[] cities) {
        if (null != mCityPicker) {
            mCityPicker.setMinValue(0);
//            mCityPicker.setMaxValue(cities.length - 1);
            mCityPicker.setMaxValue(cities.length > 0 ? cities.length - 1 : 0);
            mCityPicker.setWrapSelectorWheel(false);
            mCityPicker.setDisplayedValues(cities);
            mCityPicker.setValue(0);

            mProvicePicker.setMinValue(0);
//            mProvicePicker.setMaxValue(mProvinceDatas.length - 1);
            mProvicePicker.setMaxValue(mProvinceDatas.length > 0 ? mProvinceDatas.length - 1 : 0);
            mProvicePicker.setWrapSelectorWheel(false);
            mProvicePicker.setDisplayedValues(mProvinceDatas);
        }
    }

    protected void setDisTrictPickerTextSize(String[] districts) {
        if (mDistrictPicker != null) {
            if (districts != null && districts.length > 0) {
                mDistrictPicker.setMinValue(0);
                mDistrictPicker.setMaxValue(districts.length > 0 ? districts.length - 1 : 0);
                mDistrictPicker.setWrapSelectorWheel(false);
                mDistrictPicker.setDisplayedValues(districts);
            } else {
                mDistrictPicker.setMinValue(0);
                mDistrictPicker.setMaxValue(0);
                mDistrictPicker.setWrapSelectorWheel(false);
                mDistrictPicker.setDisplayedValues(new String[]{"不限"});
            }
        }
    }

    /**
     * 初始化省市数据
     */
    protected void initProvinceDatas() {

        // 初始化默认选中的省、市
        if (mProvinceList != null && !mProvinceList.isEmpty()) {
            mCurrentProviceName = mProvinceList.get(0).getName();
            List<CityModel> cityList = mProvinceList.get(0).getCity();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
            }
        }
//        Log.d("wnw", list.size() + "");
        mProvinceDatas = new String[mProvinceList.size() + 1];

        for (int i = 0; i < mProvinceList.size() + 1; i++) {
            // 遍历所有省的数据
            //为了加不限
//            mProvinceDatas[i] = mProvinceList.get(i).getName();

            if (i == 0) {
                mProvinceDatas[i] = "不限";
                String[] n = new String[]{"不限"};
                mCitisDatasMap.put("不限", n);
            } else {
                mProvinceDatas[i] = mProvinceList.get(i - 1).getName();

                List<CityModel> cityList = mProvinceList.get(i - 1).getCity();
                String[] cityNames = new String[cityList.size() + 1];
                for (int j = 0; j < cityList.size() + 1; j++) {
                    // 遍历省下面的所有市的数据
                    //为了家不限
//                cityNames[j] = cityList.get(j).getName();
                    // 遍历省下面的所有市的数据
                    if (j == 0) {
                        cityNames[j] = "不限";
                        String[] n = new String[]{"不限"};
                        mDistrictMap.put("不限", n);
                    } else {
                        if (cityList.get(j - 1).getName() != null) {
                            cityNames[j] = cityList.get(j - 1).getName();
                        }
                        //所有县
                        List<DistrictModel> districtList = cityList.get(j - 1).getCountry();
                        if (districtList != null && districtList.size() > 0) {
                            String[] districtNames = new String[districtList.size() + 1];

                            for (int d = 0; d < districtList.size() + 1; d++) {
                                if (d == 0) {
                                    districtNames[d] = "不限";
                                } else {
                                    if (districtList.get(d - 1).getName() != null) {
                                        districtNames[d] = districtList.get(d - 1).getName();
                                    }
                                }
                                Log.e("districtNames", districtNames.length + "");
                                //为了加不限
//                        districtNames[d] = districtList.get(d).getName();
                            }
//                    if (cityList.get(j).getName().equals("市辖区")) {
//                        mDistrictMap.put(mProvinceList.get(i).getName(), districtNames);
//                    } else if (cityList.get(j).getName().equals("县")) {
//
//                    } else {

                            mDistrictMap.put(cityList.get(j - 1).getName() + mProvinceList.get(i - 1).getName(), districtNames);

//                    }
                        }

                    }
                }
                Log.e("cityNames", cityNames.length + "");

                for (int j = 0; j < cityNames.length; j++) {
//                if (cityNames[j].equals("市辖区")) {
//                    // 省-市的数据，保存到mCitisDatasMap
//                    cityNames = new String[]{mProvinceList.get(i).getName()};
//                    mCitisDatasMap.put(mProvinceList.get(i).getName(), cityNames);
//                    break;
//                } else if (cityNames[j].equals("县")) {
//
//                    break;
//                } else {
                    mCitisDatasMap.put(mProvinceList.get(i - 1).getName(), cityNames);

//                    break;
//                }
                }
            }


        }
    }
}