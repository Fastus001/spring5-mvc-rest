package guru.springframework.repositories;

import guru.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tom - 15.03.2021
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
