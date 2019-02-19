package cc.project.busapp.controllers;

import cc.project.busapp.domain.Tickets;
import cc.project.busapp.services.TicketService;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketsControllerTest {

    @Mock
    TicketService ticketService;

    @InjectMocks
    TicketsController ticketsController;

    MockMvc mockMvc;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketsController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }


    @Test
    public void getAllTickets() throws Exception {

        Tickets tickets1 = Tickets.builder()
                .ticketId(1l)
                .route("Ruta 1")
                .busDate(new Date())
                .price(2.3f)
                .quantity(10)
                .build();

        Tickets tickets2 = Tickets.builder()
                .ticketId(2l)
                .route("Ruta 2")
                .busDate(new Date())
                .price(1.3f)
                .quantity(13)
                .build();

        Tickets tickets3 = Tickets.builder()
                .ticketId(3l)
                .route("Ruta 3")
                .busDate(new Date())
                .price(3.3f)
                .quantity(15)
                .build();

        when(ticketService.getAllTickets()).thenReturn(Arrays.asList(tickets1, tickets1, tickets3));

        mockMvc.perform(get(TicketsController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].route", equalTo("Ruta 1")));
    }

    @Test
    public void getTicketByID() throws Exception {

        Tickets tickets1 = Tickets.builder()
                .ticketId(1l)
                .route("Ruta 1")
                .busDate(new Date())
                .price(2.3f)
                .quantity(10)
                .build();

        when(ticketService.getTicketById(anyLong())).thenReturn(tickets1);

        mockMvc.perform(get(TicketsController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.route", CoreMatchers.equalTo("Ruta 1")));
    }
}