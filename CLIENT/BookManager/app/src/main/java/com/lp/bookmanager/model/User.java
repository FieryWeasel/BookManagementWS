package com.lp.bookmanager.model;

/**
 * Created by iem on 12/12/14.
 */
public class User {
    private int user_id;
    private String first_name;
    private String last_name;
    private String nickname;
    private String birth_date;

    public User() {
    }

    public User(int user_id, String first_name, String last_name, String nickname, String birth_date, String mail) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.birth_date = birth_date;
        this.mail = mail;
    }

    private String mail;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
