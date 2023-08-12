package com.fmatheus.app.controller.security;


import com.fmatheus.app.controller.rule.MessageResponseRule;
import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final MessageResponseRule rule;

    /**
     * @param username nome de usuario
     * @return UserDetails
     * @throws UsernameNotFoundException Lancamento de excecao
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Username: {}", username);
        var user = this.userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha incorreto."));

        if (!user.isActive()) {
            log.error("Usuário inativo: {}", username);
            throw this.rule.errorUserInactiveException();
        }

        log.info("Usuario encontrado: {}", username);

        return new CustomUserDetails(user, this.authorities(user));
    }

    private Collection<? extends GrantedAuthority> authorities(User user) {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        user.getPermissions().forEach(permission -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission.getName().toLowerCase())));
        return simpleGrantedAuthorities;
    }

}
