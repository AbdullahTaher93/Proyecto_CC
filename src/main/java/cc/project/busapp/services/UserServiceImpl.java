package cc.project.busapp.services;

import cc.project.busapp.domain.User;
import cc.project.busapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User updateUser(long id, User user) {
        User findUser =  getUserById(id);

        user.setUserId(findUser.getUserId());

        return userRepository.save(user);

    }

    @Override
    public void delete(long id) {
        User findUser =  getUserById(id);
        userRepository.delete(findUser);
    }

}
