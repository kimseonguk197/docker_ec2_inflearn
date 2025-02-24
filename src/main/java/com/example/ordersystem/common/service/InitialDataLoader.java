package com.example.ordersystem.common.service;

import com.example.ordersystem.member.domain.Member;
import com.example.ordersystem.member.domain.Role;
import com.example.ordersystem.member.repository.MemberRepository;
import com.example.ordersystem.product.domain.Product;
import com.example.ordersystem.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//CommandLineRunner를 상속함으로서 해당 컴포넌트가 스프링빈으로 등록되는 시점에서 run메서드 자동실행
@Component
public class InitialDataLoader implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    public InitialDataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        if(memberRepository.findByEmail("admin@naver.com").isPresent())return;
        Member member = Member.builder()
                .name("admin")
                .email("admin@naver.com")
                .password(passwordEncoder.encode("12341234"))
                .role(Role.ADMIN)
                .build();
        memberRepository.save(member);

        Product product = Product.builder()
                .name("apple")
                .price(2000)
                .stockQuantity(200)
                .member(member)
                .build();
        productRepository.save(product);
    }
}
