package com.baizhi.mapper;

import com.baizhi.entity.Counter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterMapper {
    public List<Counter> findAllCounter(@Param("start") Integer start, @Param("rows") Integer rows);
}
