package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest{

    public static final long ID = 1L;
    public static final long ID1 = 2L;
    MockMvc mockMvc;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        CustomerDTO michael = new CustomerDTO();
        michael.setId(ID);
        michael.setFirstName("Michael");
        michael.setLastName("Lachappele");

        CustomerDTO david = new CustomerDTO();
        david.setId(ID1);
        david.setFirstName("David");
        david.setLastName("Winter");

        List<CustomerDTO> customers = Arrays.asList(michael, david);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO michael = new CustomerDTO();
        michael.setId(ID);
        michael.setFirstName("Michael");
        michael.setLastName("Lachappele");

        when(customerService.getCustomerById(anyLong())).thenReturn(michael);

        mockMvc.perform(get("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));

    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("Fred");
        customerDTO.setLastName("Flinstone");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setId(customerDTO.getId());
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName(customerDTO.getLastName());

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDto);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }
}