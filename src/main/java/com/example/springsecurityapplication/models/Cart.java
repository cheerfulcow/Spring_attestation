package com.example.springsecurityapplication.models;

import jakarta.persistence.*;

@Entity
//Подключаемся к существующей промежуточной таблице для реализации связи М-М
//При ее отсутствии - будет создана
@Table(name="product_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //id пользователя, которому принадлежит данная корзина
    @Column(name="person_id")
    private int personId;

    @Column(name="product_id")
    private int productId;

    public Cart(int personId, int productId) {
        this.personId = personId;
        this.productId = productId;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
