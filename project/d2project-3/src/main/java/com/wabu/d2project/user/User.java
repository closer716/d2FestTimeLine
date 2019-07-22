package com.wabu.d2project.user;

import org.springframework.data.annotation.Id;

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
}