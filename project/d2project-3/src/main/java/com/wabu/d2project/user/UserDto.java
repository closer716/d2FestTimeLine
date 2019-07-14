package com.wabu.d2project.user;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	@NotBlank
	public String name;
	
	@NotBlank
	@Size(min=7,max=15)
    public String address;
	
	public UserDto(String name, String address){
		this.name = name;
		this.address = address;
	}
	
	
	public User toEntity() {
		return new User(address, name);
	}
    
}
