package fr.ekod.cda.ja.cineclub.service;

import fr.ekod.cda.ja.cineclub.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.cineclub.dto.auth.UserDTO;
import fr.ekod.cda.ja.cineclub.entity.Role;
import fr.ekod.cda.ja.cineclub.entity.User;
import fr.ekod.cda.ja.cineclub.exception.EmailAlreadyUsedException;
import fr.ekod.cda.ja.cineclub.mapper.UserMapper;
import fr.ekod.cda.ja.cineclub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException(dto.email());
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER); // role is forced server-side

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in DB"));
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
