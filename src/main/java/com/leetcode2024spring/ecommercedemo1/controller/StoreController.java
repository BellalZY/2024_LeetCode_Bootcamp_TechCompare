package com.leetcode2024spring.ecommercedemo1.controller;

import com.leetcode2024spring.ecommercedemo1.collection.Inventory;
import com.leetcode2024spring.ecommercedemo1.collection.Product;
import com.leetcode2024spring.ecommercedemo1.collection.Store;
import com.leetcode2024spring.ecommercedemo1.collection.StoreInventoryDTO;
import com.leetcode2024spring.ecommercedemo1.service.ProductServiceImp;
import com.leetcode2024spring.ecommercedemo1.service.StoreService;
import com.leetcode2024spring.ecommercedemo1.service.StoreServiceImp;
import com.leetcode2024spring.ecommercedemo1.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://blue-hill-0255a820f.5.azurestaticapps.net/")
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreServiceImp storeService;
    @Autowired
    private ProductServiceImp productService;

    @GetMapping("/getStoreByInventoryQuantity")
    public List<StoreInventoryDTO> getStoreByQuantity(@RequestParam String productStringId) {
        List<Store> allStores = storeService.getAllStore();
        List<StoreInventoryDTO> result = new ArrayList<>();
        for (Store store : allStores) {
            for (Inventory inventory : store.getInventory()) {
                if (inventory.getProductStringId().equals(productStringId) && inventory.getQuantity() > 0) {
                    result.add(new StoreInventoryDTO(store, inventory.getQuantity()));
                    break; // Breaks after adding the store once with the needed product quantity
                }
            }
        }
        return result;
    }
}
