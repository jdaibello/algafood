package com.algaworks.algafood.auth.core;

import com.algaworks.algafood.auth.domain.UserModel;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class AuthUser extends User {
    private static final long serialVersionUID = 1L;

    private String fullName;

    public AuthUser(UserModel user) {
        super(user.getEmail(), user.getPassword(), Collections.emptyList());

        this.fullName = user.getName();
    }
}
