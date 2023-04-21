package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class productController {
    private final ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }

    //Метод сработает при гет-запросе на /product.
    //Позволит получить все товары и вывести представление product из папки product
    @GetMapping("")
    public String getAllProduct(Model model){
        //Кладем в модель аттрибут, чтобы можно к нему можно было обратиться на шаблоне. В
        // качестве ключа "products", в качестве значения, то что вернёт метод getAllProduct)
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }

 //Метод для получения информации о конкретном продукте (при переходе по ссылке с карточки товара со страницы /product)
 //Считываем динамический id и по нему передаем в модель из репозитория продукт с этим id.
 //И возвращаем представление product/infoProduct
    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/infoProduct";
    }
}
