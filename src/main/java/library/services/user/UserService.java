package library.services.user;


import library.domain.User;

import java.util.Optional;

public interface UserService
{
    User getUserByEmail(String email);

    User getUserById(int id);

    Iterable<User> getAllUsers();

    User save(User form);

    void deleteById(int id);
}
