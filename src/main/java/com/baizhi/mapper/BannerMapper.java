package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    public List<Banner> findAllBanner(@Param("start") Integer start, @Param("rows") Integer rows);

    public void insertBanner(Banner banner);

    public void deleteBanner(String[] id);

    public void updateBanner(Banner banner);

    public Banner findBannerById(String id);

    public Integer selectCount();
}
