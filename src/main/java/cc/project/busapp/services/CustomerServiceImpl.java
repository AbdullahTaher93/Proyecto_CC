package cc.project.busapp.services;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.errors.ResourceNotFoundException;
import cc.project.busapp.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{


    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuario con id :" + id + " no encontrado"));
    }


    @Override
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(long id, Customer customer) {
        Customer findCustomer =  getCustomerById(id);

        customer.setUserId(findCustomer.getUserId());

        return customerRepository.save(customer);

    }

    @Override
    public void delete(long id) {
        Customer findCustomer =  getCustomerById(id);
        customerRepository.delete(findCustomer);
    }

    @Override
    public Customer getCustomerByName(String username) {
        return this.customerRepository.findByUserName(username).orElseThrow(() -> new ResourceNotFoundException("Usuario con nombre :" + username + " encontrado"));
    }

}
