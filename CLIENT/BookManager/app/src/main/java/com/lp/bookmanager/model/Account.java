package com.lp.bookmanager.model;

/**
 * Created by iem on 12/12/14.
 */
public class Account {
    private int id;
    private String crypted_key;
    private int user_id;

    public Account(int id, String crypted_key, int user_id) {
        this.id = id;
        this.crypted_key = crypted_key;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrypted_key() {
        return crypted_key;
    }

    public void setCrypted_key(String crypted_key) {
        this.crypted_key = crypted_key;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
