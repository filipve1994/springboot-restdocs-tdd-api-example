package com.filip.springbootrestdocstddapi.persistence.services;

import com.filip.springbootrestdocstddapi.errorhandling.dto.request.UserInput;
import com.filip.springbootrestdocstddapi.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    User save(UserInput user);

    User findOne(@PathVariable Long id);
}
