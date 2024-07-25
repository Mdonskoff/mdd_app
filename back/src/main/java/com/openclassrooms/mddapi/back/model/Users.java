package com.openclassrooms.mddapi.back.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String pseudo;

    private String email;

    private String password;

    private Date created_at;

    private Date updated_at;


    @OneToMany
    private List<Topics> topicsList;

    @PrePersist //avant la sauvegarde dans la BDD pendant la création
    public void onCreate() {
        this.created_at = new Date(); //la date se créera automatiquement avant la sauvegarde dans la BDD
        this.updated_at = new Date();
    }

    @PreUpdate //avant la sauvegarde dans la BDD pendant la MAJ
    public void onUpdate() {
        this.updated_at = new Date();
    }

    //UserDetails implements methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USERS"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

