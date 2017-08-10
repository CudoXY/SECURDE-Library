package library.services.user;


import library.domain.User;

import java.util.Optional;

public interface UserService
{
    User getUserByEmail(String email);

    User getUserByIdNumber(int idNumber);

    Iterable<User> getAllUsers();

    User save(User form);

    // Staff
    User setUserLock(User user, boolean isLocked);
}
