package com.example.sharefekhair.repository;

import com.example.sharefekhair.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<MyUser, UUID> {
    Optional<MyUser> findMyUsersByUsername(String username);
    Optional<MyUser> findMyUserByUsername(String username);

}
