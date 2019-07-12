package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;

public class CustomerDAO {
	
	@Autowired
	private CustomerRepository repository;
	
	public void printDB() {
		System.out.println("왜 안대는거야");
		repository.findAll();
	}
}
