package com.intelligence.edge.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 设备运行数据
 * @author: uu
 * @create: 12-22
 **/
@Data
public class DeviceTravelData {
    @NotNull
    private String deviceID;
    private int distance;
    private String runTime;
    private int daysUsed;

    public DeviceTravelData(@NotNull String deviceID, int distance, String runtime, int daysUsed){
        this.deviceID = deviceID;
        this.distance = distance;
        this.runTime = runtime;
        this.daysUsed = daysUsed;
    }
}
