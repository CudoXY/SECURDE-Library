package library.domain.form;

/**
 * Created by Brandon on 8/14/2017.
 */
public class FormChangePassword {
    public String origPassword, newPassword, repNewPassword;
    public int id;
    public String getOrigPassword() {
        return origPassword;
    }

    public void setOrigPassword(String origPassword) {
        this.origPassword = origPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepNewPassword() {
        return repNewPassword;
    }

    public void setRepNewPassword(String repNewPassword) {
        this.repNewPassword = repNewPassword;
    }
}
