package cc.project.busapp.controllers;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.services.CustomerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {


    @Mock
    CustomerService userService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

/*
    @Test
    public void getAllCustomers() throws Exception {

        Customer customer1 = new Customer(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        Customer customer2 = new Customer(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        Customer customer3 = new Customer(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito");

        when(userService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2, customer3));

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Jhon Doe")));
    }


    @Test
    public void getUserById() throws Exception {
         Customer customer1 = new Customer(1l,"Cliente Creado","Jhon Does2", "usCreado@correo.com");

         when(userService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get(CustomerController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Cliente Creado")));

    }


    @Test
    public void createUser() throws  Exception{
        Customer customer1 = new Customer();
        customer1.setName("Customer created");
        customer1.setUserName("JhonDoes2");
        customer1.setEmail("usuario@correo.com");
        when(userService.createXustomer(customer1)).thenReturn(customer1);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer1)))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateUser() throws Exception {
        Customer customer1 = new Customer();
        customer1.setName("Cliente Creado");
        customer1.setUserName("JhonDoes2");
        customer1.setEmail("usuario@correo.com");

        when(userService.updateCustomer(anyLong(), any(Customer.class))).thenReturn(customer1);

        mockMvc.perform(put(CustomerController.BASE_URL +"/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer1)))
        .andExpect(status().isOk());

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL +"/1")
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
    */
}