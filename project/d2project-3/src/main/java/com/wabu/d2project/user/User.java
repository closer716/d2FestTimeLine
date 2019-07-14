package com.wabu.d2project.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    private String user_id;

    @Column(name="user_name")
    private String user_name;
    
    @Column(name="user_password")
    private String user_password;
    
    @Column(name="birthday")
    private String birthday;
    
    @Column(name="elm_school")
    private String elm_school;
    
    @Column(name="mid_school")
    private String mid_school;
    
    @Column(name="high_school")
    private String high_shcool;
    
    @Column(name="univ_school")
    private String univ_school;
    
    @Column(name="address")
    private String address;
    
    @Column(name="office")
    private String office;

    protected User() {}

    public User( String user_id, String user_name, String user_password, String birthday) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return String.format(
                "user[id=%s, name='%s', birthday='%s']",
                user_id, user_name, birthday);
    }

}