package org.oladushek.module25.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.entity.base.UserRole;
import org.oladushek.module25.entity.base.UserStatus;
import org.oladushek.module25.exception.UserNotFoundException;
import org.oladushek.module25.mapper.UserMapper;
import org.oladushek.module25.repository.UserRepository;
import org.oladushek.module25.rest.dto.UserDto;
import org.oladushek.module25.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** registerUser
     * */
    @Override
    public Mono<UserEntity> create(UserEntity user) {
        return Mono.fromCallable(() -> userRepository.save(
                        user.toBuilder()
                                .username(user.getUsername())
                                .password(passwordEncoder.encode(user.getPassword()))
                                .role(UserRole.USER)
                                .userStatus(UserStatus.ACTIVE)
                                .build()
                ))
                .doOnSuccess(u -> {
                    log.info("IN registerUser - user: {} created", u);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


    @Override
    public Mono<UserEntity> getById(Long id) {
        return Mono.fromCallable(() -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' not found")))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UserEntity> getUserByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsername(username))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<List<UserEntity>> getAll() {
        return Mono.fromCallable(userRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<UserEntity> update(Long aLong, UserEntity entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }



}
