package cc.project.busapp.controllers;

import cc.project.busapp.domain.User;
import cc.project.busapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {


    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }


    @Test
    public void getAllCustomers() throws Exception {

        User user1 = new User(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        User user2 = new User(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        User user3 = new User(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2, user3));

        mockMvc.perform(get(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Jhon Doe")));
    }


    @Test
    public void getUserById() throws Exception {
         User user1 = new User();
        user1.setName("Usuario Creado");
        user1.setName("Jhon Does2");
        user1.setEmail("usCreado@correo.com");

         when(userService.getUserById(anyLong())).thenReturn(user1);

        mockMvc.perform(get(UserController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Jhon Does2")));

    }

    @Test
    public void createUser() throws  Exception{
        User user1 = new User();
        user1.setName("Usuario Creado");
        user1.setUserName("JhonDoes2");
        user1.setEmail("usuario@correo.com");
        when(userService.createUser(user1)).thenReturn(user1);

        mockMvc.perform(post(UserController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user1)))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateUser() throws Exception {
        User user1 = new User();
        user1.setName("Usuario Creado");
        user1.setUserName("JhonDoes2");
        user1.setEmail("usuario@correo.com");

        when(userService.updateUser(anyLong(), user1)).thenReturn(user1);

        mockMvc.perform(put(UserController.BASE_URL +"/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user1)))
        .andExpect(status().isOk());

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(UserController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).delete(anyLong());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}