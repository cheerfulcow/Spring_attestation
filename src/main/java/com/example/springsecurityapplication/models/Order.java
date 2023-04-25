package com.example.springsecurityapplication.models;

import com.example.springsecurityapplication.enumm.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="orders") //order - зарезервированное posgreSQL слово, применять нельзя - даст ошибку
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String onumber;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Person person;

    //количество
    private int count;
    private float price;
    private LocalDateTime dateTime;
    private Status status;

    //автоматическая запись времени создания объекта(заказа)
    @PrePersist
    private void init(){
        dateTime= LocalDateTime.now();
    }

    //daTime и id не внедряем - они внедряются автоматически
    public Order(String onumber, Product product, Person person, int count, float price, Status status) {
        this.onumber = onumber;
        this.product = product;
        this.person = person;
        this.count = count;
        this.price = price;
        this.status = status;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOnumber() {
        return onumber;
    }

    public void setOnumber(String number) {
        this.onumber = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
