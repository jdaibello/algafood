package com.algaworks.algafood.auth.core;

import com.algaworks.algafood.auth.domain.UserModel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AuthUser extends User {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String fullName;

    public AuthUser(UserModel user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);

        this.userId = user.getId();
        this.fullName = user.getName();
    }
}
