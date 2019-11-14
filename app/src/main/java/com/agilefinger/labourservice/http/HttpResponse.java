package com.agilefinger.labourservice.http;

import com.agilefinger.labourservice.bean.AddJcnRongBean;
import com.agilefinger.labourservice.bean.AddOneBean;
import com.agilefinger.labourservice.bean.AddTwoBean;
import com.agilefinger.labourservice.bean.AddressBean;
import com.agilefinger.labourservice.bean.AddressImagBean;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.CompleteBean;
import com.agilefinger.labourservice.bean.DetailBean;
import com.agilefinger.labourservice.bean.DetliaTopBean;
import com.agilefinger.labourservice.bean.KBServiceBean;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.bean.KanBanProjectBoardBean;
import com.agilefinger.labourservice.bean.KanBanSarchBean;
import com.agilefinger.labourservice.bean.LoginBean;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.ProjectConditionBean;
import com.agilefinger.labourservice.bean.ProvinceBean;
import com.agilefinger.labourservice.bean.ReadModel;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RedLineBean;
import com.agilefinger.labourservice.bean.SearchBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TaskBean;
import com.agilefinger.labourservice.bean.TeamBean;
import com.agilefinger.labourservice.bean.UploadFileBean;
import com.agilefinger.labourservice.bean.VersionCodeBean;
import com.agilefinger.labourservice.bean.WorkModel;
import com.agilefinger.labourservice.bean.ZGZPManBean;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    public int code;
    public String message;

    public static class Strings extends HttpResponse {
        public String data;

        public String getData() {
            return data;
        }
    }

    public static class StringsArray extends HttpResponse {
        public List<String> data;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    public static class Team extends HttpResponse {
        public List<TeamBean> data;

        public List<TeamBean> getData() {
            return data;
        }

    }

    public static class UploadFile extends HttpResponse {
        public UploadFileBean data;

        public UploadFileBean getData() {
            return data;
        }
    }

    public static class Project extends HttpResponse {
        public List<ProjectBean> data;

        public List<ProjectBean> getData() {
            return data;
        }

        public void setData(List<ProjectBean> data) {
            this.data = data;
        }
    }

    public static class BuildingNo extends HttpResponse {
        public List<BuildingNoBean> data;

        public List<BuildingNoBean> getData() {
            return data;
        }

    }

    public static class Staff extends HttpResponse {
        public List<StaffBean> data;

        public List<StaffBean> getData() {
            return data;
        }
    }

    public static class ZGZPMan extends HttpResponse {
        public ZGZPManBean data;

        public ZGZPManBean getData() {
            return data;
        }

        public void setData(ZGZPManBean data) {
            this.data = data;
        }
    }

    public static class Rectification extends HttpResponse {
        public List<RectificationBean> data;

        public List<RectificationBean> getData() {
            return data;
        }
    }

    public static class RectificationDetail extends HttpResponse {
        public RectificationBean data;

        public RectificationBean getData() {
            return data;
        }
    }

    public static class Login extends HttpResponse {
        public LoginBean data;

        public LoginBean getData() {
            return data;
        }
    }

    public static class Read extends HttpResponse {
        public ReadModel data;

        public ReadModel getData() {
            return data;
        }

    }

    public static class Company extends HttpResponse {
        public List<CompanyBean> data;

        public List<CompanyBean> getData() {
            return data;
        }
    }

    public static class Message extends HttpResponse {
        public List<MessageBean> data;

        public List<MessageBean> getData() {
            return data;
        }
    }

    public static class Work extends HttpResponse {
        public WorkModel data;

        public WorkModel getData() {
            return data;
        }
    }

    //巡检新增第一步
    public static class ZengOne extends HttpResponse {
        public List<AddOneBean> data;

        public List<AddOneBean> getData() {
            return data;
        }
    }

    //巡检新增第二步
    public static class ZengTwo extends HttpResponse {
        public AddTwoBean data;

        public AddTwoBean getData() {
            return data;
        }
    }

    //检测内容
    public static class NeiRong extends HttpResponse {
        public AddJcnRongBean data;

        public AddJcnRongBean getData() {
            return data;
        }
    }

    //搜索
    public static class Search extends HttpResponse {
        public List<SearchBean> data;

        public List<SearchBean> getData() {
            return data;
        }
    }

    //新建任务完成
    public static class Complete extends HttpResponse {
        public CompleteBean data;

        public CompleteBean getData() {
            return data;
        }
    }

    //筛选
    public static class Task extends HttpResponse {
        public List<TaskBean> data;

        public List<TaskBean> getData() {
            return data;
        }
    }

    //详情
    public static class Detail extends HttpResponse {
        public DetailBean data;

        public DetailBean getData() {
            return data;
        }
    }

    //获取检测任务接口
    public static class Address extends HttpResponse {
        public AddressBean data;

        public AddressBean getData() {
            return data;
        }
    }

    //获取版本号
    public static class VersionBean extends HttpResponse {
        public VersionCodeBean data;

        public VersionCodeBean getData() {
            return data;
        }
    }

    //看板列表
    public static class KBProjectBoardBean extends HttpResponse {
        public KanBanProjectBoardBean data;

        public KanBanProjectBoardBean getData() {
            return data;
        }
    }

    //看板搜索列表
    public static class KBSarchBean extends HttpResponse {
        public List<KanBanSarchBean> data;

        public List<KanBanSarchBean> getData() {
            return data;
        }
    }


    //看板筛选地址选择
    public static class KBProvinceBean extends HttpResponse {
        public List<ProvinceBean> data;

        public List<ProvinceBean> getData() {
            return data;
        }
    }

    //获取开发商 项目经理 和三级城市
    public static class KBProjectConditionBean extends HttpResponse {
        public ProjectConditionBean data;

        public ProjectConditionBean getData() {
            return data;
        }
    }


    //看板项目详情顶部基础信息
    public static class KBDetliaTopData extends HttpResponse {
        public DetliaTopBean data;

        public DetliaTopBean getData() {
            return data;
        }
    }

    //看板项目详情顶部基础信息
    public static class RedLineData extends HttpResponse {
        public ArrayList<RedLineBean> data;

        public ArrayList<RedLineBean> getData() {
            return data;
        }
    }

    //看板项目获取项目详情概论
    public static class KanBanDetailData extends HttpResponse {
        public KanBanDetailBean data;

        public KanBanDetailBean getData() {
            return data;
        }
    }

    //看板项目获取项目详情概论
    public static class MoreImgBeanData extends HttpResponse {
        public MoreImgBean data;

        public MoreImgBean getData() {
            return data;
        }
    }

    //看板项目获取服务商列表
    public static class KBServicData extends HttpResponse {
        public ArrayList<KBServiceBean> data;

        public ArrayList<KBServiceBean> getData() {
            return data;
        }
    }


    //看板项目 详情概述
    public static class KanBanDetailBeanData extends HttpResponse {
        public KanBanDetailBean data;

        public KanBanDetailBean getData() {
            return data;
        }
    }


    //看板项目 详情概述
    public static class KanBanImageAddressData extends HttpResponse {
        public ArrayList<AddressImagBean> data;

        public ArrayList<AddressImagBean> getData() {
            return data;
        }
    }
}
