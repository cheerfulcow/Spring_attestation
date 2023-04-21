package com.example.springsecurityapplication.models;

import jakarta.persistence.*;

// Модель для хранения картинок (условие: доступно для хранения от 1 до 5 картинок)
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;

    //Делаем связь с классом Product (поле imageList)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    public Image(int id, String fileName, Product product) {
        this.id = id;
        this.fileName = fileName;
        this.product = product;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
