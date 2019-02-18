package cc.project.busapp.bootstrap;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.domain.Tickets;
import cc.project.busapp.repositories.CustomerRepository;
import cc.project.busapp.repositories.TicketsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;

@Component
public class Bootstrap  implements CommandLineRunner {

    private CustomerRepository customerRepository;

    private TicketsRepository ticketsRepository;

    public Bootstrap(CustomerRepository customerRepository, TicketsRepository ticketsRepository) {
        this.customerRepository = customerRepository;
        this.ticketsRepository = ticketsRepository;
    }

    @Override
        public void run(String... args) throws Exception {
            loadCustomer();
            loadTickets();
        }

    public void loadCustomer (){


        customerRepository.save(Customer.builder()
                            .userId(1l)
                            .name("Jhon Doe")
                            .userName("admin")
                            .email("admin@admin.com")
                            .password("1234")
                            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                            .build());

        customerRepository.save(Customer.builder()
                .userId(2l)
                .name("Pamela J. Travis")
                .userName("PamelaJT")
                .email("PamelaJTravis@gustr.com")
                .password("1234")
                .roles(Arrays.asList("ROLE_USER"))
                .build());

        customerRepository.save(Customer.builder()
                .userId(3l)
                .name("Willie D. Morrison")
                .userName("willied")
                .email("WillieDMorrison@superrito.com")
                .password("1234")
                .roles(Arrays.asList("ROLE_USER"))
                .build());

      System.out.println("**********************Users Loaded****************************"+ customerRepository.count());


    }


    public void loadTickets() {

        ticketsRepository.save(Tickets.builder()
                .ticketId(1l)
                .route("Ruta 1")
                .busDate(new Date())
                .price(2.3f)
                .quantity(10)
                .build());

        ticketsRepository.save(Tickets.builder()
                .ticketId(2l)
                .route("Ruta 2")
                .busDate(new Date())
                .price(1.3f)
                .quantity(13)
                .build());

        ticketsRepository.save(Tickets.builder()
                .ticketId(3l)
                .route("Ruta 3")
                .busDate(new Date())
                .price(3.3f)
                .quantity(15)
                .build());

        ticketsRepository.save(Tickets.builder()
                .ticketId(4l)
                .route("Ruta 4")
                .busDate(new Date())
                .price(3.3f)
                .quantity(5)
                .build());

        ticketsRepository.save(Tickets.builder()
                .ticketId(5l)
                .route("Ruta 5")
                .busDate(new Date())
                .price(1f)
                .quantity(10)
                .build());
        System.out.println("**********************Tickets Loaded****************************"+ ticketsRepository.count());

    }
}

