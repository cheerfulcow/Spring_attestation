package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    //Поле для хранения фотографий. В Value указываем путь для хранения,
    //тут мы ссылаемся на upload.path, который ранее объявлен в application.properties
    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    @GetMapping("/admin")
    public String admin(Model model){
     //по переходу по ссылке /admin передаем также все продукты, которые есть в репозитории
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    //По ссылке admin/product/add возвращаем представление product/addProduct, создаем новый Product,
    //получаем все категории товаров из репозитория категорий - их будет использовать select в представлении
    @GetMapping("admin/product/add")
    public String addProduct (Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    //Принимаем @ModelAttribute объект модели "product", под хранение модели создаём экземпляр подели Product,
    // проводим валидацию @Valid и в BindingResult кладём все ошибки валидации, далее поскольку наши импуты формы,
    // куда мы загружаем фотографии не привязаны к форме, получаем от них данные при помощи @RequestParam,
    // также принимаем выбранную в селекте категорию.
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                             @RequestParam("file_one")MultipartFile file_one,
                             @RequestParam("file_two")MultipartFile file_two,
                             @RequestParam("file_three")MultipartFile file_three,
                             @RequestParam("file_four")MultipartFile file_four,
                             @RequestParam("file_five")MultipartFile file_five,
                             @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getTitle());
        if(bindingResult.hasErrors()) {
    //Если есть ошибки валидации, то сначала возвращаем список категорий(иначе они на шаблоне не выведутся), а потом представление
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }
        //Если ошибок нет, то Обрабатываем загружаемые файлы с файлИнпутов формы.
        //Если файл не пустой
        if(file_one!=null){
            //Превращаем ссылку на файл в объект файла
            File uploadDir = new File(uploadPath);
            //Если директория не найдена, то создаём её
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
    //Генерируем UUID для файла и создаем новое уникальное имя файла UUID + исходное название файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_one.getOriginalFilename();
    //Отправляем файл file_one с новым сгенерированным наименованием в папку с загрузками (ссылка на которую
    // хранится в uploadPath и указана в app. prop.). Не забываем выбросить Exception.
            file_one.transferTo(new File(uploadPath + "/" +resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if(file_two!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" +resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if(file_three!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" +resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if(file_four!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" +resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if(file_five!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" +resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }

    //Метод для удаления товара по кнопке "удалить.." из представления admin.html
    // Получаем динамический ID и по нему удаляем товар из БД
    @GetMapping("/admin/product/delete/{id}")
        public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    //Метод для вызова шаблона редактирования товара
    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        //кладем в модель продукт по id и категории товаров. Благодаря этому заполним все поля
        // значениями данного объекта
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product")
                              @Valid Product product, BindingResult bindingResult,
                              @PathVariable("id") int id,
                              Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";

    }



    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

}
