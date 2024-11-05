package com.library.managementsystem.entities.book;

public class Book {
    private Integer id;
    private BookInfo bookInfo;
    private BookStatus status;

    public Book(Book.Builder builder)
    {
        this.bookInfo = builder.bookInfo;
        this.status = builder.status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public static class Builder {

        private BookInfo bookInfo;
        private BookStatus status = BookStatus.AVAILABLE;;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

        public Builder setBookInfo(BookInfo bookInfo)
        {
            this.bookInfo = bookInfo;
            return this;
        }

        public Builder setStatus(BookStatus status)
        {
            this.status = status;
            return this;
        }

        public Book build()
        {
            return new Book(this);
        }
    }

}
