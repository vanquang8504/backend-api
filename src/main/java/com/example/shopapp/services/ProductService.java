package com.example.shopapp.services;

import com.example.shopapp.dataNotFoundException.DataNotFoundException;
import com.example.shopapp.dataNotFoundException.InvalidParamException;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category exitingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Cannot found category with id : "+ productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .category(exitingCategory)
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product by id : "+id));
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception{
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            Category exitingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot found category with id : "+ productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCategory(exitingCategory);
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            long productId,
            ProductImageDTO productImageDTO
    ) throws DataNotFoundException, InvalidParamException {
        Product exitingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot found product with id : "+ productId));
        ProductImage newProductImage = ProductImage.builder()
                .product(exitingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //can't update > 5 images
        int size = productImageRepository.findByProductId(productId).size();
        System.out.println(size);
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Max number of image is "+ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }

        return productImageRepository.save(newProductImage);
    }
}
