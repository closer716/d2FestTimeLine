package com.wabu.d2project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMongoDBTest {
    @Autowired
    private CustomerRepository customerMongoDBRepository;
 
    @Test
    public void printProjectData() {
        System.out.println(customerMongoDBRepository.findAll());
    }
}