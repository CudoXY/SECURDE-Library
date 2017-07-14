package library.services;


import library.domain.User;

public interface UserService
{
    Iterable<User> listAllUsers();

    User getUserById(int id);

    User addUser(User user);

    // Staff
    User setUserLock(User user, boolean isLocked);
}
