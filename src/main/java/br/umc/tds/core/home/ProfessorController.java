package br.umc.tds.core.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfessorController {

    @GetMapping("/professor")
    public String professor() {
        return "professor";
    }
}