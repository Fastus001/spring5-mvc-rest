package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Tom";
    public static final String LAST_NAME = "Kowlaski";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerService= new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        final List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getById() {
        Customer customerOptional = new Customer();
        customerOptional.setId(ID);
        customerOptional.setFirstName(NAME);
        customerOptional.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customerOptional));

        final CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertAll(()->assertEquals(ID, customerDTO.getId()),
                ()->assertEquals(NAME, customerDTO.getFirstName()),
                ()->assertEquals(LAST_NAME, customerDTO.getLastName()));
    }
}