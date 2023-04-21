package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.repositories.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    //подключаем встроенный класс для хэширования паролей
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    @Transactional
    public void register(Person person) {
   //подключаем хэширование пароля при регистрации - получаем пароль из объекта person(с формы)
   // и с помощью метода encode хэшируем его пароль и сохраняем его в пользователе
        person.setPassword(passwordEncoder.encode(person.getPassword()));
    //добавляем пользователя в репозиторий
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}
