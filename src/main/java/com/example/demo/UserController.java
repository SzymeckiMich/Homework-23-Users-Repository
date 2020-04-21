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
        return printList(users);
    }


    @PostMapping("/add")
    public String addUser(@RequestParam(defaultValue = "Anonim", required = false) String firstName,  @RequestParam(defaultValue = "Anonim", required = false)  String lastName, @RequestParam(defaultValue = "999", required = false) String age) {

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
    public String findFirstName(@RequestParam String phrase) {
        return printList(filterByFirstName(phrase));
    }

    @ResponseBody
    @PostMapping("/findLastName")
    public String findLastName(@RequestParam String phrase) {
        return printList(filterByLastName(phrase));
    }

    @ResponseBody
    @PostMapping("/findAge")
    public String findAge(@RequestParam String phrase) {
        try {
            return printList(filterByAge(phrase));
        } catch (NumberFormatException ex) {
            return "Podany wiek nie jest liczbą";
        }
    }

    @ResponseBody
    @PostMapping("/removeFirstName")
    public String removeWithFirstname(@RequestParam String phrase) {
        return remove(filterByLastName(phrase));
    }

    @ResponseBody
    @PostMapping("/removeLastName")
    public String removeWithLastName(@RequestParam String phrase) {
        return remove(filterByLastName(phrase));
    }

    @ResponseBody
    @PostMapping("/removeAge")
    public String removeWithAge(@RequestParam String phrase) {
        try {
            return remove(filterByAge(phrase));
        } catch (NumberFormatException ex) {
            return "Podany wiek nie jest liczbą";
        }
    }


    private List<User> filterByFirstName(@RequestParam String phrase) {
        return usersRepository.getAll().stream()
                .filter(user -> user.getFirstName().equals(phrase))
                .collect(Collectors.toList());
    }


    private List<User> filterByLastName(@RequestParam String phrase) {
        return usersRepository.getAll().stream()
                .filter(user -> user.getLastName().equals(phrase))
                .collect(Collectors.toList());
    }


    private List<User> filterByAge(@RequestParam String phrase) throws NumberFormatException {
        int ageValue = Integer.parseInt(phrase);
        return usersRepository.getAll().stream()
                .filter(user -> user.getAge() == ageValue)
                .collect(Collectors.toList());
    }

    private String remove(List<User> users) {
        if (users.isEmpty()) {
            return "Nie wyszukano pasujących wyników do usunięcia";
        }
        for (User user : users) {
            usersRepository.delete(user);
        }
        return "Usunięto";
    }

    private String printList(List<User> users) {
        String result = "";
        if (users.isEmpty()) {
            return "Brak wyników wyszukiwania";
        }
        for (int i = 0; i < users.size(); i++) {
            result += users.get(i).toString() + "</br>";
        }
        return result;
    }
}
