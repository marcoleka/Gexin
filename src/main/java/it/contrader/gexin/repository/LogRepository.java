package it.contrader.gexin.repository;

import it.contrader.gexin.domain.Log;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Log entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
