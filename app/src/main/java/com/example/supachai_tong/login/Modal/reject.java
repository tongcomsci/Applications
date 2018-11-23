package com.example.supachai_tong.login.Modal;

public class reject {
    private String appr_name;
    private Appr_tag_reject appr_tag;

    public Appr_tag_reject getAppr_tag() {
        return appr_tag;
    }

    public void setAppr_tag(Appr_tag_reject appr_tag) {
        this.appr_tag = appr_tag;
    }

    public String getAppr_name ()
    {
        return appr_name;
    }

    public void setAppr_name (String appr_name)
    {
        this.appr_name = appr_name;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [appr_name = "+appr_name+", appr_tag = "+appr_tag+"]";
    }
}
