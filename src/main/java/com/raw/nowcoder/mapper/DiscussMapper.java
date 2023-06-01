package com.raw.nowcoder.mapper;


import com.raw.nowcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussMapper {

    List<DiscussPost> selectAll();

    List<DiscussPost> selectPage();
}
