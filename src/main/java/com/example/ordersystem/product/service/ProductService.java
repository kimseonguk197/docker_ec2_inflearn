package com.example.ordersystem.product.service;

import com.example.ordersystem.member.domain.Member;
import com.example.ordersystem.member.repository.MemberRepository;
import com.example.ordersystem.product.domain.Product;
import com.example.ordersystem.product.dto.ProductRegisterDto;
import com.example.ordersystem.product.dto.ProductResDto;
import com.example.ordersystem.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public ProductService(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public Product productCreate(ProductRegisterDto dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByEmail(authentication.getName()).orElseThrow(()->new EntityNotFoundException("member is not found"));

        Product product = productRepository.save(dto.toEntity(member));
        return product;
    }

    public List<ProductResDto> findAll(){
        List<Product> productList = productRepository.findAll();
        List<ProductResDto> productResDtos = new ArrayList<>();
        for(Product p : productList){
            ProductResDto productResDto = ProductResDto.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .stockQuantity(p.getStockQuantity())
                    .seller(p.getName())
                    .build();
            productResDtos.add(productResDto);
        }
        return productResDtos;
    }

}
