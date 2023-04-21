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

}
