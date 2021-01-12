package com.lakitchen.LA.Kitchen.service.global;

import com.lakitchen.LA.Kitchen.repository.ProductRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class Func {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Boolean isExistUser(Integer id) {
        return userRepository.findFirstById(id) != null;
    }

    public Boolean isExistProduct(Integer id) {
        return productRepository.findFirstById(id) != null;
    }

    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
