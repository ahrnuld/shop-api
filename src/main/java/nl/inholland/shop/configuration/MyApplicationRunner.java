package nl.inholland.shop.configuration;

import nl.inholland.shop.model.Category;
import nl.inholland.shop.model.Product;
import nl.inholland.shop.service.CategoryService;
import nl.inholland.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Category category = new Category(1, "Books");

        categoryService.addCategory(category);

        Product product1 = new Product(1, "Testboek", "testomschrijving", 4);
        product1.setCategory(category);
        productService.addProduct(product1);
    }
}
