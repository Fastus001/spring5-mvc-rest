package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String NAME = "Tomek";
    public static final String LAST_NAME = "Kowalski";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(NAME);
        customer.setLastName(LAST_NAME);

        final CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertAll(
                ()->assertEquals(ID, customerDTO.getId()),
                ()->assertEquals(NAME, customerDTO.getFirstName()),
                ()->assertEquals(LAST_NAME, customerDTO.getLastName())
        );
    }
}