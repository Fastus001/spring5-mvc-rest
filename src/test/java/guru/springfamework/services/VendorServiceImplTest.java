package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class VendorServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Name";
    public static final String API_V_1_VENDORS_1 = "/api/v1/vendors/1";
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor());
        vendors.add(new Vendor());
        vendors.add(new Vendor());

        Mockito.when(vendorRepository.findAll()).thenReturn(vendors);

        final List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(3, vendorDTOS.size());
    }

    @Test
    void getVendorById() {
        Vendor optionalVendor = new Vendor();
        optionalVendor.setId(ID);
        optionalVendor.setName(NAME);

        Mockito.when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(optionalVendor));

        final VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(1,vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(vendorDTO.getName());

        Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        final VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName(),savedVendorDTO.getName());
        assertEquals(API_V_1_VENDORS_1, savedVendorDTO.getVendorUrl());
    }

    @Test
    void saveVendorByDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(vendorDTO.getName());

        Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        final VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID,vendorDTO);

        assertEquals(vendorDTO.getName(),savedVendorDTO.getName());
        assertEquals(API_V_1_VENDORS_1, savedVendorDTO.getVendorUrl());
    }

    @Test
    void patchVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName("OLD_NAME");

        Mockito.when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(savedVendor));
        savedVendor.setName(vendorDTO.getName());
        Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        final VendorDTO patchedVendor = vendorService.patchVendor(1L, vendorDTO);

        assertEquals(NAME,patchedVendor.getName());
    }

    @Test
    void deleteVendorById() {
        Long id = 1L;

        vendorRepository.deleteById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}