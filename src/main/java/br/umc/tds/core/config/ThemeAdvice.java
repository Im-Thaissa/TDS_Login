package br.umc.tds.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ThemeAdvice {

    @Value("${app.theme}")
    private String theme;

    @ModelAttribute("theme")
    public String theme() {
        return theme;
    }
}