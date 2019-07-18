package com.wabu.d2project.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Data
public class Profile {
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="birthday")
    private String birthday;
    
    @Column(name="country")
    private String country;
    
    @Override
    public String toString() {
        return String.format(
                "profile[id=%s, country='%s', birthday='%s']",
                id, country, birthday);
    }
}
