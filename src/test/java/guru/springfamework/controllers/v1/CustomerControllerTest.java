package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest{

    public static final String API_V_1_CUSTOMERS_1 = "/api/v1/customers/1";
    public static final String API_V_1_CUSTOMERS = "/api/v1/customers/";
    public static final String FRED = "Fred";
    public static final String FLINSTONE = "Flinstone";
    MockMvc mockMvc;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    CustomerDTO customer;
    CustomerDTO returnDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstName("Fred");
        customer.setLastName("Flinstone");

        returnDto = new CustomerDTO();
        returnDto.setId(customer.getId());
        returnDto.setFirstName(customer.getFirstName());
        returnDto.setLastName(customer.getLastName());
    }

    @Test
    void getAllCustomers() throws Exception {

        List<CustomerDTO> customers = Arrays.asList(customer, returnDto);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(API_V_1_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get(API_V_1_CUSTOMERS_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void createNewCustomer() throws Exception {

        when(customerService.createNewCustomer(customer)).thenReturn(returnDto);

        mockMvc.perform(post(API_V_1_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FRED)))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    void testUpdateCustomer() throws Exception {

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(put(API_V_1_CUSTOMERS_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FRED)))
                .andExpect(jsonPath("$.lastname", equalTo(FLINSTONE)))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    void testPatchCustomer() throws Exception {

        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(API_V_1_CUSTOMERS_1)
                 .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FRED)))
                .andExpect(jsonPath("$.lastname", equalTo(FLINSTONE)))
                .andExpect(jsonPath("$.customer_url", equalTo(API_V_1_CUSTOMERS_1)));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(API_V_1_CUSTOMERS_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL+"/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}