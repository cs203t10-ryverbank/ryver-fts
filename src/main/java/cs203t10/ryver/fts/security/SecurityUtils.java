package cs203t10.ryver.fts.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() { }

    public static String getCurrentSessionJWT() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new GetJWTException("Current context is not authenticated.");
        }

        Object principal = auth.getPrincipal();
        if (principal == null || !(principal instanceof RyverPrincipal)) {
            throw new GetJWTException("No principal in current authentication context.");
        }

        RyverPrincipal ryverPrincipal = (RyverPrincipal) principal;
        return ryverPrincipal.jwt;
    }

}
