package com.raw.nowcoder.Util;

import com.raw.nowcoder.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    private ThreadLocal<User> users=new ThreadLocal<User>();

    public void setUsers(User user) {
        users.set(user);
    }

    public User getUsers() {
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
