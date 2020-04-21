package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    private UsersRepository usersRepository;

    //    @Autowired
    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    //    @GetMapping("/users")
//    @PostMapping("/users")
    @ResponseBody
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users() {
        List<User> users = usersRepository.getAll();

        String result = "";


        for (int i = 0; i < users.size(); i++) {
            result += users.get(i).toString() + "</br>";
        }
        return result;
    }
    

    @RequestMapping("/add")
    public String addUser(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String age = request.getParameter("age");

        int ageInt = Integer.parseInt(age);

        if (StringUtils.isEmpty(firstName)) {
            return "/error.html";
        } else {
            User user = new User(firstName, lastName, ageInt);
            usersRepository.add(user);
            return "/success.html";
        }
    }
}
