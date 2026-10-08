package br.umc.tds.core.home;

import br.umc.tds.core.user.Role;
import br.umc.tds.core.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/aprovacoes")
    public String aprovacoes(Model model) {
        model.addAttribute("coordenadores", userService.listarPendentesPorRole(Role.COORDENADOR));
        return "admin-aprovacoes";
    }

    @PostMapping("/admin/aprovacoes/{id}/aprovar")
    public String aprovar(@PathVariable String id) {
        userService.aprovar(id);
        return "redirect:/admin/aprovacoes";
    }

    @PostMapping("/admin/aprovacoes/{id}/rejeitar")
    public String rejeitar(@PathVariable String id) {
        userService.rejeitar(id);
        return "redirect:/admin/aprovacoes";
    }
}