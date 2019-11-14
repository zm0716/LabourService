package com.agilefinger.labourservice.data;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.CatalogueBean;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.ConstructionProcessBean;
import com.agilefinger.labourservice.bean.DataCollectionBean;
import com.agilefinger.labourservice.bean.DataCollectionFilterBean;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.EndBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.InspectionItemBean;
import com.agilefinger.labourservice.bean.InspectionPositionBean;
import com.agilefinger.labourservice.bean.ItemBean;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.UnitBean;
import com.agilefinger.labourservice.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadData {
    private static String[] urls = {
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2720761512,1992761174&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=401967138,750679164&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1059486618,1562064036&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2720761512,1992761174&fm=26&gp=0.jpg"};

    public static List<MessageBean> getNoticeList() {
        List<MessageBean> mList = new ArrayList<>();
       /* for (int i = 1; i < 10; i++) {
            MessageBean message = new MessageBean();
            if (i % 2 == 1) {
                message.setShowType(1);
            } else {
                message.setShowType(2);
            }
            mList.add(message);
        }*/
        return mList;
    }

    public static List<RectificationBean> getRectificationList() {
        List<RectificationBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            RectificationBean message = new RectificationBean();
            if (i % 2 == 1) {
                message.setImages(getImagesList(4));
            } else {
                message.setImages(getImagesList(3));
            }
            mList.add(message);
        }
        return mList;
    }


    public static List<String> getImagesList(int size) {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mList.add(urls[i]);
        }
        return mList;
    }


    public static List<ProjectBean> getProjectList() {
        List<ProjectBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            ProjectBean project = new ProjectBean();
            mList.add(project);
        }
        return mList;
    }

    public static List<BuildingNoBean> getBuildingNoList() {
        List<BuildingNoBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            BuildingNoBean buildingNo = new BuildingNoBean();
            buildingNo.setName(i + "号楼");
            mList.add(buildingNo);
        }
        return mList;
    }

    public static List<FloorBean> getFloorList() {
        List<FloorBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            FloorBean floor = new FloorBean();
            floor.setName("F" + i);
            mList.add(floor);
        }
        return mList;
    }

    public static List<DirectionBean> getDirectionList() {
        List<DirectionBean> mList = new ArrayList<>();
        DirectionBean direction1 = new DirectionBean();
        direction1.setName("东立面");
        mList.add(direction1);
        DirectionBean direction2 = new DirectionBean();
        direction2.setName("西立面");
        mList.add(direction2);
        DirectionBean direction3 = new DirectionBean();
        direction3.setName("南立面");
        mList.add(direction3);
        DirectionBean direction4 = new DirectionBean();
        direction4.setName("北立面");
        mList.add(direction4);
        return mList;
    }

    public static List<StaffBean> getStaffList() {
        List<StaffBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            StaffBean staff = new StaffBean();
            if (i == 9) {
                staff.setAdd(true);
            }
            mList.add(staff);
        }
        return mList;
    }

    public static List<CompanyBean> getCompanyList() {
        List<CompanyBean> mList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            CompanyBean company = new CompanyBean();
            company.setCompany_name(i + "公司");
            mList.add(company);
        }
        return mList;
    }

    public static List<UnitBean> getUnitList() {
        List<UnitBean> mList = new ArrayList<>();
        UnitBean unit1 = new UnitBean("m²");
        UnitBean unit2 = new UnitBean("m");
        UnitBean unit3 = new UnitBean("kg");
        UnitBean unit4 = new UnitBean("件");
        UnitBean unit5 = new UnitBean("个");
        mList.add(unit1);
        mList.add(unit2);
        mList.add(unit3);
        mList.add(unit4);
        mList.add(unit5);
        return mList;
    }

    public static List<ConstructionProcessBean> getConstructProgressList() {
        List<ConstructionProcessBean> mList = new ArrayList<>();
        ConstructionProcessBean process1 = new ConstructionProcessBean("涂料");
        ConstructionProcessBean process2 = new ConstructionProcessBean("保温");
        mList.add(process1);
        mList.add(process2);
        return mList;
    }

    public static List<InspectionPositionBean> getInspectionPositionList() {
        List<InspectionPositionBean> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InspectionPositionBean inspectionPositionBean = new InspectionPositionBean();
            mList.add(inspectionPositionBean);
        }
        return mList;
    }

    public static List<InspectionItemBean> getInspectionItemList() {
        List<InspectionItemBean> mList = new ArrayList<>();
        InspectionItemBean inspectionItemBean1 = new InspectionItemBean(1, true);
        mList.add(inspectionItemBean1);
        InspectionItemBean inspectionItemBean2 = new InspectionItemBean(2, false);
        mList.add(inspectionItemBean2);
        InspectionItemBean inspectionItemBean3 = new InspectionItemBean(3, false);
        mList.add(inspectionItemBean3);
        InspectionItemBean inspectionItemBean4 = new InspectionItemBean(3, false);
        mList.add(inspectionItemBean4);
        InspectionItemBean inspectionItemBean5 = new InspectionItemBean(3, false);
        mList.add(inspectionItemBean5);
        InspectionItemBean inspectionItemBean6 = new InspectionItemBean(4, true);
        mList.add(inspectionItemBean6);
        return mList;
    }

    public static List<EndBean.DataBean.ItemsBean> getStringList(List<EndBean.DataBean.ItemsBean> items) {
        List<EndBean.DataBean.ItemsBean> mList = new ArrayList<>();
        return mList;
    }
    public static List<DataCollectionBean> getDataCollectionList() {
        List<DataCollectionBean> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new DataCollectionBean());
        }
        return mList;
    }
    public static List<DataCollectionTabBean> getDataCollectionTabList(List<ItemBean> itemsList) {
        //List<String> list = CommonUtils.getResourceArray(LSApplication.context, R.array.data_collection_tab);

        List<DataCollectionTabBean> mList = new ArrayList<>();
        mList.clear();
        for (int i = 0; i < itemsList.size(); i++) {

            //张孟

//            DataCollectionTabBean dataCollectionTab = new DataCollectionTabBean(itemsList.get(i).getMci_id(), itemsList.get(i).getMci_name(),itemsList.get(i).getMct_is_batch(),itemsList.get(i).getP_count(),itemsList i == 0 ? true : false);

            DataCollectionTabBean dataCollectionTab = new DataCollectionTabBean(itemsList.get(i).getMci_id(), itemsList.get(i).getMci_name(),itemsList.get(i).getMct_is_batch(), i == 0 ? true : false);
            mList.add(dataCollectionTab);
        }
        return mList;
    }

    public static List<DataCollectionFilterBean> getDataCollectionFilterList() {
        List<String> list = CommonUtils.getResourceArray(LSApplication.context, R.array.data_collection_filter);
        List<DataCollectionFilterBean> mList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DataCollectionFilterBean dataCollectionFilter = new DataCollectionFilterBean(i + 1, list.get(i), i == 0 ? true : false);
            mList.add(dataCollectionFilter);
        }
        return mList;
    }

    public static List<CatalogueBean> getCatalogueList() {
        List<CatalogueBean> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CatalogueBean catalogueBean = new CatalogueBean(""+i,"目录"+i);
            mList.add(catalogueBean);
        }
        return mList;
    }
}
