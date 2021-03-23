package guru.springframework.controllers;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest extends AbstractRestControllerTest {
    public static final String VENDOR = "Vendor";
    MockMvc mockMvc;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    VendorDTO vendorDTO;
    VendorDTO returnDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName(VENDOR);


        returnDTO = new VendorDTO();
        returnDTO.setId(vendorDTO.getId());
        returnDTO.setName(vendorDTO.getName());
    }

    @Test
    void getAllVendors() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(vendorDTO, returnDTO);

        Mockito.when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorsById() throws Exception {
        Mockito.when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(VENDOR)));
    }

    @Test
    void createNewVendor() throws Exception {

        Mockito.when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(VENDOR)))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    void updateVendor() throws Exception {
        Mockito.when(vendorService.saveVendorByDTO(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(VENDOR)))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    void patchVendor() throws Exception {
        Mockito.when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(VENDOR)))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    void deleteVendor() throws Exception {

        mockMvc.perform(delete("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(vendorService).deleteVendorById(anyLong());
    }
}