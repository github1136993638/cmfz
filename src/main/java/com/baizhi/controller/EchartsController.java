package com.baizhi.controller;

import com.baizhi.entity.EchartsMap;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    UserService userService;

    @RequestMapping("/findEcharts")
    public List<Integer> findEcharts() {
        List<Integer> list = new ArrayList<>();
        list.add(new Random().nextInt(1000));
        list.add(new Random().nextInt(1000));
        list.add(new Random().nextInt(1000));
        list.add(new Random().nextInt(1000));
        list.add(new Random().nextInt(1000));
        list.add(new Random().nextInt(1000));
        return list;
    }

    @RequestMapping("/findEchartsMap")
    public List<EchartsMap> findEchartsMap() {
        /*List<EchartsMap> list = new ArrayList<>();
        EchartsMap echartsMap1 = new EchartsMap("河南", userService.findAllByProvince("河南").size());
        EchartsMap echartsMap2 = new EchartsMap("陕西", userService.findAllByProvince("陕西").size());
        EchartsMap echartsMap3 = new EchartsMap("湖南", userService.findAllByProvince("湖南").size());
        EchartsMap echartsMap4 = new EchartsMap("江苏", userService.findAllByProvince("江苏").size());
        list.add(echartsMap1);
        list.add(echartsMap2);
        list.add(echartsMap3);
        list.add(echartsMap4);*/
        return null;
    }
}
