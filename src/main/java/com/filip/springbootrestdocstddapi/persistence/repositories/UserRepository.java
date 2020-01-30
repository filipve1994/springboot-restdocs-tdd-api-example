package com.filip.springbootrestdocstddapi.persistence.repositories;

import com.filip.springbootrestdocstddapi.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Page<User> findAll(Pageable pageable);
}
