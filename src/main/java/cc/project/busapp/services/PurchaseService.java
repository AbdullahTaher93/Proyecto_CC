package cc.project.busapp.services;

import cc.project.busapp.domain.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PurchaseService {

    Purchase purchaseTicket(long userId, long ticketId);

    List<Purchase> getAllPurchases();
}
