package com.example.supachai_tong.login.Modal;

public class notifications
{
    private model_notification[] notification;

    public model_notification[] getNotification ()
    {
        return notification;
    }

    public void setNotification (model_notification[] notification)
    {
        this.notification = notification;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [notification = "+notification+"]";
    }

}

