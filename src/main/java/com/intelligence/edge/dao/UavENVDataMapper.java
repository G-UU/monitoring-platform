package com.intelligence.edge.dao;

import com.intelligence.edge.pojo.EnvironmentInfo;
import com.intelligence.edge.pojo.UavEnvironmentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UavENVDataMapper {

    int insertUavENVData(UavEnvironmentInfo uavEnvironmentInfo);


}
