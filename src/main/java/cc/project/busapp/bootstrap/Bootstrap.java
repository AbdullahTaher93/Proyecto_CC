package cc.project.busapp.bootstrap;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class Bootstrap  implements CommandLineRunner {

    private CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Bootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
        public void run(String... args) throws Exception {
            loadCustomer();
        }

    public void loadCustomer (){

    /*
        Customer customer1 = new Customer(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        Customer customer2 = new Customer(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        Customer customer3 = new Customer(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito.com");
        Customer customer4 = new Customer(4l, "Homer F. Martin", "HomerF", "HomerFMartin@superrito.com");

        Customer customer1 = new Customer(1l, "eswe", "dasda", "ssadsd@gmail.com", "");
*/
        customerRepository.save(Customer.builder()
                            .userId(1l)
                            .name("Jhon Doe")
                            .userName("admin")
                            .email("admin@admin.com")
                            .password(this.passwordEncoder.encode("1234"))
                            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                            .build());

        customerRepository.save(Customer.builder()
                .userId(2l)
                .name("Pamela J. Travis")
                .userName("PamelaJT")
                .email("PamelaJTravis@gustr.com")
                .password(this.passwordEncoder.encode("1234"))
                .roles(Arrays.asList("ROLE_USER"))
                .build());

        customerRepository.save(Customer.builder()
                .userId(3l)
                .name("Willie D. Morrison")
                .userName("willied")
                .email("WillieDMorrison@superrito.com")
                .password(this.passwordEncoder.encode("1234"))
                .roles(Arrays.asList("ROLE_USER"))
                .build());


/*
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
*/
        System.out.println("**********************Users Loaded****************************"+ customerRepository.count());


    }
}

