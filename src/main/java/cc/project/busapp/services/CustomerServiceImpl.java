package cc.project.busapp.services;

import cc.project.busapp.domain.Customer;
import cc.project.busapp.errors.ResourceNotFoundException;
import cc.project.busapp.repositories.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {


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
        return customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuario con id :" + id + " encontrado"));
    }


    @Override
    public Customer createXustomer(Customer customer){
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByUserName(username).orElseThrow(() -> new ResourceNotFoundException("Usuario con nombre :" + username + " not encontrado"));
    }
}
