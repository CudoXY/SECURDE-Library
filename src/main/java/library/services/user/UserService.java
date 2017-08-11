package library.services.user;


import library.domain.User;

import java.util.Optional;

public interface UserService
{
    User getUserByEmail(String email);

    User getUserByIdNumber(int idNumber);

    Iterable<User> getAllUsers();

    User save(User form);

    void deleteByIdNumber(int idNumber);
    // Staff
    User setUserLock(User user, boolean isLocked);
}
