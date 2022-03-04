package ru.demskv.webapplicationproject;

import jakarta.annotation.Priority;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.interceptor.Interceptor;

public class OrderedObserver {
public static void main(String[] args){
    try(SeContainer container =
            SeContainerInitializer.newInstance().initialize()){
        container
        .getBeanManager()
        .fireEvent(new MyEvent("event: " +
        System.currentTimeMillis()));
    }
}
public void thisEventBefore(@Observes @Priority(Interceptor.Priority.APPLICATION - 200) MyEvent event){
    System.out.println("thisEventBefore: " + event.getValue());
}
public void thisEventAfter(@Observes @Priority(Interceptor.Priority.APPLICATION - 200) MyEvent event){
    System.out.println("thisEventAfter: " + event.getValue());
    }
}