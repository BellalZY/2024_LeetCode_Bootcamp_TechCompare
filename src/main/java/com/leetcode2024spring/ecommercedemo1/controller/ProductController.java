package com.leetcode2024spring.ecommercedemo1.controller;

import com.leetcode2024spring.ecommercedemo1.collection.PriceHistory;
import com.leetcode2024spring.ecommercedemo1.collection.Product;
import com.leetcode2024spring.ecommercedemo1.collection.Review;
import com.leetcode2024spring.ecommercedemo1.collection.User;
import com.leetcode2024spring.ecommercedemo1.service.ProductServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImp productService;

    @GetMapping("/getall")
    public List<Product> fetchAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/getproduct")
    public Product getproduct() {
        Product product = productService.getByProductStringId("109");
        System.out.println(product.getProductName());
        System.out.println(product.getCategory());
        return product;
    }


    @GetMapping("/{productStringId}")
    public ResponseEntity<Product> getProductByStringId(@PathVariable String productStringId) {
        Product product = productService.getByProductStringId(productStringId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/bycategory")
    public List<Product> getProductsByCategory(@RequestParam("category") String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/bybrand")
    public List<Product> getProductsByBrand(@RequestParam("brand") String brand) {
        return productService.getProductsByBrand(brand);
    }

    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("name") String name) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/brands")
    public List<String> getAllBrands() {
        return productService.getAllBrands();
    }

    @GetMapping("/filterByPrice")
    public List<Product> getProductsByPriceRange(
            @RequestParam("minPrice") Double minPrice,
            @RequestParam("maxPrice") Double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/similar/{productId}")
    public List<Product> getSimilarProducts(
            @PathVariable String productId,
            @RequestParam(required = false) String category) {
        return productService.getOtherProductsInCategory(productId);
    }

    @GetMapping("/{productId}/reviews")
    public List<Review> getReviewsByProductId(@PathVariable String productId) {
        return productService.getReviewsByProductId(productId);
    }

    @GetMapping("/{productStringId}/price")
    public ResponseEntity<Double> getProductPrice(@PathVariable String productStringId) {
        Double price = productService.getPriceByProductId(productStringId);
        if (price == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(price);
    }
    @GetMapping("/{productStringId}/currentprice")
    public double getProductCurrentPrice(@PathVariable String productStringId) {
        Double price = productService.getPriceByProductId(productStringId);
        if (price == null) {
            return 0.0;
        }
        System.out.println("CurrentPrice: " +price);
        return price;
    }

    @GetMapping("/{productStringId}/price-history")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryByProductId(@PathVariable String productStringId) {
        List<PriceHistory> priceHistory = productService.getPriceHistoryByProductId(productStringId);
        if (priceHistory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(priceHistory);
    }

    @GetMapping("/bystores")
    public ResponseEntity<List<Product>> getProductsByStoreIdsAndInventory(
            @RequestParam("storeIds") String[] storeIds) {
        List<Product> products = productService.findProductsByStoreIds(storeIds);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public List<Product> getProductsByAll(@RequestParam(required = false) String category,
                                          @RequestParam(required = false) String brand,
                                          @RequestParam(required = false) Double minPrice,
                                          @RequestParam(required = false) Double maxPrice,
                                          @RequestParam(required = false) String store) {
        return productService.findProductsByCriteria(category, brand, minPrice, maxPrice, store);
    }



    @PostMapping("/compare")
    public List<Product> compareProducts(
            @RequestParam("productId1") String productId1,
            @RequestParam("productId2") String productId2
    ) {
        List<Product> ans = new LinkedList<Product>();
        // Retrieve products from repository based on product IDs
        Product product1 = productService.getByProductStringId(productId1);
        Product product2 = productService.getByProductStringId(productId2);

        if (product1 != null && product2 != null) {
                ans.add(product1);
                ans.add(product2);
            // You can return both comparisons or choose to return one
            return ans;
        } else {
            return ans;
        }
    }
    @PostMapping("/sendreview")
    public String sendReview(@RequestParam("id")String id, @RequestBody Review review){
        return productService.sendReview(id,review);
    }
    @GetMapping("/getreview")
    public List<Review> getReview(@RequestParam("productStringId")String id){
        return productService.getReviewById(id);
    }

    @GetMapping("/getest")
    public Product getest(){
        return productService.gettest();
    }

    @GetMapping("/getest2")
    public void getest2(){
        productService.printAllProducts();
    }

    @PostMapping("/updatepricehist")
    public void updatepricehis(@RequestParam("productId")String productId, @RequestParam("newPrice")double newPrice){
        // add
        String ret = productService.addPriceHistory(productId,newPrice);
        System.out.println("addpricehist");
        System.out.println(ret);
    }

    ///just for testing
    @GetMapping("/test")
    public List<Product> getProductsByAll2(@RequestParam(required = false) String category,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) Double minPrice,
                                           @RequestParam(required = false) Double maxPrice) {
        return productService.findProductsByCriteria(category, brand, minPrice, maxPrice,brand);
    }
   //////////
}

