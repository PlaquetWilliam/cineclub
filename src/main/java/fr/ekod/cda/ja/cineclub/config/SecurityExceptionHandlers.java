package fr.ekod.cda.ja.cineclub.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Distinguishes API responses (JSON) from web pages (redirect).
 */
public final class SecurityExceptionHandlers {

    private SecurityExceptionHandlers() {
    }

    public static AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            if (isApiRequest(request)) {
                writeJson(response, HttpStatus.UNAUTHORIZED, "{\"message\":\"Authentication failed\"}");
            } else {
                redirectToLogin(request, response);
            }
        };
    }

    public static AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            if (isApiRequest(request)) {
                writeJson(response, HttpStatus.FORBIDDEN, "{\"message\":\"Access denied\"}");
            } else {
                response.sendRedirect(request.getContextPath() + "/?accessDenied");
            }
        };
    }

    private static boolean isApiRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return path.startsWith("/api/");
    }

    private static void redirectToLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = normalizedPath(request);
        if ("/login".equals(path) || "/register".equals(path)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String redirectUri = request.getRequestURI();
        String query = request.getQueryString();
        if (query != null && !query.isBlank()) {
            redirectUri = redirectUri + "?" + query;
        }
        String loginUrl = request.getContextPath()
                + "/login?redirect="
                + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        response.sendRedirect(loginUrl);
    }

    private static String normalizedPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return path;
    }

    private static void writeJson(HttpServletResponse response, HttpStatus status, String body)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
    }
}
