package guru.springfamework.repositories;

import guru.springfamework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tom - 15.03.2021
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
