package library.services.user;


import library.domain.User;

import java.util.Optional;

public interface UserService
{
    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    Iterable<User> getAllUsers();

    User save(User form);

    // Staff
    User setUserLock(User user, boolean isLocked);
}
