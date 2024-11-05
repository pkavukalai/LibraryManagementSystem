package com.library.managementsystem.entities.user;

import com.library.managementsystem.entities.book.Book;

import java.util.ArrayList;
import java.util.List;

public class User {

    private UserInfo userInfo;
    private UserRole role;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(Builder builder)
    {
        this.role = builder.role;
        this.userInfo = builder.userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public UserRole getRole() {
        return role;
    }

    public static class Builder {

        private UserInfo userInfo;
        private UserRole role;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

        public Builder setUserRole(UserRole role)
        {
            this.role = role;
            return this;
        }
        public Builder setUserInfo(UserInfo userInfo)
        {
            this.userInfo = userInfo;
            return this;
        }

        public User build()
        {
            return new User(this);
        }
    }
}
