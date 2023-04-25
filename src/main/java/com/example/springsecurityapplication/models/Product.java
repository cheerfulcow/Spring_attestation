package com.example.springsecurityapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    //Каждая модель должна иметь первичный ключ, помечаем поле @Id
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Если поле name не указывать, то столбец будет назван как само поле (title).
    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;

    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Цена товара не может быть отрицательной или нулевой")
    private float price;

    @Column(name = "warehouse",nullable = false)
    @NotEmpty(message = "Наименование склада не может быть пустым")
    private String warehouse;

    @Column (name = "seller", nullable = false)
    @NotEmpty(message = "Информация о продавце не может быть пустой")
    private String seller;

    //Поле, в котором лежит объект Категории
    //optional=false: значение не является обязательным
    @ManyToOne(optional = false)
    private Category category;

    //Поле для хранения даты и времени создания товара при помощи встроенного класса LocalDateTime
    private LocalDateTime dateTime;

    //Устанавливаем связь с классом Image,
    //Тут будем хранить все картинки для данного объекта
    //Связываем с полем product класса Image
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();

    //Для работы с корзиной
    //@JoinTable указывает, что для реализации связи М-М создаётся промежуточная таблица product_cart (это класс Cart)
    //@JoinColumn указывает, какие колонки будут в промежуточной таблице. Первой указывается колонка, имеющая отношение к текущему классу("product_id")
    //В самой таблице Product при этом новых колонок не добавляется
    @ManyToMany()
    @JoinTable(name="product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Order> orderList;

    public Product(String title, String description, float price, String warehouse, String seller, Category category, LocalDateTime dateTime, List<Image> imageList) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.warehouse = warehouse;
        this.seller = seller;
        this.category = category;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    public Product() {
    }

  //Данный метод позволяет добавить фотографию в лист текущего продукта
    public void addImageToProduct(Image image){
  //Указываем, для какого конкретно товара предназначена привязываемая фотография
  // т.е. качестве продукта, к которому будет привязываться фотография будет текущий продукт
        image.setProduct(this);
        imageList.add(image); //добавляем фото в лист
    }

    public void replaceImageToProduct(int index, Image image,List<Image> productImageList){
        //Указываем, для какого конкретно товара предназначена привязываемая фотография
        // т.е. качестве продукта, к которому будет привязываться фотография будет текущий продукт
        image.setProduct(this);
        productImageList.add(index, image); //добавляем фото в лист
        index++;
        productImageList.remove(index);
    }

    //Данный метод будет заполнять поле даты и времени при создании объекта класса
    //Сработает автоматически при создании объекта
    @PrePersist
    private void init(){
        dateTime=LocalDateTime.now();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

}
