package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;

public class CustomerDAO {
	
	@Autowired
	private CustomerRepository repository;
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
}
