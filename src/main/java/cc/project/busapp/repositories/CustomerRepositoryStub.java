package cc.project.busapp.repositories;

import cc.project.busapp.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerRepositoryStub {

    private static Map<Long, Customer> users = new HashMap<Long, Customer>();
    private static Long userIndex = 4l;
/*
    static {
        Customer customer1 = new Customer(1l, "Jhon Doe", "jhonDoe", "jhonDoe@mail.com");
        Customer customer2 = new Customer(2l, "Pamela J. Travis", "PamelaJT", "PamelaJTravis@gustr.com");
        Customer customer3 = new Customer(3l, "Willie D. Morrison", "WillieD", "WillieDMorrison@superrito.com");
        Customer customer4 = new Customer(4l, "Homer F. Martin", "HomerF", "HomerFMartin@superrito.com");

        users.put(1l, customer1);
        users.put(2l, customer2);
        users.put(3l, customer3);
        users.put(4l, customer4);

    }

*/
    public List<Customer> getAll() {
        return new ArrayList<Customer>(users.values());
    }


    public Customer getById(long id) {
        return users.get(id);
    }
}
