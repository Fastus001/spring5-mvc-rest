package guru.springframework.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Tom - 14.03.2021
 */
public abstract class AbstractRestControllerTest {

    public static String asJsonString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(e);
        }
    }
}
