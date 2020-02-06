package it.contrader.gexin.repository;

import it.contrader.gexin.domain.TimeZone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimeZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeZoneRepository extends JpaRepository<TimeZone, Long> {

}
