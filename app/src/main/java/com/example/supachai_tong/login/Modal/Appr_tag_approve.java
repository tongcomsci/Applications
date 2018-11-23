package com.example.supachai_tong.login.Modal;

public class Appr_tag_approve {

    private String ORDER_TYPE, ORDER_ID, DESCRIPTION, ACCEPT;

    public String getORDER_TYPE() {
        return ORDER_TYPE;
    }

    public void setORDER_TYPE(String ORDER_TYPE) {
        this.ORDER_TYPE = ORDER_TYPE;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getACCEPT() {
        return ACCEPT;
    }

    public void setACCEPT(String ACCEPT) {
        this.ACCEPT = ACCEPT;
    }

    @Override
    public String toString() {
        return "ClassPojo [ORDER_TYPE = " + ORDER_TYPE + ", ORDER_ID = " + ORDER_ID + ", DESCRIPTION = " + DESCRIPTION + ", ACCEPT = " + ACCEPT + "]";
    }
}
