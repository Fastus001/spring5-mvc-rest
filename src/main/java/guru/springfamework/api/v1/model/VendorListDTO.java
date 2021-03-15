package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Tom - 15.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorListDTO {

    private List<VendorDTO> vendors;
}
