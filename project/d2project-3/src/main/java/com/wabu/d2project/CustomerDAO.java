package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAO {
	
	@Autowired
	private CustomerRepository repository;
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
}
