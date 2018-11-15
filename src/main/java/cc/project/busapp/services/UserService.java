package cc.project.busapp.services;

import cc.project.busapp.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);
}
