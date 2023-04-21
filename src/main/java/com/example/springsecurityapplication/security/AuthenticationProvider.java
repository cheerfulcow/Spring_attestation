//package com.example.springsecurityapplication.security;
//
//import com.example.springsecurityapplication.services.PersonDetailsService;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//// В данном классе вынесена вся логика по аутентификации пользователя, который взаимодействует с PersonDetailsService
//@Component
//public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
//    private final PersonDetailsService personDetailsService;
//
//    public AuthenticationProvider(PersonDetailsService personDetailsService) {
//        this.personDetailsService = personDetailsService;
//    }
//
//    //Метод, в котором описывается вся логика аутентификации(для SecurityConfig).
//    //Данные, которые придут с формы аутентификации будут помещены в объект Authentication
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
////Получаем логин с формы аутентификации. За нас SS сам создаст объект формы и передаст его в
//// объект аутентификации authentication
//        String login = authentication.getName();
//// т.к. данный метод возвращает объект интерфейса UserDetails, то и объект мы создадим через интерфейс
//        UserDetails person = personDetailsService.loadUserByUsername(login);
////Извлекаем пароль из объекта аутентификации
//        String password = authentication.getCredentials().toString();
//        if (!password.equals(person.getPassword())){
//            throw new BadCredentialsException("Некорректный пароль");
//        }
////Возвращенный объект будет помещён в сессию. При каждом запросе пользователя на сервер, уже
//// не придется заного аутентифицироваться в аккаунт. Сервер сам возьмёт объект аутентификации из
//// сессии и передаст его SS.
////Collection для хранения ролей. Пока их нет, поэтому пустой лист возвращаем
//        return new UsernamePasswordAuthenticationToken(person, password, Collections.emptyList());
//
//    }
//
//    //Указывает, для каких конкретно случаев применяется данный AuthenticationProvider
//    //Пока что указываем как true, т.е. будет при всех случаях (проверках данных) всегда используется
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
