package guru.springframework.services;


import guru.springframework.api.v1.model.CategoryDTO;

import java.util.List;

/**
 * Created by Tom - 11.03.2021
 */
public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
