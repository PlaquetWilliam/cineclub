package fr.ekod.cda.ja.cineclub.config;

import fr.ekod.cda.ja.cineclub.controller.AuthWebController;
import fr.ekod.cda.ja.cineclub.controller.MovieWebController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {MovieWebController.class, AuthWebController.class})
public class WebControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(
            HttpServletRequest request,
            Authentication authentication,
            Model model
    ) {
        boolean authenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", authenticated);
        model.addAttribute("isAdmin", hasRole(authentication, "ROLE_ADMIN"));

        if (authenticated) {
            model.addAttribute("currentUserEmail", authentication.getName());
        }

        String path = requestPath(request);
        model.addAttribute("navHome", "/".equals(path));
        model.addAttribute("navMovies", path.startsWith("/movies"));
        model.addAttribute("navDirectors", path.startsWith("/directors"));
    }

    private static String requestPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            return uri.substring(contextPath.length());
        }
        return uri;
    }

    private static boolean hasRole(Authentication authentication, String role) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role::equals);
    }
}
