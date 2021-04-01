package com.intelligence.edge.service;

/**

 * @description: 无人机通信
 * @author: uu
 * @create: 08-04
 **/
public interface UavNetService {


    int connect(String deviceID);

    int closeConnect(String deviceID);
}
