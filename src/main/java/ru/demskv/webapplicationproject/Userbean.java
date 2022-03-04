
package ru.demskv.webapplicationproject;

import java.time.LocalDate;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Named
public class Userbean {
    
    @NotBlank (message = "Name should not be blank")
    @Size (min = 4, max = 10,message = "Name should be between 4 and 10 characters")
    private String name;
    
    @Email (message = "Invalid e-mail format")
    @NotBlank (message = "E-mail shoud not be blank")
    private String email;
    
    @PastOrPresent (message = "Created date should be past or present")
    @NotNull (message = "Create date should not be null")
    private LocalDate created;
    
    @Future (message = "Expires should be a future date")
    @NotNull (message = "Expires should not be null")
    private LocalDate expires;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getExpires() {
        return expires;
    }

    public void setExpires(LocalDate expires) {
        this.expires = expires;
    }

    public void valid(){
        FacesContext
                .getCurrentInstance()
                .addMessage(
                    null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO,"Your data is valid", ""));
    }
}
