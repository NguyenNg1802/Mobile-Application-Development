package com.example.nguyen.lab1;
import com.google.gson.annotations.SerializedName;
/**
 * Created by PC on 2/25/2019.
 */

public class HeaderModel {
    @SerializedName("header")
    private String header;

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
