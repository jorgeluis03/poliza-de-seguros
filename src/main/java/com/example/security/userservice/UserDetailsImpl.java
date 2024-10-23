package com.example.security.userservice;

import com.example.user.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    //IMPLEMENTA 'UserDetails' PARA PERSONALIZAR LOS DETALLES DEL USUARIO
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    // Constructor de la clase UserDetailsImpl
    public UserDetailsImpl(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // Metodo estático para crear una instancia de UserDetailsImpl a partir de un objeto Usuario
    public static UserDetailsImpl build(Usuario user) {
        // Convierte el rol del usuario en una lista de GrantedAuthority
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRol().getNombreRol()));

        return new UserDetailsImpl(
                user.getIdUsuario(),
                user.getNombreUsuario(),
                user.getContrasena(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}

