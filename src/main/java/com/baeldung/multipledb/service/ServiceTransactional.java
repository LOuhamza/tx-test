package com.baeldung.multipledb.service;

import com.baeldung.multipledb.product.entity.Product;
import com.baeldung.multipledb.product.repository.ProductRepository;
import com.baeldung.multipledb.user.entity.User;
import com.baeldung.multipledb.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ServiceTransactional {

    @Autowired
    @Qualifier("edpTransactionManager")
    private PlatformTransactionManager transactionManager1;

    @Autowired
    @Qualifier("narsaTransactionManager")
    private PlatformTransactionManager transactionManager2;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private ProductRepository productRepository;



    @Transactional
    public void performOperationsInTransaction() {
        TransactionStatus status1 = transactionManager1.getTransaction(new DefaultTransactionDefinition());
        TransactionStatus status2 = transactionManager2.getTransaction(new DefaultTransactionDefinition());

        try {
            // Perform operations using repository methods
            User user = User.builder()
                    .id(1)
                    .name("student" + 1)
                    .email("student" + 1 + "@gmail.com")
                    .build();

            Product product = Product.builder()
                    .id(1)
                    .name("Pc hp")
                    .price(10.0)
                    .build();

            productRepository.save(product);
            if(true)
                throw new RuntimeException("error tx");
            userRepository.save(user);

            // Commit transactions
            transactionManager1.commit(status1);
            transactionManager2.commit(status2);
        } catch (Exception e) {
            // Rollback transactions
            System.out.println(e.fillInStackTrace());
            transactionManager1.rollback(status1);
            transactionManager2.rollback(status2);
        }
    }
}


