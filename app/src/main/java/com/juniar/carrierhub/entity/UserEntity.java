package com.juniar.carrierhub.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Jarvis on 17/03/2018.
 */
@Entity(nameInDb = "user")
public class UserEntity {
    @Id(autoincrement = true)
    Long id;
    @Property(nameInDb = "username")
    String username;
    @Property(nameInDb = "password")
    String password;
    @Property(nameInDb = "role")
    String role;


    @Generated(hash = 708753960)
    public UserEntity(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
