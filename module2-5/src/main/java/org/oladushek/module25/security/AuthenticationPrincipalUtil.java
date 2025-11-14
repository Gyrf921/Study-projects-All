package org.oladushek.module25.security;

import org.oladushek.module25.security.obj.CustomPrincipal;
import org.springframework.security.core.Authentication;

public class AuthenticationPrincipalUtil {
    public static CustomPrincipal getCustomPrincipal(Authentication authentication) {
        return (CustomPrincipal) authentication.getPrincipal();
    }

    public static Long getId(Authentication authentication) {
        return ((CustomPrincipal) authentication.getPrincipal()).getId();
    }
}
