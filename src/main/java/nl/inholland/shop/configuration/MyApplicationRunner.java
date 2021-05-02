package nl.inholland.shop.configuration;

import nl.inholland.shop.model.Category;
import nl.inholland.shop.model.Product;
import nl.inholland.shop.service.CategoryService;
import nl.inholland.shop.service.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private CategoryService categoryService;
    private ProductService productService;

    public MyApplicationRunner(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Category testCategory = new Category(1, "Testcategory");
        categoryService.addCategory(testCategory);

        Product testProduct = new Product("Testbook", "testdescription", 5);
        testProduct.setCategory(testCategory);

        productService.addProduct(testProduct);
    }
}
