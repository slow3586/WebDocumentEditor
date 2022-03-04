package ru.demskv.webapplicationproject;

import jakarta.enterprise.inject.Default;


@Profile(ProfileType.OPERATOR)
@Default
public class ImplOperator implements UserProfile{
    public ProfileType type() {
        System.out.println("User is operator");
        return ProfileType.OPERATOR;
    }
}
