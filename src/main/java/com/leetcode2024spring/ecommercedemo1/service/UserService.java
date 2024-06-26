package com.leetcode2024spring.ecommercedemo1.service;

import com.leetcode2024spring.ecommercedemo1.collection.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {


    User findByEmail(String email);

    long count();

    String save(User user);
    ResponseEntity<User> registerUser(User user);
    boolean loginUser(String email, String password);

    public boolean upsertWishlist(String email, String productStringId);

    public boolean updateWishlist(@RequestParam String email, @RequestParam String productStringId);

}
