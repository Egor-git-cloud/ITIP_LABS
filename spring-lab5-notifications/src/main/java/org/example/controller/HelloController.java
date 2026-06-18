package org.example.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Привет, Spring Boot!";
    }

    @GetMapping("/goodby")
    public String sayGoodby() {
        return "До свидания, Spring Boot!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "Привет, " + name + "!";
    }

    @GetMapping("/info")
    public String info(@RequestParam String name, @RequestParam int age) {
        return "Имя: " + name + ", Возраст: " + age;
    }
}
