package com.example.store.product.service;

import com.example.store.common.ImageUploadUtil;
import com.example.store.product.domain.Product;
import com.example.store.product.dto.ProductRequestDto;
import com.example.store.product.dto.ProductResponseDto;
import com.example.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageUploadUtil imageUploadUtil;

    // 상품 등록
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setItemName(requestDto.getItemName());
        product.setPrice(requestDto.getPrice());
        product.setStockQuantity(requestDto.getStockQuantity());
        product.setDescription(requestDto.getDescription());

        MultipartFile imageFile = requestDto.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedFileName = imageUploadUtil.saveImage(imageFile);
            product.setImageUrl(savedFileName);
        }

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(
                savedProduct.getId(),
                savedProduct.getItemName(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getImageUrl(),
                savedProduct.getDescription()
        );
    }

    // 상품 수정
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("조회되는 상품 정보가 없습니다."));

        product.setItemName(requestDto.getItemName());
        product.setPrice(requestDto.getPrice());
        product.setStockQuantity(requestDto.getStockQuantity());
        product.setDescription(requestDto.getDescription());

        MultipartFile imageFile = requestDto.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedFileName = imageUploadUtil.saveImage(imageFile);
            product.setImageUrl(savedFileName);
        }

        Product updatedProduct = productRepository.save(product);

        return new ProductResponseDto(
                updatedProduct.getId(),
                updatedProduct.getItemName(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getImageUrl(),
                updatedProduct.getDescription()
        );
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("조회되는 상품 정보가 없습니다."));

        productRepository.delete(product);
    }

    // 상품 목록 조회
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getItemName(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getImageUrl(),
                        product.getDescription()
                ))
                .collect(Collectors.toList());
    }

    // 상품 상세 조회
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("조회되는 상품 정보가 없습니다."));

        return new ProductResponseDto(
                product.getId(),
                product.getItemName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getDescription()
        );
    }
}
