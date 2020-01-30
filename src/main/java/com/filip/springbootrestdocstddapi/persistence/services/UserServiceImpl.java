package com.filip.springbootrestdocstddapi.persistence.services;

import com.filip.springbootrestdocstddapi.errorhandling.dto.request.UserInput;
import com.filip.springbootrestdocstddapi.errorhandling.exceptions.UserNotFoundException;
import com.filip.springbootrestdocstddapi.persistence.models.User;
import com.filip.springbootrestdocstddapi.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User save(UserInput user) {
        return userRepository.saveAndFlush(
                new User(
                        null,
                        user.getLastName(),
                        user.getMiddleName(),
                        user.getFirstName(),
                        user.getDateOfBirth(),
                        user.getSiblings())
        );
    }

    @Override
    public User findOne(@PathVariable Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' is not found"));
    }

}
