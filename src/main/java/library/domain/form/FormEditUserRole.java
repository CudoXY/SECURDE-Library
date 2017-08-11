package library.domain.form;

import library.domain.Role;

/**
 * Created by Brandon on 8/11/2017.
 */
public class FormEditUserRole {
    private int id;
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
