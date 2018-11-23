package com.example.supachai_tong.login.Modal;

public class notifi {
    private String[] list_noti_assign,list_name_assign,list_uname_assign;
    private Detail_notifi detail_notifi;

    public String[] getList_noti_assign ()
    {
        return list_noti_assign;
    }

    public void setList_noti_assign (String[] list_noti_assign)
    {
        this.list_noti_assign = list_noti_assign;
    }

    public Detail_notifi getDetail_notifi ()
    {
        return detail_notifi;
    }

    public void setDetail_notifi (Detail_notifi detail_notifi)
    {
        this.detail_notifi = detail_notifi;
    }

    public String[] getList_name_assign ()
    {
        return list_name_assign;
    }

    public void setList_name_assign (String[] list_name_assign)
    {
        this.list_name_assign = list_name_assign;
    }

    public String[] getList_uname_assign ()
    {
        return list_uname_assign;
    }

    public void setList_uname_assign (String[] list_uname_assign)
    {
        this.list_uname_assign = list_uname_assign;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [list_noti_assign = "+list_noti_assign+", detail_notifi = "+detail_notifi+", list_name_assign = "+list_name_assign+", list_uname_assign = "+list_uname_assign+"]";
    }
}
