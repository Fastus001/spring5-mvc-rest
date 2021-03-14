package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Tom - 11.03.2021
 */
@Data
public class CustomerDTO {
    private static final String URI = "/api/v1/customers/";

    private Long id;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;


    public void setId(Long id) {
        this.id = id;
        customerUrl = URI + id;
    }
}
