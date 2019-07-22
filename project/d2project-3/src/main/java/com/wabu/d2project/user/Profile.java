package com.wabu.d2project.user;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Profile {
    @Id
    private String id;
    
    private String name;
    
    private Date birthday;
    
    private String country;
    
    private String elmSchool;
    
    private String midSchool;
    
    private String highSchool;
    
    private String univSchool;
    
    private String office;
    
    @Override
    public String toString() {
        return String.format(
                "profile[id=%s, country='%s', birthday='%s']",
                id, country, birthday.toString());
    }
}
