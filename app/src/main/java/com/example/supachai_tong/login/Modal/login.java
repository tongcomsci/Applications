package com.example.supachai_tong.login.Modal;

public class login {
    private boolean status;
    private String user, pass, s_name, uname, name, dev_v1, position,img;
    private String[] list_dev, list_noti_type, list_s_notifi_type, list_appr_nextcode;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDev_v1() {
        return dev_v1;
    }

    public void setDev_v1(String dev_v1) {
        this.dev_v1 = dev_v1;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String[] getList_appr_nextcode() {
        return list_appr_nextcode;
    }

    public void setList_appr_nextcode(String[] list_appr_nextcode) {
        this.list_appr_nextcode = list_appr_nextcode;
    }
    //    private Integer num;
//
//    public Integer getNum() {
//        return num;
//    }
//
//    public void setNum(Integer num) {
//        this.num = num;
//    }

    public String[] getList_s_notifi_type() {
        return list_s_notifi_type;
    }

    public void setList_s_notifi_type(String[] list_s_notifi_type) {
        this.list_s_notifi_type = list_s_notifi_type;
    }

    public String[] getList_noti_type() {
        return list_noti_type;
    }

    public void setList_noti_type(String[] list_noti_type) {
        this.list_noti_type = list_noti_type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String[] getList_dev() {
        return list_dev;
    }

    public void setList_dev(String[] list_dev) {
        this.list_dev = list_dev;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
