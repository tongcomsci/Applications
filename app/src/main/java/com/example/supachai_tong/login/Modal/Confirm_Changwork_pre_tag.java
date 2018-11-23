package com.example.supachai_tong.login.Modal;


public class Confirm_Changwork_pre_tag {
    private Appr_tag_pre appr_tag;
    private String appr_name;

    public String getAppr_name() {
        return appr_name;
    }

    public void setAppr_name(String appr_name) {
        this.appr_name = appr_name;
    }

    public Appr_tag_pre getAppr_tag ()
    {
        return appr_tag;
    }

    public void setAppr_tag (Appr_tag_pre appr_tag)
    {
        this.appr_tag = appr_tag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [appr_tag = "+appr_tag+"]";
    }

}
