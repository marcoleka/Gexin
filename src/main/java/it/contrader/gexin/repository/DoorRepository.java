package it.contrader.gexin.repository;

import it.contrader.gexin.domain.Door;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Door entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoorRepository extends JpaRepository<Door, Long> {

}
