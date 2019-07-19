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
public class User {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="password")
    private String password;
    
    @Override
    public String toString() {
        return String.format(
                "user[id=%s]",
                id);
    }

}