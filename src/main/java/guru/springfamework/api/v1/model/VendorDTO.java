package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * Created by Tom - 15.03.2021
 */
@Data
public class VendorDTO {
    private static final String BASE_URI = "/api/v1/vendors/";

    private Long id;

    @ApiModelProperty(required = true, notes = "Name of the Vendor")
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;

    public void setId(Long id) {
        this.id = id;
        vendorUrl = BASE_URI+id;
    }
}
