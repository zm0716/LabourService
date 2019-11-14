package com.agilefinger.labourservice.common;

/**
 * Created by 86251 on 2019/5/31.
 */

public class Constants {
    public static final String CUSTOMIZE = "CUSTOMIZE";//自定义
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_DATA_TYPE = "EXTRA_DATA_TYPE";
    public static final String EXTRA_DATA_STATUS = "EXTRA_DATA_STATUS";
    public static final String EXTRA_DATA_ = "EXTRA_DATA_";
    public static final String EXTRA_DATA__ = "EXTRA_DATA__";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_DATA_COMPANY = "EXTRA_DATA_COMPANY";
    public static final String EXTRA_DATA_BUILDING = "EXTRA_DATA_BUILDING";
    public static final String EXTRA_DATA_PROJECT = "EXTRA_DATA_PROJECT";

    public static final String CHOOSE_TYPE_SINGLE = "SINGLE";//单选
    public static final String CHOOSE_TYPE_MULTI = "MULTI";//多选

    public static final int REQUEST_CHOOSE_PROJECT = 1000;//选择项目
    public static final int REQUEST_CHOOSE_PROJECT_TYPE = 1001;//选择项目
    public static final int REQUEST_CHOOSE_PROJECT_STATUS = 1002;//选择项目
    public static final int REQUEST_CHOOSE_STAFF_ZP = 1001;//选择员工
    public static final int REQUEST_CHOOSE_STAFF_ZG = 1003;//选择员工
    public static final int REQUEST_CHOOSE_STAFF_KF = 1002;//选择开发商
    public static final int REQUEST_CHOOSE_STAFF_XJ = 1003;//选择项目经理
    public static final int REQUEST_CHOOSE_BUILDING = 1002;//选择施工楼号
    public static final int REQUEST_FILTER = 1004;//选择施工楼号
    public static final int REQUEST_WEATHER = 1005;//选择天气
    public static final int REQUEST_TEAM = 1006;//施工班组

    public static final int LAUNCH_DELAY_TIME = 2000;

    public static final String NETWORK_REFRESH = "NETWORK_REFRESH";//刷新数据
    public static final String NETWORK_LOAD_MORE = "NETWORK_LOAD_MORE";//加载更多
    public static final int PAGE_SIZE = 10;

    public static final String ZG_STATE_1 = "1";//整改中
    public static final String ZG_STATE_2 = "2";//待验收
    public static final String ZG_STATE_3 = "3";//已完成
    public static final String ZG_STATE_4 = "4";//已拒绝
    public static final String ZG_STATE_5 = "5";//已撤销


    //权限

    public static final String AUTH_94f = "Apppatrola94";
    public static final String AUTH_05 = "Appmenua05";
    public static final String AUTH_93 = "Appprogressa93";//施工进度
    public static final String AUTH_94 = "Appadda94";//新增整改派单
    public static final String AUTH_95 = "Appdispatcha95";//整改派单
    public static final String APP_BOARD_A94 = "Appboarda94";//项目看板
    public static final String APP_DATA_A94 = "Appdataa94";//数据看板

    public static final String ZG_BOHUI = "整改驳回";//整改回复
    public static final String ZG_HUIFU = "整改回复";//整改回复
    public static final String ZG_TONGGUO = "确认通过";//确认通过
    public static final String ZG_JUJUE = "确认忽略";//确认忽略
    public static final String ZG_ZHIPAI = "指派";//指派

    public static final String ZG_MAN = "ZG_MAN";
    public static final String ZP_MAN = "ZP_MAN";

    public static final String FILTER_BEGIN_TIME = "FILTER_BEGIN_TIME";
    public static final String FILTER_END_TIME = "FILTER_END_TIME";
    public static final String FILTER_SOURCE = "FILTER_SOURCE";
    public static final String FILTER_BEGIN_END_TIME = "FILTER_BEGIN_END_TIME";
    public static final String FILTER_END_END_TIME = "FILTER_END_END_TIME";
    public static final String FILTER_ZG_MAN = "FILTER_ZG_MAN";
    public static final String FILTER_ZP_MAN = "FILTER_ZP_MAN";

    public static final String FILTER_JL_MAN = "FILTER_JL_MAN";
    public static final String FILTER_KF_MAN = "FILTER_KF_MAN";
    public static final String FILTER_PC = "FILTER_PC";
    public static final String FILTER_CC = "FILTER_CC";
    public static final String FILTER_DC = "FILTER_DC";
    public static final String FILTER_PC_NAME = "FILTER_PC_NAME";
    public static final String FILTER_CC_NAME = "FILTER_CC_NAME";
    public static final String FILTER_DC_NAME = "FILTER_DC_NAME";

    public static final String STATE_ME_FUZE = "STATE_ME_FUZE";//我负责
    public static final String STATE_ME_FENPAI = "STATE_ME_FENPAI";//我分派

    public static final String STATE_ME_CHUANGJIAN = "STATE_ME_CHUANGJIAN";//我創建的
    public static final String STATE_ME_ZHIXING = "STATE_ME_ZHIXING";//我直行

    public static final String JC_STATE_1 = "2";//檢測中
    public static final String WQ_STATE_2 = "1";//未啟動
    public static final String YT_STATE_3 = "4";//已停止
    public static final String YW_STATE_4 = "3";//已完成
//  public static final String ZG_STATE_5 = "5";//已撤销

    public static final String TYPE_EDIT = "TYPE_EDIT";//编辑
}
