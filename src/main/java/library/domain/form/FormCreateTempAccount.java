package library.domain.form;

import library.domain.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Brandon on 8/10/2017.
 */

public class FormCreateTempAccount {
    private String username;
    private String password;

    private String passwordRepeat;

    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
