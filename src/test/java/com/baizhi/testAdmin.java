package com.baizhi;

import com.baizhi.entity.*;
import com.baizhi.mapper.*;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class testAdmin {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserService userService;

    @Test
    public void testAdmin() {
        Admin admin = adminMapper.login("admin");
        System.out.println(admin);
    }

    @Autowired
    UserMapper userMapper;

    @Test
    public void testUser() {
        List<User> allUser = userMapper.findAllUser(1, 5);
        for (User user : allUser) {
            System.out.println(user);
        }
    }

    @Autowired
    BannerMapper bannerMapper;

    @Test
    public void testBanner() {
        List<Banner> allBanner = bannerMapper.findAllBanner(1, 5);
        for (Banner banner : allBanner) {
            System.out.println(banner);
        }
    }

    @Autowired
    AlbumMapper albumMapper;

    @Test
    public void testAlbum() {
        List<Album> allAlbum = albumMapper.findAllAlbum(1, 5);
        for (Album album : allAlbum) {
            System.out.println(album);
        }
    }

    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void testArticle() {
        List<Article> allArticle = articleMapper.findAllArticle(1, 5);
        for (Article article : allArticle) {
            System.out.println(article);
        }
    }

    @Test
    public void test1() {

        //根据文件路径获取指定文件
        File file = new File("C:\\Users\\Administrator\\Desktop\\第三阶段\\用户信息表.xls");
        file.delete();
    }

    @Test
    public void testGoEasy() {
        /*int i=0;
        while (i<7){
            User user  = new User();
            userService.regist(user);
            i++;
        }*/
        User user = new User("7", "17839227007", "123456",
                "1565926721649_kangna.jpg", "sml", "sml",
                "女", "湖南", "常德", "嗯嗯嗯嗯",
                "展示", new Date(), "于正");
        userService.regist(user);
    }

}
