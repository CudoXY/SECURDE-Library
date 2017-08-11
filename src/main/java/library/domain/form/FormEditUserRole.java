package library.domain.form;

import library.domain.Role;

/**
 * Created by Brandon on 8/11/2017.
 */
public class FormEditUserRole {
    private int idNumber;
    private Role role;

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
