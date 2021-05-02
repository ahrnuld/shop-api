package nl.inholland.shop.configuration;

import nl.inholland.shop.model.Category;
import nl.inholland.shop.model.Product;
import nl.inholland.shop.model.Role;
import nl.inholland.shop.model.User;
import nl.inholland.shop.service.CategoryService;
import nl.inholland.shop.service.ProductService;
import nl.inholland.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Category category = new Category(1, "Books");

        categoryService.addCategory(category);

        Product product1 = new Product(1, "Testboek", "testomschrijving", 4);
        product1.setCategory(category);
        productService.addProduct(product1);

        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("testpassword");
        testUser.setRoles(Arrays.asList(Role.ROLE_USER));

        userService.add(testUser);

    }
}
