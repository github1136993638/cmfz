package com.baizhi.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import net.minidev.json.JSONArray;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<Object, Object> findAllUser(Integer page, Integer rows) {
        Map<Object, Object> map = new HashMap<>();
        //total总页数 page第几页 records总记录数 rows分页之后的数据
        Integer integer = userMapper.selectCount();
        int total = integer % rows == 0 ? integer / rows : integer / rows + 1;
        Integer start = (page - 1) * rows;//page从1开始，start从0开始
        List<User> byPage = userMapper.findAllUser(start, rows);
        map.put("rows", byPage);
        map.put("records", integer);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(String phone, String password) {
        User user = userMapper.login(phone, password);
        //业务功能
        return user;
    }

    @Override
    public void regist(User user) {
        userMapper.regist(user);
        /*
         * 用sql语句实现：
         * select create_date,count(id) from table where datediff(NOW()-当前日期,create_date)<7  datediff两个日期相减
         * select * from table where datediff(dd,datetime类型字段,getdate())<7
         * 今天的所有数据：select * from 表名 where DateDiff(dd,datetime类型字段,getdate())=0
         *昨天的所有数据：select * from 表名 where DateDiff(dd,datetime类型字段,getdate())=1
         *7天内的所有数据：select * from 表名 where DateDiff(dd,datetime类型字段,getdate())<=7
         *30天内的所有数据：select * from 表名 where DateDiff(dd,datetime类型字段,getdate())<=30
         *本月的所有数据：select * from 表名 where DateDiff(mm,datetime类型字段,getdate())=0
         *本年的所有数据：select * from 表名 where DateDiff(yy,datetime类型字段,getdate())=0
         *
         * 今天 select * from 表名 where to_days(时间字段名) = to_days(now());
         *昨天 SELECT * FROM 表名 WHERE TO_DAYS( NOW( ) ) - TO_DAYS( 时间字段名) <= 1
         *7天 SELECT * FROM 表名 where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(时间字段名)
         *近30天 SELECT * FROM 表名 where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(时间字段名)
         *本月 SELECT * FROM 表名 WHERE DATE_FORMAT( 时间字段名, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )
         *上一月 SELECT * FROM 表名 WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( 时间字段名, '%Y%m' ) ) =1
         * */
        List<EchartsMap> allByDate = findAllByDate();
        //list转成json
        JSONArray jsonArray = new JSONArray();//new JSONObject()  .put()
        jsonArray.addAll(allByDate);
        String jsonString = jsonArray.toJSONString();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-efb8df40bb964af08c3d8c2af0e15861");
        goEasy.publish("my_channel", jsonString);
        /*ArrayList<String> pastDaysList = new ArrayList<>();
        List<String> datelist = new ArrayList<>();
        List<Integer> countlist = new ArrayList<>();
        for (int i = 0; i <7; i++) {
            pastDaysList.add(getPastDate(i));
        }
        for (String s : pastDaysList) {
            //将String类型的日期转换为util类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datelist.add(s);
            countlist.add(userMapper.findAllByDate(date).size());
        }
        //list转成json
        JSONObject jsonObject = new JSONObject();//new JSONObject()  .put()
        jsonObject.put("date",datelist);
        jsonObject.put("count",countlist);
        String jsonString = jsonObject.toJSONString();

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-efb8df40bb964af08c3d8c2af0e15861");
        goEasy.publish("my_channel", jsonString);*/

        //地区
        /*
         * 或者用sql语句实现：
         * select count(province) as '重复次数/人数',province
         * from table
         * group by province
         * */
        Set<EchartsMap> allByProvince = findAllByProvince();
        //list转成json
        JSONArray jsonArrayLocal = new JSONArray();
        jsonArrayLocal.addAll(allByProvince);
        String jsonStringLocal = jsonArrayLocal.toJSONString();
        GoEasy goEasy1 = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-efb8df40bb964af08c3d8c2af0e15861");
        goEasy1.publish("local_channel", jsonStringLocal);
    }

    @Override
    public List<EchartsMap> findAllByDate() {
        ArrayList<String> pastDaysList = new ArrayList<>();
        List<EchartsMap> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            pastDaysList.add(getPastDate(i));
        }
        for (String s : pastDaysList) {
            //将String类型的日期转换为util类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(new EchartsMap(s, userMapper.findAllByDate(date).size()));
        }
        return list;
    }

    /**
     * 获取过去第几天的日期
     */
    public String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<EchartsMap> findAllByProvince() {
        Set<EchartsMap> set = new HashSet<>();
        for (int i = 0; i < userMapper.findAll().size(); i++) {
            int count = 0;
            for (int j = 0; j < userMapper.findAll().size(); j++) {
                if (userMapper.findAll().get(i).getProvince().equals(userMapper.findAll().get(j).getProvince())) {
                    count++;
                }
            }
            set.add(new EchartsMap(userMapper.findAll().get(i).getProvince(), count));
        }
        return set;
    }

    @Override
    public void exportUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> allUser = userMapper.findAll();
        for (User user : allUser) {
            //获取图片上传的路径
            //String realPath = request.getSession(true).getServletContext().getRealPath("/upload/img");
            String realPath = "http://localhost:8686/cmfz/upload/img/";
            String picture = realPath + "/" + user.getHead_picture();
            user.setHead_picture(picture);
            System.out.println(user);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("持明法洲", "用户信息表"), User.class, allUser);
        ServletOutputStream outputStream = null;
        try {
            String fileName = "用户信息表.xls";
            /*//根据文件路径获取指定文件
            File file = new File("C:\\Users\\Administrator\\Desktop\\第三阶段\\"+fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);*/
            //获取响应输出流---往浏览器写（浏览器下载）---不需要从输入流中读取，输入流是从字节数组读取拷贝
            outputStream = response.getOutputStream();
            //处理在线打开问题  文件名字不存在问题   处理中文文件名不显示问题
            response.setHeader("content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8").replace("+", "%20"));
            workbook.write(outputStream);
            /*FileUtils.copyFile(file,outputStream);//文件拷贝，不需要输入流，直接将指定的文件拷贝到输出流中
            workbook.close();
            outputStream.close();
            fileOutputStream.close();//会占用文件，导致文件无法删除
            if (file.exists() && file.isFile()) {
                if (file.delete()) {//delete()可以删除文件或者空文件夹,若文件夹不为空，则先删除掉该文件夹下的所有文件才能删除该文件夹
                    System.out.println("删除单个文件" + fileName + "成功！");
                } else {
                    System.out.println("删除单个文件: " + fileName + "失败！");
                }
            } else {
                System.out.println("删除单个文件失败：" + fileName + "不存在！");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
