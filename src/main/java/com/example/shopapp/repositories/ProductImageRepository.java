package com.example.shopapp.repositories;

import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(long productId);
}
