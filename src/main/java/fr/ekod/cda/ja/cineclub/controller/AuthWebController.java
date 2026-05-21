package fr.ekod.cda.ja.cineclub.controller;

import fr.ekod.cda.ja.cineclub.dto.auth.LoginRequestDTO;
import fr.ekod.cda.ja.cineclub.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.cineclub.exception.EmailAlreadyUsedException;
import fr.ekod.cda.ja.cineclub.security.CustomUserDetailsService;
import fr.ekod.cda.ja.cineclub.security.JwtService;
import fr.ekod.cda.ja.cineclub.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthWebController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", emptyRegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerRequest") RegisterRequestDTO registerRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.register(registerRequest);
            return "redirect:/login?registered";
        } catch (EmailAlreadyUsedException ex) {
            model.addAttribute("error", "Cet email est déjà utilisé.");
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String loginForm(
            @RequestParam(required = false) String registered,
            Model model
    ) {
        model.addAttribute("loginRequest", emptyLoginRequest());
        if (registered != null) {
            model.addAttribute("success", "Inscription réussie. Connectez-vous avec votre compte.");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginRequest") LoginRequestDTO loginRequest,
            BindingResult bindingResult,
            HttpServletResponse response,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            UserDetails user = userDetailsService.loadUserByUsername(loginRequest.email());
            String accessToken = jwtService.generateAccessToken(user);
            addAccessTokenCookie(response, accessToken);

            return "redirect:/";
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
            model.addAttribute("loginRequest", loginRequest);
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        clearAccessTokenCookie(response);
        return "redirect:/";
    }

    private void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) jwtService.getAccessExpirationSeconds());
        response.addCookie(cookie);
    }

    private void clearAccessTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("ACCESS_TOKEN", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static RegisterRequestDTO emptyRegisterRequest() {
        return new RegisterRequestDTO("", "", "", "");
    }

    private static LoginRequestDTO emptyLoginRequest() {
        return new LoginRequestDTO("", "");
    }
}
