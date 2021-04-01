package com.intelligence.edge.service;

/**

 * @Description 无人车服务接口
 **/
public interface CarNetService {
//    void receiveData(String carID);
//
//    void closeConnection(String carID);
//
//    void receiveVideo(String carID);
//
//    void closeVideo(String carID);

    boolean ping(String ip);

    int connect(String carID);

    int closeConnect(String carID);

}
