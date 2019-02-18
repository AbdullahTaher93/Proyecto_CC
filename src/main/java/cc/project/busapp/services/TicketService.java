package cc.project.busapp.services;

import cc.project.busapp.domain.Tickets;

import java.util.List;

public interface TicketService {

    List<Tickets> getAllTickets();

    Tickets getTicketById(long ticketId);

    Tickets updateTicket(long tickedId, Tickets updateTicked);
}
