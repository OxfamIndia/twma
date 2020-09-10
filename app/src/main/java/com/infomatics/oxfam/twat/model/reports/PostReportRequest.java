package com.infomatics.oxfam.twat.model.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostReportRequest {

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("checkpointId")
    @Expose
    private int checkpointId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("userId")
    @Expose
    private long userId;

    @SerializedName("reportedBy")
    @Expose
    private String reportedBy;

    @SerializedName("severity")
    @Expose
    private String severity;

    @SerializedName("status")
    @Expose
    private int status;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
