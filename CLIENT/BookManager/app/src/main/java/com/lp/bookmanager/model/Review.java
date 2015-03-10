package com.lp.bookmanager.model;

/**
 * Created by iem on 12/12/14.
 */
public class Review {
    private int id;
    private String book_id;
    private int user_id;
    private int mark;
    private String comments;

    public Review(int id, String book_id, int user_id, int mark, String comments) {
        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.mark = mark;
        this.comments = comments;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
