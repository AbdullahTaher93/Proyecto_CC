package cc.project.busapp.services;

import cc.project.busapp.domain.Tickets;
import cc.project.busapp.errors.ResourceNotFoundException;
import cc.project.busapp.repositories.TicketsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {


    private static TicketsRepository ticketsRepository;

    public TicketServiceImpl(TicketsRepository ticketsRepository){
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }

    @Override
    public Tickets getTicketById(long ticketId) {
        return ticketsRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Tickete numero "+ ticketId +" no disponible "));
    }

    @Override
    public Tickets updateTicket(long tickedId, Tickets updateTicked) {
        Tickets findTicked = getTicketById(tickedId);

        updateTicked.setTicketId(findTicked.getTicketId());

        return  ticketsRepository.save(updateTicked);
    }

}


