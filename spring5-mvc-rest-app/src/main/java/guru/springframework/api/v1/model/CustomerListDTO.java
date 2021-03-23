package guru.springframework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Tom - 14.03.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDTO {

    private List<CustomerDTO> customers;
}
