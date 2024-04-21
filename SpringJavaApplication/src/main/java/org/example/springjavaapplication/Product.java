package org.example.springjavaapplication;

public class Product {
    private String title;
    private String link;
    private String price;

    public Product(String title, String link, String price) {
        this.title = title;
        this.link = link;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPrice() {
        return price;
    }
}

