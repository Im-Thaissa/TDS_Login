package br.umc.bookrats.core.auth;

import br.umc.bookrats.core.user.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/cadastro")
    public String formularioCadastro(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@Valid @ModelAttribute RegisterForm registerForm,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/cadastro";
        }

        try {
            userService.cadastrar(registerForm.getEmail(), registerForm.getSenha(), registerForm.getNome());
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "auth/cadastro";
        }

        return "redirect:/login?cadastroSucesso";
    }

    @GetMapping("/login")
    public String formularioLogin() {
        return "auth/login";
    }
}