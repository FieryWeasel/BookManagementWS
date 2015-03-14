package com.lp.bookmanager.tools;

import com.lp.bookmanager.model.Author;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.model.Review;
import com.lp.bookmanager.model.User;

import java.util.List;

/**
 * Created by Kazuya on 12/03/2015.
 * BookManager
 */
public class DataManager {

    private static DataManager instance;

    private List<Book> mListBook;
    private List<Author> mListAuthor;
    private List<User> mListUsers;
    private List<Review> mListReviews;

    public static DataManager getInstance(){
        if(instance == null)
            instance = new DataManager();
        return instance;
    }

    public String getAuthorFromId(int id){
        for(Author author: mListAuthor){
            if(author.getId() == id)
                return author.getFirst_name() + " " + author.getLast_name();
        }
        return "Author";
    }

    public String getAuthorUserFromId(int user_id) {
        for(User user: mListUsers){
            if(user.getId() == user_id)
                return user.getNickname();
        }
        return "Unknown";
    }

    public List<Book> getmListBook() {
        return mListBook;
    }

    public void setmListBook(List<Book> mListBook) {
        this.mListBook = mListBook;
    }

    public List<Author> getmListAuthor() {
        return mListAuthor;
    }

    public void setmListAuthor(List<Author> mListAuthor) {
        this.mListAuthor = mListAuthor;
    }

    public void setmListUsers(List<User> mListUsers) {
        this.mListUsers = mListUsers;
    }

    public List<User> getmListUsers() {
        return mListUsers;
    }

    public List<Review> getmListReviews() {
        return mListReviews;
    }

    public void setmListReviews(List<Review> mListReviews) {
        this.mListReviews = mListReviews;
    }
}
