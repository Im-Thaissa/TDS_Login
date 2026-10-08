package br.umc.tds.core.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final Set<Role> ROLES_PERMITIDAS_NO_CADASTRO = Set.of(Role.ALUNO, Role.PROFESSOR, Role.COORDENADOR);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User cadastrar(String email, String senhaCrua, String nome, String roleStr) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com esse e-mail.");
        }

        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Perfil inválido.");
        }

        if (!ROLES_PERMITIDAS_NO_CADASTRO.contains(role)) {
            throw new IllegalArgumentException("Esse perfil não pode ser escolhido no cadastro.");
        }

        String senhaCriptografada = passwordEncoder.encode(senhaCrua);
        User user = new User(email, senhaCriptografada, nome, role);
        user.setAprovado(false);
        return userRepository.save(user);
    }

    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> listarPendentesPorRole(Role role) {
        return userRepository.findByRolesContainingAndAprovadoFalse(role);
    }

    public void aprovar(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        user.setAprovado(true);
        userRepository.save(user);
    }

    public void rejeitar(String id) {
        userRepository.deleteById(id);
    }
}