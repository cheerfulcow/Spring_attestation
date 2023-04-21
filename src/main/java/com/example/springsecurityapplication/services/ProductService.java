package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//в сервисе прописываем все основные методы по работе с продуктом
@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Данный метод позволяет получить список всех товаров
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    //Данный метод позволяет получить продукт по ID или вернуть null, если не будет найден
    public Product getProductById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    //Даннный метод позволяет сохранить товар
    //Transactional т.к. в этом методе мы добавляем, а класс помечен как рид-онли по умолчанию
    @Transactional
    public void saveProduct (Product product, Category category) {
    //присваиваем продукту категорию, т.к. она лежит в отдельной БД и мы связаны с ней М-1
        product.setCategory(category);
        productRepository.save(product);
    }

    //Метод позволяет обновить данные о товаре
    //Т.к. мы передаём ID, spring Data JPA сам понимает, что мы хотим обновить информацию о продукте
    @Transactional
    public void updateProduct (int id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }

    //Метод для удаления товара по id
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
