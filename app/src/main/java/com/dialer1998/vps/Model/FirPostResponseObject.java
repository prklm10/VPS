package com.dialer1998.vps.Model;



import com.google.gson.annotations.SerializedName;

public class FirPostResponseObject {
    @SerializedName("status")
    String status;
    @SerializedName("id")
    Integer Id;

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return Id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
