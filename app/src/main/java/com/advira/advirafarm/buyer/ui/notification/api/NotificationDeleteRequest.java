package com.advira.advirafarm.buyer.ui.notification.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationDeleteRequest {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

}