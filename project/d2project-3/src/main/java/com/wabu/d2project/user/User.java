package com.wabu.d2project.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.wabu.d2project.Functions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class User {

    @Id
    private String id;

    private String password;
    
    @Override
    public String toString() {
        return String.format(
                "user[id=%s]",
                id);
    }
    
    public String toValues() {
    	String[] str = {id,password};
    	return Functions.makeValues(str);
    }
    
    public String toColumns() {
    	return "id,password";
    }
}