package com.library.managementsystem.entities.user;

import com.library.managementsystem.entities.book.BookInfo;

import javax.jws.soap.SOAPBinding;
import java.util.Objects;

public class UserInfo implements Comparable<UserInfo>{

    private final String name;
    private final String password;

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(name, userInfo.name) && Objects.equals(password, userInfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    @Override
    public int compareTo(UserInfo obj) {
        if (this.name.compareTo(obj.name) != 0) {
            return this.name.compareTo(obj.name);
        }
        else {
            return this.password.compareTo(obj.password);
        }
    }

}
