package com.lp.bookmanager.data_container;

import com.lp.bookmanager.model.User;

/**
 * Created by iem on 03/03/15.
 */
public class UserManager {

    private static UserManager instance;

    private User user;

    public static UserManager getInstance(){
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
}
