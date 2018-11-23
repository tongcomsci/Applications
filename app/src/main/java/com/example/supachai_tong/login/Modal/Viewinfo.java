package com.example.supachai_tong.login.Modal;

public class Viewinfo {
    private String appr_name, appr_date, flag_id, appr_nextcode, appr_status;

    private Appr_tag[] appr_tag;


    public String getAppr_status() {
        return appr_status;
    }

    public void setAppr_status(String appr_status) {
        this.appr_status = appr_status;
    }

    public String getAppr_name() {
        return appr_name;
    }

    public void setAppr_name(String appr_name) {
        this.appr_name = appr_name;
    }

    public String getAppr_date() {
        return appr_date;
    }

    public void setAppr_date(String appr_date) {
        this.appr_date = appr_date;
    }

    public String getFlag_id() {
        return flag_id;
    }

    public void setFlag_id(String flag_id) {
        this.flag_id = flag_id;
    }

    public Appr_tag[] getAppr_tag() {
        return appr_tag;
    }

    public void setAppr_tag(Appr_tag[] appr_tag) {
        this.appr_tag = appr_tag;
    }

    public String getAppr_nextcode() {
        return appr_nextcode;
    }

    public void setAppr_nextcode(String appr_nextcode) {
        this.appr_nextcode = appr_nextcode;
    }

    @Override
    public String toString() {
        return "ClassPojo [appr_name = " + appr_name + ", appr_date = " + appr_date + ", flag_id = " + flag_id + ", appr_tag = " + appr_tag + ", appr_nextcode = " + appr_nextcode + "]";
    }
}