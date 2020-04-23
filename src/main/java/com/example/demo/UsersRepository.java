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
        users.add(new User("Piotr", "Nowak", 35));
        users.add(new User("Piotr", "Jachon", 42));
        users.add(new User("Andrzej", "Jachon", 36));
        users.add(new User("Roman", "Górski", 36));
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public void add(User user) {
        users.add(user);
    }

    public void delete(User user){
        users.remove(user);
    }

}
