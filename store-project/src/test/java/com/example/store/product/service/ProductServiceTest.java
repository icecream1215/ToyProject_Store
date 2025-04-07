package com.example.store.product.service;

import com.example.store.product.domain.Product;
import com.example.store.product.dto.ProductRequestDto;
import com.example.store.product.dto.ProductResponseDto;
import com.example.store.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setItemName("상품 A");
        requestDto.setPrice(10000);
        requestDto.setStockQuantity(50);
        requestDto.setDescription("상품 A 설명");
        requestDto.setImage(null); // 파일 업로드는 생략

        // when
        ProductResponseDto responseDto = productService.createProduct(requestDto);

        // then
        Product savedProduct = productRepository.findById(responseDto.getId()).orElseThrow();
        assertThat(savedProduct.getItemName()).isEqualTo("상품 A");
        assertThat(savedProduct.getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getStockQuantity()).isEqualTo(50);
        assertThat(savedProduct.getDescription()).isEqualTo("상품 A 설명");
    }

    @Test
    void updateProduct() {
        // given
        Product product = new Product();
        product.setItemName("상품 B");
        product.setPrice(20000);
        product.setStockQuantity(30);
        product.setDescription("상품 B 설명");
        productRepository.save(product);

        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setItemName("상품 B 수정");
        updateDto.setPrice(25000);
        updateDto.setStockQuantity(40);
        updateDto.setDescription("상품 B 설명 수정");
        updateDto.setImage(null);

        // when
        productService.updateProduct(product.getId(), updateDto);

        // then
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updatedProduct.getItemName()).isEqualTo("상품 B 수정");
        assertThat(updatedProduct.getPrice()).isEqualTo(25000);
        assertThat(updatedProduct.getStockQuantity()).isEqualTo(40);
        assertThat(updatedProduct.getDescription()).isEqualTo("상품 B 설명 수정");
    }

    @Test
    void deleteProduct() {
        // given
        Product product = new Product();
        product.setItemName("상품 C");
        product.setPrice(15000);
        product.setStockQuantity(20);
        product.setDescription("상품 C 설명");
        productRepository.save(product);

        Long productId = product.getId();

        // when
        productService.deleteProduct(productId);

        // then
        boolean exists = productRepository.existsById(productId);
        assertThat(exists).isFalse();
    }

    @Test
    void getAllProducts() {
        // given
        Product product1 = new Product();
        product1.setItemName("상품 1");
        product1.setPrice(10000);
        product1.setStockQuantity(10);
        product1.setDescription("상품 1 설명");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setItemName("상품 2");
        product2.setPrice(20000);
        product2.setStockQuantity(20);
        product2.setDescription("상품 2 설명");
        productRepository.save(product2);

        // when
        List<ProductResponseDto> products = productService.getAllProducts();

        // then
        assertThat(products).hasSize(2);
        assertThat(products).extracting("itemName")
                .containsExactlyInAnyOrder("상품 1", "상품 2");
    }

    @Test
    void getProduct() {
        // given
        Product product = new Product();
        product.setItemName("상품 단일 조회");
        product.setPrice(30000);
        product.setStockQuantity(5);
        product.setDescription("상품 설명 단일 조회용");
        productRepository.save(product);

        Long productId = product.getId();

        // when
        ProductResponseDto responseDto = productService.getProduct(productId);

        // then
        assertThat(responseDto.getItemName()).isEqualTo("상품 단일 조회");
        assertThat(responseDto.getPrice()).isEqualTo(30000);
        assertThat(responseDto.getStockQuantity()).isEqualTo(5);
        assertThat(responseDto.getDescription()).isEqualTo("상품 설명 단일 조회용");
    }
}