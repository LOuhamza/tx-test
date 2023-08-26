package com.baeldung.multipledb.service;

import com.baeldung.multipledb.product.entity.Product;
import com.baeldung.multipledb.product.repository.ProductRepository;
import com.baeldung.multipledb.user.entity.User;
import com.baeldung.multipledb.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired(required = true)
    private ProductRepository productRepository;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void performTransactionalOperations() {
       // try {

                User user = User.builder()
                        .id(1)
                        .name("student" + 1)
                        .email("student" + 1 + "@gmail.com")
                        .build();
                this.userRepository.save(user);


            Product product = Product.builder()
                    .id(1)
                    .name("Pc hp")
                    .price(10.0)
                    .build();
            this.productRepository.save(product);

       // } catch (Exception e) {
            // Gérer l'exception ou la relancer pour provoquer un rollback
       // }
    }

}
