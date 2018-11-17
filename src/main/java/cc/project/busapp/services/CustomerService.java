package cc.project.busapp.services;

import cc.project.busapp.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(long id);

    Customer createXustomer(Customer customer);

    Customer updateCustomer(long id, Customer customer);

    void delete(long id);
}
