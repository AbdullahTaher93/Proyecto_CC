package cc.project.busapp.bootstrap;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap  implements CommandLineRunner {

    private CustomerRepository customerRepository;

    public Bootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
        public void run(String... args) throws Exception {
            loadCustomer();
        }

    public void loadCustomer (){

        Customer customer1 = new Customer(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        Customer customer2 = new Customer(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        Customer customer3 = new Customer(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito.com");
        Customer customer4 = new Customer(4l, "Homer F. Martin", "HomerF", "HomerFMartin@superrito.com");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);

        System.out.println("**********************Users Loaded****************************"+ customerRepository.count());


    }
}

