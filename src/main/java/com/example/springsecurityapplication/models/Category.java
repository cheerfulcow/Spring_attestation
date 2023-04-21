package com.example.springsecurityapplication.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    //Создаем поле,в котором будем хранить список продуктов, принадлежащих определенной категории
    // Устанавливаем связь 1-M с классом Product. Указываем с каким полем("category") будем устанавлена связь
    //fetch = FetchType.EAGER = при загрузке владеемого объекта необходимо сразу загрузить коллекцию владельцев
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
