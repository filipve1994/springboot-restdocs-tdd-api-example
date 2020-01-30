package com.filip.springbootrestdocstddapi.web.controllers;

import com.filip.springbootrestdocstddapi.errorhandling.dto.request.UserInput;
import com.filip.springbootrestdocstddapi.errorhandling.exceptions.UserNotFoundException;
import com.filip.springbootrestdocstddapi.errorhandling.models.ApiError;
import com.filip.springbootrestdocstddapi.persistence.models.User;
import com.filip.springbootrestdocstddapi.persistence.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user")
@AllArgsConstructor
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = {"/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserInput user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll(
            @Valid @Positive(message = "Page number should be a positive number") @RequestParam(required = false, defaultValue = "1") int page,
            @Valid @Positive(message = "Page size should be a positive number") @RequestParam(required = false, defaultValue = "10") int size) {
        HttpHeaders headers = new HttpHeaders();
//        Page<User> users = repository.findAll(PageRequest.of(page, size));
        Page<User> users = userService.findAll(PageRequest.of(page, size));
        headers.add("X-Users-Total", Long.toString(users.getTotalElements()));
        return new ResponseEntity<>(users.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }




}
