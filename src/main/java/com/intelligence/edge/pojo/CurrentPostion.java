package com.intelligence.edge.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 设备当前位置
 * @author: uu
 * @create: 12-20
 **/
@Data
public class CurrentPostion {
    @NotNull
    private String deviceID;
    private Double longitude;
    private Double latitude;

    public CurrentPostion(@NotNull String deviceID, double longitude, double latitude){
        this.deviceID = deviceID;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
