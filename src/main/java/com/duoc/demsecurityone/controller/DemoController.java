package com.duoc.demsecurityone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/publico")
    public String publico() {
        return "Información pública de Clínica Petsalud";
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "API de demostración de Clínica Petsalud";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "Perfil de usuario autenticado. En producción, devolver solo datos del usuario actual.";
    }

    @GetMapping("/empleados")
    public String empleados() {
        return "Sección interna para empleados y administradores.";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Sección exclusiva de administradores.";
    }
}
