package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    public List<Banner> findAllBanner(Integer start, Integer rows);

    public void insertBanner(Banner banner);

    public void deleteBanner(String[] id);

    public void updateBanner(Banner banner);

    public Banner findBannerById(String id);

    public Integer selectCount();
}
