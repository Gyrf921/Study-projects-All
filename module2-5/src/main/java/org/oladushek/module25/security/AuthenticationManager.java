package org.oladushek.module25.security;

import lombok.RequiredArgsConstructor;
import org.oladushek.module25.entity.base.UserStatus;
import org.oladushek.module25.exception.UnauthorizedException;
import org.oladushek.module25.security.obj.CustomPrincipal;
import org.oladushek.module25.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getById(principal.getId())
                .filter(user -> UserStatus.ACTIVE.equals(user.getUserStatus()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("User disabled")))
                .map(user -> authentication);
    }
}
