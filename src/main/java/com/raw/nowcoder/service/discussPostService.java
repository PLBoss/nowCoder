package com.raw.nowcoder.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.raw.nowcoder.Util.PageUtil;
import com.raw.nowcoder.entity.DiscussPost;
import com.raw.nowcoder.entity.PageRequest;
import com.raw.nowcoder.entity.PageResult;
import com.raw.nowcoder.mapper.DiscussMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class discussPostService {

    @Autowired
    private DiscussMapper discussMapper;

    public List<DiscussPost> selectAll(){

        return  discussMapper.selectAll();


    }

   public PageResult selectPage(PageRequest request){

        return PageUtil.getPageResult(request,getPageinfo(request));

   }

   private PageInfo<DiscussPost> getPageinfo(PageRequest pageRequest){
       int pageNum = pageRequest.getPageNum();
       int pageSize = pageRequest.getPageSize();
       PageHelper.startPage(pageNum,pageSize);
       List<DiscussPost> discussPosts = discussMapper.selectPage();
       return new PageInfo<DiscussPost>(discussPosts);



   }
}
