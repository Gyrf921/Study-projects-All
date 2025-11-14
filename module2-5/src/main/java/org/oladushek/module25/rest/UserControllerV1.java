package org.oladushek.module25.rest;

import lombok.RequiredArgsConstructor;
import org.oladushek.module25.mapper.UserMapper;
import org.oladushek.module25.rest.dto.UserDto;
import org.oladushek.module25.security.AuthenticationPrincipalUtil;
import org.oladushek.module25.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public Mono<UserDto> getMyProfile(Authentication authentication) {
        return userService.getById(AuthenticationPrincipalUtil.getId(authentication))
                .map(userMapper::map);
    }

    @PutMapping("/me")
    public Mono<UserDto> updateMyProfile(Authentication authentication, @RequestBody UserDto user) {
        return userService.update(AuthenticationPrincipalUtil.getId(authentication), userMapper.map(user))
                .map(userMapper::map);
    }

    @DeleteMapping("/me")
    public Mono<Void> deleteMyProfile(Authentication authentication) {
        userService.delete(AuthenticationPrincipalUtil.getId(authentication));
        return Mono.empty();
    }


    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getById(userId)
                .map(userMapper::map);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<List<UserDto>> getAllUsers() {
        return userService.getAll()
                .map(userEntities -> userEntities.stream()
                        .map(userMapper::map)
                        .toList());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> changeUserStatusv (@PathVariable Long userId) {
        userService.delete(userId);
        return Mono.empty();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return Mono.empty();
    }

}
