package com.lp.bookmanager.model;

import java.io.Serializable;

/**
 * Created by iem on 12/12/14.
 */
public class Review implements Serializable{
    private int id;
    private String book_id;
    private int user_id;
    private int mark;
    private String title;
    private String comment;

    public Review(int id, String book_id, int user_id, int mark, String title, String comment) {
        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.mark = mark;
        this.title = title;
        this.comment = comment;
    }

    public Review(String book_id, int user_id, int mark, String title, String comment) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.mark = mark;
        this.title = title;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
