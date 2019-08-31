package com.baizhi.serviceImpl;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findAllBanner(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        return bannerMapper.findAllBanner(start, rows);
    }

    @Override
    public void insertBanner(Banner banner) {
        bannerMapper.insertBanner(banner);
    }

    @Override
    public void deleteBanner(String[] id) {
        bannerMapper.deleteBanner(id);
    }

    @Override
    public void updateBanner(Banner banner) {
        bannerMapper.updateBanner(banner);
    }

    @Override
    public Banner findBannerById(String id) {
        Banner banner = bannerMapper.findBannerById(id);
        return banner;
    }

    @Override
    public Integer selectCount() {
        return bannerMapper.selectCount();
    }
}
