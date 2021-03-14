package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tom - 11.03.2021
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
