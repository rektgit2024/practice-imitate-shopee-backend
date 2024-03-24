package com.springboot.practiceimitateshopeebackend.service;

import com.springboot.practiceimitateshopeebackend.model.ProductModel;
import com.springboot.practiceimitateshopeebackend.model.StockRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductModel saveProduct(ProductModel model);
    void saveProductStocks(StockRequest stockRequest);
    void saveProductVariationStocks(StockRequest stockRequest);
    List<ProductModel> searchProduct(String search);
    Optional<ProductModel> getOneById(Long id);
    void delete(Long id);



}
