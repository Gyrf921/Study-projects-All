package org.oladushek.module25.rest;

import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<UserEntity> getAllEmployees() {
        return userRepository.findAll();
    }

}
