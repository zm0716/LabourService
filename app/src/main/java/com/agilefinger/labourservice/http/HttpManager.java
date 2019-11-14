package com.agilefinger.labourservice.http;


import android.util.Log;

import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.GsonUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Akira on 2018/5/28.
 */

public class HttpManager {

    private static HttpManager sInstance;

    private HttpEngine mHttpEngine = new HttpEngine();

    public static HttpManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpManager.class) {
                if (sInstance == null) {
                    sInstance = new HttpManager();
                }
            }
        }
        return sInstance;
    }


    public void send_sms(String phone, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("user_mobile", phone)
                .build();
        Request request = new Request.Builder().url(URL.SEND_SMS)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void loginCode(String phone, String code, HttpEngine.OnResponseCallback<HttpResponse.Login> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("bus_code", "1")
                .add("session_id", "3dnnnd3igfbfq0fj2e5vsk5pl5")
                .add("authority_type", "1")
                .add("user_mobile", phone)
                .add("code", code)
                .build();
        Request request = new Request.Builder().url(URL.LOGIN_CODE)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Login.class, callback);
    }

    public void getCompany(String id, HttpEngine.OnResponseCallback<HttpResponse.Company> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .build();
        Request request = new Request.Builder().url(URL.GET_COMPANY)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Company.class, callback);
    }

    public void getWork(String id, String cid, HttpEngine.OnResponseCallback<HttpResponse.Work> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .build();
        Request request = new Request.Builder().url(URL.GET_WORK)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Work.class, callback);

    }


    /**
     * 上传图片
     */
    public void uploadImage(File file, HttpEngine.OnResponseCallback<HttpResponse.UploadFile> callback) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("multipart-formdata"), file);
        String filename = file.getName();
        requestBody.addFormDataPart("file", filename, body);
        requestBody.addFormDataPart("type", "img");
        requestBody.addFormDataPart("is_thumb", "0");
        Request request = new Request.Builder().url(URL.UPLOAD_FILE)
                .post(requestBody.build())
                .build();
        mHttpEngine.request(request, HttpResponse.UploadFile.class, callback);
    }

    /**
     * 上传音频
     */
    public void uploadVoice(File file, HttpEngine.OnResponseCallback<HttpResponse.UploadFile> callback) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("multipart-formdata"), file);
        String filename = file.getName();
        requestBody.addFormDataPart("file", filename, body);
        requestBody.addFormDataPart("type", "audio");
        requestBody.addFormDataPart("is_thumb", "0");
        Request request = new Request.Builder().url(URL.UPLOAD_FILE)
                .post(requestBody.build())
                .build();
        mHttpEngine.request(request, HttpResponse.UploadFile.class, callback);
    }

    public void getFloorList2(String id, String cid, String pid, HttpEngine.OnResponseCallback<HttpResponse.BuildingNo> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("userID", id)
                .add("companyID", cid)
                .add("missionID", pid)
                .build();
      /*  FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            Object object = params.get(key);
            builder.add(key, String.valueOf(object));
        }*/
        Log.d("获取楼号传值", id + "::" + cid + "::" + pid);
        Request request = new Request.Builder().url(URL.NEW_GET_FLOOR_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.BuildingNo.class, callback);
    }


    public void getFloorList(String id, String cid, String pid, HttpEngine.OnResponseCallback<HttpResponse.BuildingNo> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("project_id", pid)
                .build();
      /*  FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            Object object = params.get(key);
            builder.add(key, String.valueOf(object));
        }*/
        Log.d("获取楼号传值", id + "::" + cid + "::" + pid);
        Request request = new Request.Builder().url(URL.GET_FLOOR_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.BuildingNo.class, callback);
    }

    public void getEmployeeList(String id, String cid, String pid, String bid, HttpEngine.OnResponseCallback<HttpResponse.Staff> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("project_id", pid)
                .add("building_id", bid)
                .build();
        Request request = new Request.Builder().url(URL.GET_EMPLOYEE_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Staff.class, callback);
    }

    public void getProjectList(String id, String cid, String project_title, HttpEngine.OnResponseCallback<HttpResponse.Project> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("project_title", project_title)
                .add("type", "3")
                .build();
        Request request = new Request.Builder().url(URL.GET_PROJECT_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Project.class, callback);
    }

    public void addRectification(String id, String cid, String project, String building_id, String floor_id, String inside_id, String zgMan, String deadline, String images, String voice, String location, String voice_second, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("project", project)
                .add("building_id", building_id)
                .add("floor_id", floor_id)
                .add("inside_id", inside_id)
                .add("zgMan", zgMan)
                .add("deadline", deadline)
                .add("images", images)
                .add("voice", voice)
                .add("location", location)
                .add("voice_second", voice_second)
                .build();
        Request request = new Request.Builder().url(URL.ADD_PROJECT_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void getRectificationList(String id, String cid, String responsibility, String state, String project, String begin, String end, String zgMan, String zpMan, String page, String pageSize, HttpEngine.OnResponseCallback<HttpResponse.Rectification> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("responsibility", responsibility)
                .add("state", state)
                .add("project", project)
                .add("begin", begin)
                .add("end", end)
                .add("zgMan", zgMan)
                .add("zpMan", zpMan)
                .add("page", page)
                .add("pageSize", pageSize)
                .build();
        Request request = new Request.Builder().url(URL.GET_ZGPD_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Rectification.class, callback);
    }

    /*
       筛选加列表
     */
    public void getTaskList(String userID, String companyId, String stopBegin, String stopEnd,
                            String checkUser, String developUser, String userStatus,
                            String missionStatus, String createBegin, String createEnd, String project,
                            String page, String pageSize, String projectid, String miNo,
                            HttpEngine.OnResponseCallback<HttpResponse.Task> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("userID", userID)
                .add("companyID", companyId)
                .add("stopBegin", stopBegin)
                .add("stopEnd", stopEnd)
                .add("checkUser", checkUser)
                .add("developUser", developUser)
                .add("userStatus", userStatus)
                .add("missionStatus", missionStatus)
                .add("createBegin", createBegin)
                .add("createEnd", createEnd)
                .add("project", project)
                .add("page", page)
                .add("pageSize", pageSize)
                .add("mci_p_id", projectid)
                .add("mci_no", miNo)
                .build();
        Log.d("传递参数", missionStatus + "::" + GsonUtils.toJson(body));
        Request request = new Request.Builder().url(URL.XUNJIAN_SHAIXUAN)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Task.class, callback);
    }

    public void getRectificationDetail(String id, String cid, String zid, HttpEngine.OnResponseCallback<HttpResponse.RectificationDetail> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("zid", zid)
                .build();
        Request request = new Request.Builder().url(URL.GET_ZGPD_DETAIL)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.RectificationDetail.class, callback);
    }

    public void login(String session_id, String user_account, String user_passwd, String bus_code, String authority_type, HttpEngine.OnResponseCallback<HttpResponse.Login> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("session_id", session_id)
                .add("user_account", user_account)
                .add("user_passwd", user_passwd)
                .add("bus_code", bus_code)
                .add("authority_type", authority_type)
                .build();
        Request request = new Request.Builder().url(URL.LOGIN_PWD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Login.class, callback);
    }

    public void reply(String id, String zid, String reason, String images, String voice, String voice_second, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .add("reason", reason)
                .add("images", images)
                .add("voice", voice)
                .add("voice_second", voice_second)
                .build();
        Request request = new Request.Builder().url(URL.REPLY_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void reject(String id, String zid, String reason, String images, String voice, String voice_second, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .add("reason", reason)
                .add("images", images)
                .add("voice", voice)
                .add("voice_second", voice_second)
                .build();
        Request request = new Request.Builder().url(URL.REJECT_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void refuse(String id, String zid, String reason, String images, String voice, String voice_second, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .add("reason", reason)
                .add("images", images)
                .add("voice", voice)
                .add("voice_second", voice_second)
                .build();
        Request request = new Request.Builder().url(URL.REFUSE_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void pass(String id, String zid, String reason, String images, String voice, String voice_second, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .add("reason", reason)
                .add("images", images)
                .add("voice", voice)
                .add("voice_second", voice_second)
                .build();
        Request request = new Request.Builder().url(URL.PASS_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void appoint(String id, String zid, String reason, String images, String voice, String voice_second, String man, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .add("reason", reason)
                .add("images", images)
                .add("voice", voice)
                .add("voice_second", voice_second)
                .add("man", man)
                .build();
        Request request = new Request.Builder().url(URL.ZHIPAI_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void withdraw(String id, String zid, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("zid", zid)
                .build();
        Request request = new Request.Builder().url(URL.WITHDRAW_ZGPD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void zg_zp_man(String id, String cid, HttpEngine.OnResponseCallback<HttpResponse.ZGZPMan> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .build();
        Request request = new Request.Builder().url(URL.ZG_ZP_MAN)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.ZGZPMan.class, callback);
    }

    public void lookState(String id, String correct_id, String msg_id, HttpEngine.OnResponseCallback<HttpResponse.Read> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("correct_id", correct_id)
                .add("msg_id", msg_id)
                .build();
        Request request = new Request.Builder().url(URL.LOOK_STATE)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Read.class, callback);
    }

    public void getIndex(String id, HttpEngine.OnResponseCallback<HttpResponse.Message> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .build();
        Request request = new Request.Builder().url(URL.GET_INDEX)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Message.class, callback);
    }

    public void getMessageList(String id, String cid, String page, String pageSize, HttpEngine.OnResponseCallback<HttpResponse.Message> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("cid", cid)
                .add("page", page)
                .add("pageSize", pageSize)
                .build();
        Request request = new Request.Builder().url(URL.GET_MESSAGE_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Message.class, callback);
    }

    public void forgetPwd(String code, String user_mobile, String user_password, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("code", code)
                .add("user_mobile", user_mobile)
                .add("user_passwd", user_password)
                .build();
        Request request = new Request.Builder().url(URL.FORGET_PASSWORD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void updatePwd(String user_id, String old_password, String password, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("user_id", user_id)
                .add("old_password", old_password)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(URL.UPDATE_PASSWORD)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    public void getTeamList(String id, String project_id, String building_id, HttpEngine.OnResponseCallback<HttpResponse.Team> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("id", id)
                .add("project_id", project_id)
                .add("building_id", building_id)
                .build();
        Request request = new Request.Builder().url(URL.GET_TEAM_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Team.class, callback);
    }

    /**
     * 新增第一步
     *
     * @param
     * @param companyID
     */
    public void addOneList(String companyID, String text, String userid, HttpEngine.OnResponseCallback<HttpResponse.ZengOne> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("companyID", companyID)
                .add("text", text)
                .add("userID", userid)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_LIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.ZengOne.class, callback);
    }

    /**
     * 新增第二步
     *
     * @param
     * @param
     */

    public void addTwoList(String projectId, String companyID, HttpEngine.OnResponseCallback<HttpResponse.ZengTwo> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("projectID", projectId)
                .add("companyID", String.valueOf(companyID))
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_TWOLIST)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.ZengTwo.class, callback);
    }

    /**
     * 新增第二步检测项数量
     *
     * @param
     * @param
     */
    public void addNumber(String projectId, HttpEngine.OnResponseCallback<HttpResponse.Strings> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("tid", projectId)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_NUMBER)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Strings.class, callback);
    }


    /**
     * 新增第二步检测项数量
     *
     * @param
     * @param
     */
    public void jcNeiRong(String companyID, String templateID, HttpEngine.OnResponseJsonCallback<HttpResponse.NeiRong> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("companyID", companyID)
                .add("templateID", templateID)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_JCNR)
                .post(body)
                .build();
        mHttpEngine.requestJson(request, callback);
    }

    /**
     * 新增第二步检测项数量
     *
     * @param
     * @param
     */
    public void search(String content, String companyID, String userID, String page, String pageSize, HttpEngine.OnResponseCallback<HttpResponse.Task> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("string", content)
                .add("companyID", companyID)
                .add("userID", userID)
                .add("page", String.valueOf(page))
                .add("pageSize", String.valueOf(pageSize))
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_SEARCH)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Task.class, callback);

    }

    /**
     * 新建项目完成
     *
     * @param
     * @param
     */
    public void createWc(String cvid, String itemID, String itemType, String user_id,
                         String projectId, int times, String isBatch,
                         String checkUser, String datee, String isZhengGai, String zhengTime,
                         HttpEngine.OnResponseCallback<HttpResponse.Complete> callback) {
        Map<String, Object> pram = new HashMap<>();
        pram.put("templateID", cvid);
        pram.put("itemID", itemID);
        pram.put("itemType", itemType);
        pram.put("userID", user_id);
        pram.put("projectID", projectId);
        pram.put("times", String.valueOf(times));
        pram.put("isBatch", isBatch);
        pram.put("checkUser", String.valueOf(checkUser));
        pram.put("endDate", datee);

        pram.put("enableCorrect", isZhengGai);
        pram.put("correctDate", zhengTime);


        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("templateID", cvid)
                .add("itemID", itemID)
                .add("itemType", itemType)
                .add("userID", user_id)
                .add("projectID", projectId)
                .add("times", String.valueOf(times))
                .add("isBatch", isBatch)
                .add("checkUser", String.valueOf(checkUser))
                .add("endDate", datee)
                .add("enableCorrect", isZhengGai)
                .add("correctDate", zhengTime)
                .build();

        Log.d("创建任务传参", GsonUtils.toJson(pram));
        Request request = new Request.Builder().url(URL.XUNJIAN_WANCHENG)
                .post(body)
                .build();

        mHttpEngine.request(request, HttpResponse.Complete.class, callback);
    }

    /**
     * 获取详情
     *
     * @param
     */
    public void getDetail(String taskId, String user_id, HttpEngine.OnResponseCallback<HttpResponse.Detail> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("task_id", taskId)
                .add("user_id", user_id)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_DETAILS)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Detail.class, callback);
    }

    /**
     * 开启任务
     *
     * @param
     */
    public void startTask(String companyID, HttpEngine.OnResponseCallback<HttpResponse.Strings> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("missionID", companyID)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_START)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Strings.class, callback);
    }


    /**
     * 获取检测位置
     *
     * @param
     */
    public void addressData(String companyID, HttpEngine.OnResponseCallback<HttpResponse.Address> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("missionID", companyID)
                .build();
        Request request = new Request.Builder().url(URL.XUNJIAN_ADDRESS)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.Address.class, callback);
    }


    /**
     * 获取版本
     *
     * @param
     */
    public void getAppVersion(HttpEngine.OnResponseCallback<HttpResponse.VersionBean> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("phoneType", "Android")

/**
 *   distribution 生产
 *   uat  uat环境
 *   develop 开发
 *   test 测试
 *
 */
                .add("environment", URL.Environment)
                .add("appType", "qy")
                .build();
        Request request = new Request.Builder().url(URL.App_Version)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.VersionBean.class, callback);
    }

    /**
     * 项目看板分类和状态
     *
     * @param
     */
    public void getKBProjectBoard(String companyID, HttpEngine.OnResponseCallback<HttpResponse.KBProjectBoardBean> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("companyID", companyID)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_ProjectBoard)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBProjectBoardBean.class, callback);
    }


    /**
     * 项目看板搜索页面查询 项目列表
     *
     * @param
     */
    public void getKBProjectSearch(int start, int limit, String companyID, String project_title, String user_id, HttpEngine.OnResponseCallback<HttpResponse.KBSarchBean> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("start", String.valueOf(start))
                .add("limit", String.valueOf(limit))
                .add("companyID", companyID)
                .add("project_title", project_title)
                .add("user_id", user_id)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_ProjectSearch)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBSarchBean.class, callback);
    }

    /**
     * 项目看板搜索页面查询 项目列表
     *
     * @param
     */
    public void getKBKBProvinceData(HttpEngine.OnResponseCallback<HttpResponse.KBProvinceBean> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .build();
        Request request = new Request.Builder().url(URL.KanBan_Province)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBProvinceBean.class, callback);
    }

    //获取开发商 项目经理 和三级城市
    public void getKBKBProjectCondition(String companyID, HttpEngine.OnResponseCallback<HttpResponse.KBProjectConditionBean> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("companyID", companyID)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_ProjectCondition)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBProjectConditionBean.class, callback);
    }


    /**
     * 项目看板项目详情顶部基础信息
     *
     * @param
     */
    public void getKBdetailTop(String project_id, HttpEngine.OnResponseCallback<HttpResponse.KBDetliaTopData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("project_id", project_id)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_DetaolTop)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBDetliaTopData.class, callback);
    }

    /**
     * 项目看板获取项目下面的分布红色项目比例
     * <p>
     * index.php/api/admin/getRedLine
     *
     * @param
     */
    public void getRedLine(String project_id, String startTime, String endTime, HttpEngine.OnResponseCallback<HttpResponse.RedLineData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("project_id", project_id)
                .add("check_begin", endTime)
                .add("check_end", startTime)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_RedLine)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.RedLineData.class, callback);
    }


