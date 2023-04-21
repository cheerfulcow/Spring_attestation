package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final PersonValidator personValidator;
    private final PersonService personService;

    public MainController(PersonValidator personValidator, PersonService personService) {
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("/index")
    public String index(){
        // Получаем объект аутентификации. Далее, с помощью SpringContextHolder обращаемся к контексту ина нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    //получаем роль аутентифицированного пользователя от полученного объекта сессии
        String role = personDetails.getPerson().getRole();
    // Если у аутентиф. пользователя роль Админ, то отправляем его по ссылке :/admin", иначе отправляем на index
        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        return "index";
    }

    //Первый способ работы с моделью
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("person", new Person());
//        return "registration";
//    }

    //Второй способ работы с моделью. При отправке формы спринг сам положит объект в модель. Если в модели такого объекта не будет, спринг в эту модель объект положит.
    // При гет-запросе на /registration спринг смотрит, приходит какой-либо объект в качестве запросу по данному адресу, если нет такого аттрибута, то спринг автоматически внедрит аттрибут Person
    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        //Дополнительная валидация на наличие регистрируемого логина в БД (в дополнение к валидации, указанной в модели Person). Если пользователь уже существует, валидатор внедрит ошибку в bindingResult
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/index";
    }
}
