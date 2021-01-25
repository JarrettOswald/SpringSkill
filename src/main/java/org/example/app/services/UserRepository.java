package org.example.app.services;

import java.util.List;

public interface UserRepository<T> {
    void store(T user);
    boolean contains(T user);
}
