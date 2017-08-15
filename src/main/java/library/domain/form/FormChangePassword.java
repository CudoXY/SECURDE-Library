package library.domain.form;

/**
 * Created by Brandon on 8/14/2017.
 */
public class FormChangePassword {
    public String origPassword, password, passwordRepeat;
    public String getOrigPassword() {
        return origPassword;
    }

    public void setOrigPassword(String origPassword) {
        this.origPassword = origPassword;
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
}
