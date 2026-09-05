package com.lernia.auth.oauth2;

import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final UserEntity user;

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User != null ? oauth2User.getAttributes() : Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = (user != null && user.getUserRole() != null) ? user.getUserRole() : UserRole.USER;
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getName() {
        if (user == null) {
            return oauth2User != null ? oauth2User.getName() : "";
        }
        return user.getUsername() != null ? user.getUsername() : user.getEmail();
    }
}