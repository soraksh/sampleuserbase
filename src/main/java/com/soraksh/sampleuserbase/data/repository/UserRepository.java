package com.soraksh.sampleuserbase.data.repository;

import com.soraksh.sampleuserbase.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    void deleteById(Long id);
}
