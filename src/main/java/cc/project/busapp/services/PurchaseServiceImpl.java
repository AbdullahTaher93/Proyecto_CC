package cc.project.busapp.services;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.domain.Purchase;
import cc.project.busapp.domain.Tickets;
import cc.project.busapp.errors.ErrorQuantityPurchase;
import cc.project.busapp.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final CustomerService customerService;

    private final TicketService ticketService;

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(CustomerService customerService, TicketService ticketService, PurchaseRepository purchaseRepository) {
        this.customerService = customerService;
        this.ticketService = ticketService;
        this.purchaseRepository = purchaseRepository;
    }


    @Override
    public Purchase purchaseTicket(long userId, long ticketId, int quantity) {

        Customer buyer = customerService.getCustomerById(userId);

        System.out.println(buyer.toString());

        Tickets ticket = ticketService.getTicketById(ticketId);


        System.out.println(ticket.toString());

        if (quantity < ticket.getQuantity()){
            Purchase purchase = new Purchase().builder()
                    .customerId(buyer.getUserId())
                    .CustomerName(buyer.getName())
                    .pricePerTicket(ticket.getPrice())
                    .quantity(ticket.getQuantity())
                    .route(ticket.getRoute())
                    .totalPrice((ticket.getPrice() * ticket.getQuantity()))
                    .build();

            ticket.setQuantity(ticket.getQuantity() - quantity);

            ticketService.updateTicket(ticketId, ticket);

            System.out.println(purchase.toString());
            purchaseRepository.save(purchase);

            return purchase;

        }else {
            throw  new ErrorQuantityPurchase("Cantidad de tiquetes que desa comprar es  mayor a los disponibles por el momento.");
        }


    }

    @Override
    public List<Purchase> getAllPurchases() {
        return this.purchaseRepository.findAll();
    }
}
