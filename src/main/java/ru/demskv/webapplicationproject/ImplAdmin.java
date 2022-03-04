package ru.demskv.webapplicationproject;

@Profile(ProfileType.ADMIN)
public class ImplAdmin implements UserProfile{
    public ProfileType type() {
        System.out.println("User is admin");
        return ProfileType.ADMIN;
    }
}