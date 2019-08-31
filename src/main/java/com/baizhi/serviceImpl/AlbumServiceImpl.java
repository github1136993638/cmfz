package com.baizhi.serviceImpl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<Object, Object> findAllAlbum(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        Map<Object, Object> map = new HashMap<>();
        /*F12查看发送的请求信息
         * 请求携带的参数
         * _search: false
         *nd: 1565915559098
         *rows: 2
         *page: 2
         *sidx:
         *sord: asc
         * */
        //total总页数 page第几页 records总记录数 rows分页之后的数据
        Integer integer = albumMapper.selectCount();
        Integer total = integer % rows == 0 ? integer / rows : integer / rows + 1;
        List<Album> byPage = albumMapper.findAllAlbum(start, rows);
        map.put("rows", byPage);
        map.put("records", integer);
        map.put("total", total);
        map.put("page", page);
        /*（控件接收的）分页的数据格式：
         * {
         *   "page":1
         *   "total":20
         *   "records":200
         *   "rows":[
         *   {
         *       "id":1
         *       "name":xiaohuahua
         *   },
         *   {
         *       "id":2
         *       "name":xiaohheihei
         *   }
         * ]
         * }
         * */

        return map;
    }

    @Override
    public void insertAlbum(Album album) {
        albumMapper.insertAlbum(album);
    }

    @Override
    public void deleteAlbum(String[] id) {
        albumMapper.deleteAlbum(id);
    }

    @Override
    public void updateAlbum(Album album) {
        albumMapper.updateAlbum(album);
    }

    @Override
    public Album findAlbumById(String id) {
        return albumMapper.findAlbumById(id);
    }

}
