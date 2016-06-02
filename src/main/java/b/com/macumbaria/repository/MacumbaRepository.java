package b.com.macumbaria.repository;

import b.com.macumbaria.domain.Macumba;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Macumba entity.
 */
public interface MacumbaRepository extends JpaRepository<Macumba,Long> {

}
