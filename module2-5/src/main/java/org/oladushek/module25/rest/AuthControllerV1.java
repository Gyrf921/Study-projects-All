package org.oladushek.module25.rest;

import lombok.RequiredArgsConstructor;
import org.oladushek.module25.entity.UserEntity;
import org.oladushek.module25.mapper.UserMapper;
import org.oladushek.module25.rest.dto.AuthRequestDto;
import org.oladushek.module25.rest.dto.AuthResponseDto;
import org.oladushek.module25.rest.dto.UserDto;
import org.oladushek.module25.security.obj.CustomPrincipal;
import org.oladushek.module25.security.SecurityService;
import org.oladushek.module25.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.create(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.username(), dto.password())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

}
