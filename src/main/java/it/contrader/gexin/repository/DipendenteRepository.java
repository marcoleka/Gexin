package it.contrader.gexin.repository;

import it.contrader.gexin.domain.Dipendente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dipendente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {

}
