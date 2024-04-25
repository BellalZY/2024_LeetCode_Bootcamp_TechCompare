package com.leetcode2024spring.ecommercedemo1.controller;

import com.leetcode2024spring.ecommercedemo1.collection.Product;
import com.leetcode2024spring.ecommercedemo1.collection.User;
import com.leetcode2024spring.ecommercedemo1.collection.wlist;
import com.leetcode2024spring.ecommercedemo1.service.ProductServiceImp;
import com.leetcode2024spring.ecommercedemo1.service.UserService;
import com.leetcode2024spring.ecommercedemo1.service.UserServiceImp;
import com.leetcode2024spring.ecommercedemo1.util.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://blue-hill-0255a820f.5.azurestaticapps.net/")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImp userService;
    @Autowired
    private ProductServiceImp productService;


    @GetMapping("/getUser")
    public User findByEmail(String email){
        return userService.findByEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean loginSuccess = userService.loginUser(user.getEmail(), user.getPassword());
        System.out.println("login: "+loginSuccess );
        if (loginSuccess) {
            JwtTokenUtil jwt = new JwtTokenUtil();
            String token = jwt.generateToken(user.getEmail());

            // 将 Token 放在响应头部中返回给客户端
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Access-Control-Expose-Headers", "Authorization");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Login failed");
        }
    }



    @GetMapping("/getWishlist")
    public List<Product> getWishlist(@RequestParam String email){
        System.out.println("getwishlist__");
        List<wlist> pIdList = userService.findByEmail(email).getWishlist();
        List<Product> res = new LinkedList<>();
        for(wlist s : pIdList){
            Product pro = productService.getByProductStringId(s.getProductStringId());
            res.add(pro);
        }
        return res;
    }

    @PostMapping("/addWishlist") // 改为使用 POST 方法
    public String addWishlist(@RequestParam String email, @RequestParam String productId) {
        System.out.println("addwishlist__");
        try {
            userService.addToWishlist(email, productId);
            System.out.println("add to wishlist");
        } catch (Exception e) {
            return "fail add to wish list";
        }
        return "add"+ productId +" to wish list";
    }

    @PostMapping("/removeFromWishlist") // 改为使用 POST 方法
    public boolean removeWishlist(@RequestParam String email, @RequestParam String productId) {
        System.out.println("remove_from_wishlist");
        try {
            userService.removeFromWishlist(email, productId);
            System.out.println("remove From wishlist");
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    @PostMapping("/addProductToWishlist")
    public ResponseEntity<Boolean> upsertWishlist(String email, String productStringId){
        boolean addSuccess = userService.upsertWishlist(email, productStringId);
        return ResponseEntity.ok(addSuccess);
    }

    @PostMapping("/deleteProductFromWishlist")
    public ResponseEntity<Boolean> updateWishlist(String email, String productStringId){
        boolean addSuccess = userService.updateWishlist(email, productStringId);
        return ResponseEntity.ok(addSuccess);
    }

    @GetMapping("/home")
    public String home() {
        return "home.html"; // 返回html文件的名称，Spring Boot会自动查找位于resources/static或resources/templates目录下的HTML文件
    }
}
