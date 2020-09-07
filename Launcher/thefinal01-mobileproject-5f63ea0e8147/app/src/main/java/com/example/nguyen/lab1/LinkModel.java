package com.example.nguyen.lab1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LinkModel {
    @SerializedName("low_res")
    private String low_res;

    @SerializedName("mid_res")
    private String mid_res;

    @SerializedName("high_res")
    private String high_res;

    @SerializedName("parameters")
    private List<HeaderModel> headers;

    public String getLow_res() {
        return low_res;
    }

    public void setLow_res(String low_res) {
        this.low_res = low_res;
    }


    public List<HeaderModel> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderModel> headers) {
        this.headers = headers;
    }
}
