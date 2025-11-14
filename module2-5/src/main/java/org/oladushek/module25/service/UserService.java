package org.oladushek.module25.service;

import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.mapper.UserMapper;
import org.oladushek.module25.rest.dto.UserDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface UserService extends GenericService<UserEntity, Long>{
    Mono<UserEntity> getUserByUsername(String username);
}
