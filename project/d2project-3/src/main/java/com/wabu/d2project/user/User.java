package com.wabu.d2project.user;

import javax.persistence.Column;
import javax.persistence.Id;

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
    @Column(name="user_id")
    private String user_id;

    @Column(name="user_name")
    private String user_name;
    
    @Column(name="user_password")
    private String user_password;
    
    @Column(name="birthday")
    private String birthday;
    
    /*
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
*/

    @Override
    public String toString() {
        return String.format(
                "user[id=%s, name='%s', birthday='%s']",
                user_id, user_name, birthday);
    }

}