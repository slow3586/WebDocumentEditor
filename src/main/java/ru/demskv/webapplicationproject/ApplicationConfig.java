package ru.demskv.webapplicationproject;

import jakarta.ws.rs.ApplicationPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationPath("webresources")
public class ApplicationConfig extends jakarta.ws.rs.core.Application {
    private static final Logger logger = LogManager.getLogger("org.hibernate");
    private static final Logger logger2 = LogManager.getLogger("org.hibernate.SQL");
}