//    /**
//     * 项目看板获取项目详情概论
//     * <p>
//     * index.php/api/admin/getRedLine
//     *
//     * @param
//     */
//    public void getDetails(String project_id, String start_time, String end_time, HttpEngine.OnResponseCallback<HttpResponse.KanBanDetailData> callback) {
//        FormBody.Builder builder = new FormBody.Builder();
//        RequestBody body = builder
//                .add("project_id", project_id)
//                .add("start_time", start_time)
//                .add("end_time", end_time)
//                .build();
//        Request request = new Request.Builder().url(URL.KanBan_Details)
//                .post(body)
//                .build();
//        mHttpEngine.request(request, HttpResponse.KanBanDetailData.class, callback);
//    }


    /**
     * 项目看板获取项目图片
     * <p>
     * index.php/api/admin/getRedLine
     *
     * @param
     */
    public void getprojectMorePic(String project_id, String page, String page_size, String source, String start_time,
                                  String end_time, String sort, String position, HttpEngine.OnResponseCallback<HttpResponse.MoreImgBeanData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("project_id", project_id)
                .add("page", page)
                .add("page_size", page_size)
                .add("source", source)
                .add("sort", sort)
                .add("start_time", start_time)
                .add("end_time", end_time)
                .add("position", position)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_MoreImg)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.MoreImgBeanData.class, callback);
    }


    /**
     * 项目看板服务商
     * <p>
     * index.php/api/admin/getRedLine
     *
     * @param
     */
    public void getServiceList(String company_id, String company_name, HttpEngine.OnResponseCallback<HttpResponse.KBServicData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("company_id", company_id)
                .add("company_name", company_name)

                .build();
        Request request = new Request.Builder().url(URL.KanBan_ServicData)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KBServicData.class, callback);
    }


    /**
     * 项目看板详情概述
     * <p>
     * index.php/api/admin/getRedLine
     *
     * @param
     */
    public void getDetailsData(String projectId, String begin,
                               String end, String startTime, String endTime,HttpEngine.OnResponseCallback<HttpResponse.KanBanDetailBeanData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("project_id", projectId)
                .add("start_time", end)
                .add("end_time", begin)
                .add("check_begin", endTime)
                .add("check_end", startTime)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_Details)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KanBanDetailBeanData.class, callback);
    }

    /**
     * 项目看板图片位置筛选
     * <p>
     * index.php/api/admin/getRedLine
     *
     * @param
     */
    public void getImageAddress(String projectId, HttpEngine.OnResponseCallback<HttpResponse.KanBanImageAddressData> callback) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody body = builder
                .add("project_id", projectId)
                .build();
        Request request = new Request.Builder().url(URL.KanBan_BuildingByProjectId)
                .post(body)
                .build();
        mHttpEngine.request(request, HttpResponse.KanBanImageAddressData.class, callback);
    }

    /**
     * 移除项目组成员
     *
     * @param
     */
    public void removeGroupPeople(String project_id, String user_id, String remove_time, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();


        builder.add("project_id", project_id);
        builder.add("user_id", user_id);
        builder.add("remove_time", remove_time);

        String s = builder.build().toString();
        Request request = new Request.Builder().url(URL.REMOVE_PEOPLE)
                .post(builder.build())
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }

    /**
     * 移除项目经理
     *
     * @param
     */
    public void removeTransfermanager(String project_id, String user_id, String remove_time, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();


        builder.add("project_id", project_id);
        builder.add("user_id", user_id);
        builder.add("remove_time", remove_time);

        String s = builder.build().toString();
        Request request = new Request.Builder().url(URL.REMOVE_transfermanager)
                .post(builder.build())
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }


    /**
     * 批量新增楼栋
     *
     * @param
     */
    public void addBatchesBuilding(String projectId, String userID, String count, String ffloor, String bfloor,
                                   Map<Integer, String> insideName, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();


        builder.add("project_id", projectId);
        builder.add("user_id", userID);
        builder.add("count", count);
        builder.add("f_floor", ffloor);
        builder.add("b_floor", bfloor);
//        builder.add("inside_name[0]", insideName);
        for (Integer key : insideName.keySet()) {
            builder.add("inside_name[" + key + "]", insideName.get(key));
        }
        String s = builder.build().toString();
        Log.e("AAAA", s);
        Request request = new Request.Builder().url(URL.XUNJIAN_ADDBATCHEBUILD)
                .post(builder.build())
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }


    /**
     * 新增楼栋
     *
     * @param
     */
    public void addBuilding(String projectId, String ffloor, String bfloor, String name, String user_id,
                            Map<Integer, String> insideName, HttpEngine.OnResponseCallback<HttpResponse.StringsArray> callback) {
        FormBody.Builder builder = new FormBody.Builder();


        builder.add("project_id", projectId);
        builder.add("f_floor", ffloor);
        builder.add("b_floor", bfloor);
        builder.add("user_id", user_id);
        builder.add("building_num", name);
        for (Integer key : insideName.keySet()) {
            builder.add("inside_name[" + key + "]", insideName.get(key));
        }
        String s = builder.build().toString();
        Log.e("AAAA", s);
        Request request = new Request.Builder().url(URL.XUNJIAN_ADDBUILD)
                .post(builder.build())
                .build();
        mHttpEngine.request(request, HttpResponse.StringsArray.class, callback);
    }


    public void cancel(String url) {
        mHttpEngine.cancel(url);
    }
}
