package ru.demskv.webapplicationproject;

import jakarta.enterprise.inject.Produces;

import java.io.Serializable;
import java.time.LocalDate;

public class UserFactory implements Serializable {
    @Produces
    public User getUser() {
        return new User("Elder Moraes", "elder@eldermoraes.com", LocalDate.now());
    }
}
