package cc.project.busapp.controllers;


import cc.project.busapp.domain.User;
import cc.project.busapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/user";

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllCustomers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @PathVariable long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long id){
        userService.delete(id);
    }

}
