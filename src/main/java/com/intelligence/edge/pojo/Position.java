package com.intelligence.edge.pojo;

import lombok.Data;

/**

 **/
@Data
public class Position {
    private Double longitude;
    private Double latitude;

    public Position(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }



}
