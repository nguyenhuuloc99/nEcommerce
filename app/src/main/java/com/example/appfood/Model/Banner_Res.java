package com.example.appfood.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banner_Res {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    List<Banner>result;

    public Banner_Res(Boolean success, String message, List<Banner> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public Banner_Res() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Banner> getResult() {
        return result;
    }

    public void setResult(List<Banner> result) {
        this.result = result;
    }
}
