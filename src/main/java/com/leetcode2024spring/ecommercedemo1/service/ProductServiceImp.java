package com.leetcode2024spring.ecommercedemo1.service;

import com.leetcode2024spring.ecommercedemo1.collection.PriceHistory;
import com.leetcode2024spring.ecommercedemo1.collection.Inventory;
import com.leetcode2024spring.ecommercedemo1.collection.Product;
import com.leetcode2024spring.ecommercedemo1.collection.Review;
import com.leetcode2024spring.ecommercedemo1.collection.Store;
import com.leetcode2024spring.ecommercedemo1.repository.ProductRepository;
import com.leetcode2024spring.ecommercedemo1.repository.StoreRepository;
import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.mongodb.core.MongoTemplate;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp {

    //private static final Logger log = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    //private SequenceGeneratorService sequenceGeneratorService;

    public ProductServiceImp(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public void printAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(System.out::println);
    }



    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        List<Product> products = productRepository.findAll();
        for (Product p:products){
            System.out.println(p.getCategory());
        }
        System.out.println(123);

        return optionalProduct.orElse(null); // Return null if product is not found
    }

    public Product getByProductStringId(String productStringId) {
        Product product = productRepository.findByProductStringId(productStringId);
        return product; // Return null if product is not found
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }


    public List<Product> searchProductsByName(String name) {
        String regex = ".*" + name + ".*";
        return productRepository.findByProductNameMatches(regex);
    }

    public List<String> getAllCategories() {
        return productRepository.findAllCategories().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllBrands() {
        return productRepository.findAllBrands().stream()
                .map(Product::getBrand)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<Product> getOtherProductsInCategory(String productId) {
        // Use your custom method to find the product by its string ID
        Product product = productRepository.findByProductStringId(productId);
        if (product == null) {
            throw new NoSuchElementException("Product not found with ID: " + productId);
        }

        // Now that you have the product and hence its category, fetch other products in the same category
        return productRepository.findByCategoryAndIdNot(product.getCategory(), productId);
    }

    public List<Review> getReviewsByProductId(String productStringId) {
        Product product = productRepository.findByProductStringId(productStringId);
        return product != null ? product.getReview() : null;
    }

    public Double getPriceByProductId(String productStringId) {
        Product product = productRepository.findByProductStringId(productStringId);
        return product != null ? product.getCurrentPrice() : null;
    }

    public List<PriceHistory> getPriceHistoryByProductId(String productStringId) {
        Product product = productRepository.findByProductStringId(productStringId);
        return product != null ? product.getPriceHistory() : null;
    }

    public List<Product> findProductsByStoreIds(String[] storeIds) {
        List<Store> stores = storeRepository.findByStoreStringIdIn(Arrays.asList(storeIds));
        Set<String> productIds = new HashSet<>();
        for (Store store : stores) {
            store.getInventory().stream()
                    .filter(inv -> inv.getQuantity() > 0)
                    .forEach(inv -> productIds.add(inv.getProductStringId()));
        }

        return productRepository.findByIdIn(new ArrayList<>(productIds));
    }

    public List<Product> findProductsByCriteria(String category, String brand, Double minPrice, Double maxPrice, String store) {
        Query query = new Query();
        List<Criteria> conditions = new ArrayList<>();

        if (category.length() != 0) {
            conditions.add(Criteria.where("category").is(category));
        }

        if (brand.length() != 0) {
            conditions.add(Criteria.where("brand").is(brand));
        }

        if (minPrice != null && maxPrice != null) {
            conditions.add(Criteria.where("currentPrice").gte(minPrice).lte(maxPrice));
        } else if (minPrice != null) {
            conditions.add(Criteria.where("currentPrice").gte(minPrice));
        } else if (maxPrice != null) {
            conditions.add(Criteria.where("currentPrice").lte(maxPrice));
        }

        if (store != null) {
            conditions.add(Criteria.where("store").is(store));
        }

        if (!conditions.isEmpty()) {
            Criteria combinedCriteria = new Criteria().andOperator(conditions.toArray(new Criteria[0]));
            query.addCriteria(combinedCriteria);
        }

        System.out.println(query.toString());  // Log the query to understand its final form

        List<Product> products = mongoTemplate.find(query, Product.class);
        System.out.println(products);  // Print the query results if needed

        return products;
    }



    public String compareProductsByPrice(Product product1, Product product2) {
        // need to add more detailed information
        double price1 = product1.getCurrentPrice();
        double price2 = product2.getCurrentPrice();
        String ans = "";
        if (price1>price2){
            ans = "The first product has a higher price";
        }
        else if (price1==price2){
            ans = "They have the same price";
        }
        else{
            ans = "The second product has a higher price";
        }
        return ans;
    }

    public String compareProductsByCategory(Product product1, Product product2) {
        // need to add more detailed information
        String ans = "";
        String s1 = product1.getCategory();
        String s2 = product2.getCategory();
        if (s1.equals(s2)){
            return "Same Category";
        }
        return "Different Category";
    }
    @Transactional
    public String sendReview(String id, Review review){
        Query query = new Query();
        query.addCriteria(Criteria.where("productStringId").is(id));

        // Fetch the current reviews from the product
        Product product = mongoTemplate.findOne(query, Product.class);
        if (product == null) {
            return "Not Find Product";
        }

        // Assuming there is a list of reviews in the Product class
        List<Review> reviews = product.getReview();
        reviews.add(review); // Add the new review to the list

        // Update operation to push a new review to the reviews array
        Update update = new Update();
        update.set("review", reviews); // This replaces the existing reviews list with the updated one

        // Perform the update operation
        mongoTemplate.updateFirst(query, update, Product.class);
        return review.getComment(); // Return true indicating the review was added successfully

    }

    public List<Review> getReviewById(String id){
        Product product = productRepository.findByProductStringId(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        // includes comment, rating etc.
        System.out.println("getreviews");
        System.out.println(product.getCategory());

        List<Review> reviews = product.getReview();
        for (Review each: reviews){
            System.out.println(each.getComment());
        }
        return reviews;
    }

    public Product gettest(){
        Product product = productRepository.findByProductStringId("1");
        return product;
    }

    @Transactional
    public String addPriceHistory(String productId, double newPrice) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productStringId").is(productId));

        // Create a new price history entry
        PriceHistory priceHistory = new PriceHistory(new Date(), newPrice);

        // Use MongoDB's $push operator to add the new price history to the priceHistory array
        Update update = new Update();
        update.push("priceHistory", priceHistory);

        // Perform the update operation
        mongoTemplate.updateFirst(query, update, Product.class);
        return "Price history added successfully for product ID: " + productId;
    }



}

