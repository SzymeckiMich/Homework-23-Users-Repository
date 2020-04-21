package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UsersRepository usersRepository;

    //    @Autowired
    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @ResponseBody
    @GetMapping("/users")
    public String users() {
        List<User> users = usersRepository.getAll();

        String result = "";


        for (int i = 0; i < users.size(); i++) {
            result += users.get(i).toString() + "</br>";
        }
        return result;
    }


    @PostMapping("/add")
    public String addUser(@RequestParam(defaultValue = "Anonim", required = false) String firstName, @RequestParam String lastName, @RequestParam String age) {

        int ageInt = Integer.parseInt(age);

        if (StringUtils.isEmpty(firstName)) {
            return "redirect:/error.html";
        } else {
            User user = new User(firstName, lastName, ageInt);
            usersRepository.add(user);
            return "redirect:/success.html";
        }
    }


    @ResponseBody
    @PostMapping("/findFirstName")
    public String findByFirstName(@RequestParam String phrase) {
        List<User> foundUsers = usersRepository.getAll().stream()
                .filter(user -> user.getFirstName().equals(phrase))
                .collect(Collectors.toList());

        return getInfos(foundUsers);
    }

    @ResponseBody
    @PostMapping("/findLastName")
    public String findByLastName(@RequestParam String phrase) {
        List<User> foundUsers = usersRepository.getAll().stream()
                .filter(user -> user.getLastName().equals(phrase))
                .collect(Collectors.toList());

        return getInfos(foundUsers);
    }

    @ResponseBody
    @PostMapping("/findAge")
    public String findByAge(@RequestParam String phrase) {
        try {
            int ageValue = Integer.parseInt(phrase);
            List<User> foundUsers = usersRepository.getAll().stream()
                    .filter(user -> user.getAge() == ageValue)
                    .collect(Collectors.toList());
            return getInfos(foundUsers);
        } catch (NumberFormatException ex) {
            return "Podany wiek nie jest liczbą";
        }
    }


    private String getInfos(List<User> users) {
        String result = "";
        if (users.isEmpty()) {
            return "Brak wyników wyszukiwania po imieniu";
        }
        for (int i = 0; i < users.size(); i++) {
            result += users.get(i).toString() + "</br>";
        }
        return result;
    }
}
