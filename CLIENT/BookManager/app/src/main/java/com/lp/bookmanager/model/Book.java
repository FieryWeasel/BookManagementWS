package com.lp.bookmanager.model;

/**
 * Created by iem on 05/12/14.
 */
public class Book {

    private String isbn;
    private String title;
    private int type_id;
    private int author_id;
    private String summary;

    public Book(String isbn, String title, int type_id, int author_id, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.type_id = type_id;
        this.author_id = author_id;
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
