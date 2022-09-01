package com.intuit.user.service.dto;

import com.intuit.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsDTO implements UserDetails {
    private static final List<SimpleGrantedAuthority> simpleGrantedAuthorities = Arrays.asList(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    private final long id;
    private final String userName;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public UserDetailsDTO(long id, String email, String password, List<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.userName = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails create(User user) {

        return new UserDetailsDTO(user.getId(), user.getEmail(), user.getPassword(), simpleGrantedAuthorities);
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
        return userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsDTO that = (UserDetailsDTO) o;
        return id == that.id && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, authorities);
    }
}
