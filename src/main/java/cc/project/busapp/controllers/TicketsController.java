package cc.project.busapp.controllers;

import cc.project.busapp.domain.Tickets;
import cc.project.busapp.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TicketsController.BASE_URL)
public class TicketsController {
    public static final String BASE_URL = "/tickets";


    private final TicketService ticketService;

    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tickets> getAllTickets(){
        return ticketService.getAllTickets();
    }


}
