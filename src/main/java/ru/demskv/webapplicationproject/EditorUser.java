package ru.demskv.webapplicationproject;


import java.time.LocalDate;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="editoruser")
public class EditorUser implements Serializable {
    
    @Id
    @Column(name="login")
    private String login;
    
    @Column(name="password")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public EditorUser(String login, String password, LocalDate created) {
        this.login = login;
        this.password = password;
    }

    protected EditorUser() {
    }
    
    
}