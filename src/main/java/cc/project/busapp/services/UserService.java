package cc.project.busapp.services;

import cc.project.busapp.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    User createUser(User user);

    User updateUser(long id, User user);

    void delete(long id);
}
