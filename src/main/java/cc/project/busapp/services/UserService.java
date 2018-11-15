package cc.project.busapp.services;

import cc.project.busapp.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(long id);
}
