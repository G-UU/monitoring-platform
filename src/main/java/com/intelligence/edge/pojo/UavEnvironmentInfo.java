package com.intelligence.edge.pojo;

import lombok.Data;
@Data
public class UavEnvironmentInfo {
    private String time;
    private String deviceID;
    private double longitude;
    private double latitude;
    private double alt;
    private double groundspeed;
    private double yaw;
    private double yawspeed;
    private double temperature;
    private double wind;
    private double humidity;

    public UavEnvironmentInfo(String time,String deviceID,double longitude,double latitude,double alt,double groundspeed,double yaw,double yawspeed,double temperature,double wind,double humidity){
        this.time=time;
        this.deviceID=deviceID;
        this.longitude=longitude;
        this.latitude=latitude;
        this.alt=alt;
        this.groundspeed=groundspeed;
        this.yaw=yaw;
        this.yawspeed=yawspeed;
        this.temperature=temperature;
        this.wind=wind;
        this.humidity=humidity;
    }

}
