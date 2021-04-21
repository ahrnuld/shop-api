package nl.inholland.shop.service;

import nl.inholland.shop.model.Product;
import nl.inholland.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }
}
