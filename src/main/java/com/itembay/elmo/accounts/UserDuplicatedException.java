package com.itembay.elmo.accounts;

public class UserDuplicatedException extends RuntimeException {

    String userName;

    public UserDuplicatedException(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
