package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by 86251 on 2019/5/29.
 */
@DatabaseTable(tableName = "Rectification")
public class RectificationBean {
    @DatabaseField(useGetSet = true, columnName = "id")
    private String id;
    @DatabaseField(useGetSet = true, columnName = "pid")
    private String pid;
    private String pic;
    @DatabaseField(useGetSet = true, columnName = "zgMan")
    private String zgMan;
    private String person;
    @DatabaseField(useGetSet = true, columnName = "deadline")
    private String deadline;
    @DatabaseField(useGetSet = true, columnName = "deadlineFormat")
    private String deadlineFormat;
    @DatabaseField(useGetSet = true, columnName = "time")
    private String time;
    @DatabaseField(useGetSet = true, columnName = "time2")
    private String time2;
    @DatabaseField(useGetSet = true, columnName = "title")
    private String title;
    @DatabaseField(useGetSet = true, columnName = "state")
    private String state;
    private String subtitle;
    private String status;
    private String location;
    private String creator;
    private String project_id;
    private String building_id;
    @DatabaseField(useGetSet = true, columnName = "buildingId")
    private String buildingId;

    @DatabaseField(useGetSet = true, columnName = "directionId")
    private String directionId;
    @DatabaseField(useGetSet = true, columnName = "floorId")
    private String floorId;
    @DatabaseField(useGetSet = true, columnName = "buildingContent")
    private String buildingContent;
    @DatabaseField(useGetSet = true, columnName = "buildingDes")
    private String buildingDes = "";
    @DatabaseField(useGetSet = true, columnName = "zgManId")
    private String zgManId;
    @DatabaseField(useGetSet = true, columnName = "image")
    private String image;

    //补充--begin
    private String company_id;
    private String floor_id;//楼层id
    private String inside_id;//立面id
    private String zgMan_id;//整改人id
    private String address_descrip;//详情地址
    private String deadline_time;//截止日期，不拼接字串
    private List<String> images_active_url;
    private List<String> voice_active_url;
    private String creater_id;
    private String creater;
    private String building_name;
    private String floor_name;

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public String getInside_name() {
        return inside_name;
    }

    public void setInside_name(String inside_name) {
        this.inside_name = inside_name;
    }

    private String inside_name;
    //补充--end

    private List<RectificationItemBean> list;
    private AuthBean auth;
    private List<String> images;
    private List<ImagesBean> imagesList;
    private List<String> voice;
    private List<String> voice_second;
    private List<VoiceBean> voiceList;


    public List<VoiceBean> getVoiceList() {
        return voiceList;
    }

    public void setVoiceList(List<VoiceBean> voiceList) {
        this.voiceList = voiceList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getZgManId() {
        return zgManId;
    }

    public void setZgManId(String zgManId) {
        this.zgManId = zgManId;
    }


    public String getDeadlineFormat() {
        return deadlineFormat;
    }

    public void setDeadlineFormat(String deadlineFormat) {
        this.deadlineFormat = deadlineFormat;
    }

    public String getBuildingDes() {
        return buildingDes;
    }

    public void setBuildingDes(String buildingDes) {
        this.buildingDes = buildingDes;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getBuildingContent() {
        return buildingContent;
    }

    public void setBuildingContent(String buildingContent) {
        this.buildingContent = buildingContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getZgMan() {
        return zgMan;
    }

    public void setZgMan(String zgMan) {
        this.zgMan = zgMan;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<ImagesBean> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImagesBean> imagesList) {
        this.imagesList = imagesList;
    }

    public List<String> getVoice() {
        return voice;
    }

    public void setVoice(List<String> voice) {
        this.voice = voice;
    }

    public List<String> getVoice_second() {
        return voice_second;
    }

    public void setVoice_second(List<String> voice_second) {
        this.voice_second = voice_second;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<RectificationItemBean> getList() {
        return list;
    }

    public void setList(List<RectificationItemBean> list) {
        this.list = list;
    }

    public AuthBean getAuth() {
        return auth;
    }

    public void setAuth(AuthBean auth) {
        this.auth = auth;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getInside_id() {
        return inside_id;
    }

    public void setInside_id(String inside_id) {
        this.inside_id = inside_id;
    }

    public String getZgMan_id() {
        return zgMan_id;
    }

    public void setZgMan_id(String zgMan_id) {
        this.zgMan_id = zgMan_id;
    }

    public String getAddress_descrip() {
        return address_descrip;
    }

    public void setAddress_descrip(String address_descrip) {
        this.address_descrip = address_descrip;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public List<String> getImages_active_url() {
        return images_active_url;
    }

    public void setImages_active_url(List<String> images_active_url) {
        this.images_active_url = images_active_url;
    }

    public List<String> getVoice_active_url() {
        return voice_active_url;
    }

    public void setVoice_active_url(List<String> voice_active_url) {
        this.voice_active_url = voice_active_url;
    }

    public String getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(String creater_id) {
        this.creater_id = creater_id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
