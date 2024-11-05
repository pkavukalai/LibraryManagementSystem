package com.library.managementsystem.entities.book;

import java.util.Objects;

public class BookInfo implements Comparable<BookInfo>{
    private final String name;
    private final String author;

    public BookInfo(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInfo bookInfo = (BookInfo) o;
        return Objects.equals(name, bookInfo.name) && Objects.equals(author, bookInfo.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

    @Override
    public int compareTo(BookInfo obj) {
        if (this.name.compareTo(obj.name) != 0) {
            return this.name.compareTo(obj.name);
        }
        else {
            return this.author.compareTo(obj.author);
        }
    }
}
