package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final Logger logger = Logger.getLogger(LoginService.class);

    private final UserRepository<LoginForm> loginForms;

    @Autowired
    public LoginService(UserRepository<LoginForm> loginForm) {
        this.loginForms = loginForm;
    }

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        return loginForms.contains(loginFrom);
    }

    public void registration(LoginForm loginFrom) {
        logger.info("try registration with user-form: " + loginFrom);
        loginForms.store(loginFrom);
    }

}
