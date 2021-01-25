package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class LoginRepository implements UserRepository<LoginForm> {

    private final Logger logger = Logger.getLogger(LoginRepository.class);

    private final Set<LoginForm> repo = new HashSet<>();

    {
        repo.add(new LoginForm("root", "123"));
    }

    @Override
    public void store(LoginForm user) {
        logger.info("New user: " + user);
        repo.add(user);
    }

    @Override
    public boolean contains(LoginForm item) {
        return repo.contains(item);
    }
}
