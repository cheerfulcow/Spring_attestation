package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Role;
import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.*;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepostitory;
import com.example.springsecurityapplication.services.ImageService;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
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
import java.util.Optional;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;
    private final ImageService imageService;
    private final OrderService orderService;
    private final OrderRepostitory orderRepostitory;
    private final PersonService personService;

    //Поле для хранения фотографий. В Value указываем путь для хранения,
    //тут мы ссылаемся на upload.path, который ранее объявлен в application.properties
    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, ImageService imageService, OrderService orderService, OrderRepostitory orderRepostitory, PersonService personService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.imageService = imageService;
        this.orderService = orderService;
        this.orderRepostitory = orderRepostitory;
        this.personService = personService;
        this.categoryRepository = categoryRepository;
    }

    //Главная страница админа
    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }

    //Отображение раздела "товары" у админа
    @GetMapping("/admin/product")
    public String admin(Model model) {
        //по переходу по ссылке /admin/product передаем также все продукты, которые есть в репозитории
        model.addAttribute("products", productService.getAllProduct());
        return "admin/infoProduct";
    }

    //По ссылке admin/product/add возвращаем представление product/addProduct, создаем новый Product,
    //получаем все категории товаров из репозитория категорий - их будет использовать select в представлении
    @GetMapping("admin/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    //Принимаем @ModelAttribute объект модели "product", под хранение модели создаём экземпляр подели Product,
    // проводим валидацию @Valid и в BindingResult кладём все ошибки валидации, далее поскольку наши инпуты формы,
    // куда мы загружаем фотографии не привязаны к форме, получаем от них данные при помощи @RequestParam,
    // также принимаем выбранную в селекте категорию.
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                             @RequestParam("file_one") MultipartFile file_one,
                             @RequestParam("file_two") MultipartFile file_two,
                             @RequestParam("file_three") MultipartFile file_three,
                             @RequestParam("file_four") MultipartFile file_four,
                             @RequestParam("file_five") MultipartFile file_five,
                             @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = categoryRepository.findById(category).orElseThrow();
        if (bindingResult.hasErrors()) {
            //Если есть ошибки валидации, то сначала возвращаем список категорий(иначе они на шаблоне не выведутся), а потом представление
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }
        //Если ошибок нет, то Обрабатываем загружаемые файлы с файлИнпутов формы.
        //Если файл не пустой
        if (file_one != null) {
            //Превращаем ссылку на файл в объект файла
            File uploadDir = new File(uploadPath);
            //Если директория не найдена, то создаём её
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //Генерируем UUID для файла и создаем новое уникальное имя файла UUID + исходное название файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_one.getOriginalFilename();
            //Отправляем файл file_one с новым сгенерированным наименованием в папку с загрузками (ссылка на которую
            // хранится в uploadPath и указана в app. prop.). Не забываем выбросить Exception.
            file_one.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (file_two != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (file_three != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (file_four != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (file_five != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFile));
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
                              @RequestParam("file_one") MultipartFile file_one,
                              @RequestParam("file_two") MultipartFile file_two,
                              @RequestParam("file_three") MultipartFile file_three,
                              @RequestParam("file_four") MultipartFile file_four,
                              @RequestParam("file_five") MultipartFile file_five,
                              Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }

        //Если ошибок нет, то Обрабатываем загружаемые файлы с файлИнпутов формы.
        //Удаляем старые фотографии редактируемого товара из БД
        imageService.deleteImageByProductId(product.getId());
        if (!file_one.isEmpty()) {
            //Превращаем ссылку на файл в объект файла
            File uploadDir = new File(uploadPath);
            //Если директория не найдена, то создаём её
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //Генерируем UUID для файла и создаем новое уникальное имя файла UUID + исходное название файла
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_one.getOriginalFilename();
            //Отправляем файл file_one с новым сгенерированным наименованием в папку с загрузками (ссылка на которую
            // хранится в uploadPath и указана в app. prop.). Не забываем выбросить Exception.
            file_one.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объект модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (!file_two.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (!file_three.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (!file_four.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        if (!file_five.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFile = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFile));
            Image image = new Image(); //создаем объёкт модели Image (фото)
            image.setProduct(product); //к фотографии привязываем продукт
            image.setFileName(resultFile); //присваиваем наименование файлу
            product.addImageToProduct(image); //добавляем фото в лист с фото, привязанный к этому продукту
        }
        productService.updateProduct(id, product);
        return "redirect:/product/info/{id}";
    }

    //Отображение страницы со всеми заказами
    @GetMapping("/admin/orders")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("status", Status.values());
        return "/admin/orders";
    }

    //Подробная информация о заказе и изменение его статуса
    @GetMapping("/admin/order/{id}")
    public String orderInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("status", Status.values());
        return "/admin/orderInfo";
    }

    //Изменение статуса заказа на странице с заказами
    @PostMapping("/admin/order/{id}")
    public String ChangeOrderStatus(@ModelAttribute("status") Status status,
                                    @PathVariable("id") int id)
    {
        Order order = orderService.getOrderById(id); //получаем объект заказа из БД
        order.setStatus(status); //меняем статус на выбранный в селекте
        orderService.updateOrder(id, order); //обновляем данные заказа в БД
        return "redirect:/admin/order/{id}";
    }

    //Поиск заказов по последним символам
    @PostMapping("/admin/order/search")
    public String orderSearch(@RequestParam("search") String search, Model model){
        //кладем в модель все заказы
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("search_order", orderRepostitory.findOrderByPartOfNumber(search));
    //Кладём в модель обратно полученные значения с формы для того, чтобы после отправки формы (произойдёт перезагрузка
    // страницы) отправить в инпут ранее введённое значение
        model.addAttribute("value_search", search);
        return "/admin/orders";
    }

    //Отображение страницы со всеми пользователями
    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", personService.findAllUsers());
        model.addAttribute("role", Role.values());
        return "/admin/users";
    }

    //ПОдробная информация о выбранном пользователе
    @GetMapping("/admin/user/{id}")
    public String userInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", personService.findById(id));
        model.addAttribute("role", Role.values());
        String a=Role.ROLE_USER.name();
        System.out.println(personService.findById(id).getRole().equals(Role.ROLE_USER.name()));
        return "/admin/userInfo";
    }

    @PostMapping("/admin/user/{id}")
    public String setUserRole (@ModelAttribute("role") String role, @PathVariable("id") int id){
        Person person = personService.findById(id);

        person.setRole(role);
        personService.updatePerson(id, person);
        return "redirect:/admin/user/{id}";
    }
}