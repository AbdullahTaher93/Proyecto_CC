package cc.project.busapp.controllers;

import cc.project.busapp.domain.Purchase;
import cc.project.busapp.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PurchaseController.BASE_URL)
public class PurchaseController {

    public static final String BASE_URL = "compras";

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/{userId}/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase purchaseTicket(@PathVariable long userId, @PathVariable long ticketId){

        System.out.println("Parametro ususario Id" + userId);
        System.out.println("Parametro ticket ID" + ticketId);

        return purchaseService.purchaseTicket(userId, ticketId);
    }


    @GetMapping
    public List<Purchase> getAllPurchases(){
        return this.purchaseService.getAllPurchases();
    }

}
