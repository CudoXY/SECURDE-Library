package library.services.currentuser;

import library.domain.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, int userId);
    void autologin(String username, String password);

}
