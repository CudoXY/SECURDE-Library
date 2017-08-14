package library.services.user;

import library.domain.User;
import library.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email)
    {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public User getUserByIdNumber(int idNumber) {
        return userRepository.findOne(idNumber);
    }

    @Override
    public Iterable<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User setUserLock(User user, boolean isLocked)
    {
        user.setLocked(isLocked);
        return userRepository.save(user);
    }

    @Override
    public void deleteByIdNumber(int idNumber) {
        userRepository.deleteById(idNumber);
    }
}
