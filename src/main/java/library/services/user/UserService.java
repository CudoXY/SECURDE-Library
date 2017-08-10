package library.services.user;


import library.domain.User;

import java.util.Optional;

public interface UserService
{
    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByIdNumber(int idNumber);

    Iterable<User> getAllUsers();

    User save(User form);

    // Staff
    User setUserLock(User user, boolean isLocked);
}
