package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

    public static final long ID = 1L;
    public static final String NAME = "Name";
    VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        final VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getId(), vendorDTO.getId());
        assertEquals(vendor.getName(),vendorDTO.getName());
    }

    @Test
    void vendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        final Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);

        assertEquals(vendorDTO.getId(),vendor.getId());
        assertEquals(vendorDTO.getName(),vendor.getName());
    }
}