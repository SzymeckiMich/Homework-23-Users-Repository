package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository {


    private List<User> users;

    @Autowired
    public UsersRepository() {
        users = new ArrayList<>();
        users.add(new User("Michał", "Kowalski", 23));
        users.add(new User("Twoja", "Stara", 69));
        users.add(new User("Twoja", "Starsza", 100));
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public void add(User user) {
        users.add(user);
    }

}
