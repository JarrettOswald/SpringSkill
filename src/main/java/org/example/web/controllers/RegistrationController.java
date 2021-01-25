package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {


    Logger logger = Logger.getLogger(RegistrationController.class);

    private final LoginService loginService;

    @Autowired
    public RegistrationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String registration(Model model) {
        logger.info("GET /registration returns registration_page");
        model.addAttribute("loginForm", new LoginForm());
        return "registration_page";
    }

    @PostMapping("/auth")
    public String registrationNewPerson(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("LoginForm validation exception");
            model.addAttribute("errorMessage", "Login min 6 characters. Password min 3 characters");
            return "registration_page";
        } else {
            logger.info("GET /registration returns login_page");
            loginService.registration(loginForm);
            return "login_page";
        }
    }

}
