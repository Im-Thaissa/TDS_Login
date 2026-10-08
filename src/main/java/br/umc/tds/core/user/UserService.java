package br.umc.bookrats.core.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User cadastrar(String email, String senhaCrua, String nome) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com esse e-mail.");
        }

        String senhaCriptografada = passwordEncoder.encode(senhaCrua);
        User user = new User(email, senhaCriptografada, nome);
        return userRepository.save(user);
    }

    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
}