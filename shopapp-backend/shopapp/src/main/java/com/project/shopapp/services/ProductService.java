package com.project.shopapp.services;

import com.project.shopapp.DTO.ProductDTO;
import com.project.shopapp.models.*;
import com.project.shopapp.responses.ProductsResponse;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
    List<ProductsResponse> getAllProducts();
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
    void saveThumbnail(Product product);

    List<Product> getProductByCategoryId(Long id);
}
