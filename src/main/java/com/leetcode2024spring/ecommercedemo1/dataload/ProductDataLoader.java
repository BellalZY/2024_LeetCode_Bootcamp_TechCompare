    //package com.leetcode2024spring.ecommercedemo1.dataload;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.leetcode2024spring.ecommercedemo1.collection.Product;
//import com.leetcode2024spring.ecommercedemo1.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class ProductDataLoader implements CommandLineRunner {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @Override
//    public void run(String... args) throws IOException {
//        // 指定JSON文件路径
//        Resource resource = resourceLoader.getResource("classpath:product.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // 由于MongoDB使用的是，读取并解析JSON文件
//        List<Product> products = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Product>>(){});
//
//        // 保存数据到数据库
//        productRepository.saveAll(products);
//        System.out.println("加载product.json数据");
//    }
//}
