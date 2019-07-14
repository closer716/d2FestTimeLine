package com.wabu.d2project.user;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	@NotBlank
    @Id
    private String user_id;
	
	@NotBlank
    private String user_name;
	
	@NotBlank
    private String user_password;
	
	@NotBlank
    private String birthday;
    
    private String elm_school;
    
    private String mid_school;
    
    private String high_school;
    
    private String univ_school;

    private String office;
    
	@NotBlank
	@Size(min=7,max=15)
    public String address;
	
	public UserDto(String user_id, String user_name, String user_password, String birthday){
		this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.birthday = birthday;
	}
	
	
	public User toEntity() {
		return new User(user_id, user_name, user_password, birthday);
	}
    
}
