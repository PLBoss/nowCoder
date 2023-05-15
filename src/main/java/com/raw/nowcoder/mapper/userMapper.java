package com.raw.nowcoder.mapper;

import com.raw.nowcoder.entity.LoginTicket;
import com.raw.nowcoder.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userMapper {

    public List<User> selectAll();


    User selectByName(String username);

    void saveTickt(@Param("loginTicket") LoginTicket loginTicket);
}
