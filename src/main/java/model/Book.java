package model;

import java.time.LocalDate;

//POJO - Plain Old Java Object

public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private Double price;
    private Integer stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString(){
        return "Book: Id:" + id + " Title:" + title + " Author:" + author + " PublishedDate:" + publishedDate + " Price:" + price + " Stock:" + stock;
    }
}
