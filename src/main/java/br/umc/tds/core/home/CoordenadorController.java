package br.umc.tds.core.home;

import br.umc.tds.core.user.Role;
import br.umc.tds.core.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CoordenadorController {

    private final UserService userService;

    public CoordenadorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/coordenador")
    public String coordenador() {
        return "coordenador";
    }

    @GetMapping("/coordenador/aprovacoes")
    public String aprovacoes(Model model) {
        model.addAttribute("alunos", userService.listarPendentesPorRole(Role.ALUNO));
        model.addAttribute("professores", userService.listarPendentesPorRole(Role.PROFESSOR));
        return "coordenador-aprovacoes";
    }

    @PostMapping("/coordenador/aprovacoes/{id}/aprovar")
    public String aprovar(@PathVariable String id) {
        userService.aprovar(id);
        return "redirect:/coordenador/aprovacoes";
    }

    @PostMapping("/coordenador/aprovacoes/{id}/rejeitar")
    public String rejeitar(@PathVariable String id) {
        userService.rejeitar(id);
        return "redirect:/coordenador/aprovacoes";
    }
}