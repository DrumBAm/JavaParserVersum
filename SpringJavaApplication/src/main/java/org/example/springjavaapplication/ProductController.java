package org.example.springjavaapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String showSearchForm() {
        return "searchForm";
    }

    @PostMapping("/search")
    @ResponseBody
    public byte[] searchProduct(@RequestParam String query) throws IOException {
        List<Product> products = productService.searchProducts(query);
        return productService.generateExcelBytes(products);
    }
}


