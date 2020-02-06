package it.contrader.gexin.repository;

import it.contrader.gexin.domain.Accesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Accesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessoRepository extends JpaRepository<Accesso, Long> {

}
