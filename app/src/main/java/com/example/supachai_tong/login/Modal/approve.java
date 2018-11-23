package com.example.supachai_tong.login.Modal;

public class approve {
    private Detail_notifi_app[] detail_notifi_app;
    private Detail_desc[] detail_desc;
    private Detail_work_cntr[] detail_work_cntr;

    public Detail_notifi_app[] getDetail_notifi_app() {
        return detail_notifi_app;
    }

    public void setDetail_notifi_app(Detail_notifi_app[] detail_notifi_app) {
        this.detail_notifi_app = detail_notifi_app;
    }

    public Detail_desc[] getDetail_desc() {
        return detail_desc;
    }

    public void setDetail_desc(Detail_desc[] detail_desc) {
        this.detail_desc = detail_desc;
    }

    public Detail_work_cntr[] getDetail_work_cntr() {
        return detail_work_cntr;
    }

    public void setDetail_work_cntr(Detail_work_cntr[] detail_work_cntr) {
        this.detail_work_cntr = detail_work_cntr;
    }

    @Override
    public String toString() {
        return "ClassPojo [detail_notifi_app = " + detail_notifi_app + ", detail_desc = " + detail_desc + ", detail_work_cntr = " + detail_work_cntr + "]";
    }
}
