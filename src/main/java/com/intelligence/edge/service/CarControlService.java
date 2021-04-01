package com.intelligence.edge.service;

import com.intelligence.edge.pojo.Position;

/**

 **/
public interface CarControlService {

    void control(String carID, String instruction);

//    void closeConnection(String carID);

    void reset(String carID);

    int destination(String deviceID, Position position);
}
