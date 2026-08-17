package com.lernia.auth.repository;

import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.entity.enums.AuthProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    // Adicione esta linha para resolver o erro no AuthService:
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

	Optional<UserEntity> findByProviderAndProviderId(AuthProvider authProvider, String providerId);
}