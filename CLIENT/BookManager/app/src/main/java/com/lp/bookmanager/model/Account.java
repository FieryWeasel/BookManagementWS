package com.lp.bookmanager.model;

/**
 * Created by iem on 12/12/14.
 */
public class Account {

    public static String getCryptedfkey(String mdp, String login) {
        //TODO get the crypted key
        return mdp + "0" + login;
    }

}
