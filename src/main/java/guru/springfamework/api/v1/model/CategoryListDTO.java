package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Tom - 11.03.2021
 */
@Data
@AllArgsConstructor
public class CategoryListDTO {

    private List<CategoryDTO> categories;
}
