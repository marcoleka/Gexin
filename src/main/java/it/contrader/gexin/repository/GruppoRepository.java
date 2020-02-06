package it.contrader.gexin.repository;

import it.contrader.gexin.domain.Gruppo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Gruppo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GruppoRepository extends JpaRepository<Gruppo, Long> {

    @Query(value = "select distinct gruppo from Gruppo gruppo left join fetch gruppo.doors",
        countQuery = "select count(distinct gruppo) from Gruppo gruppo")
    Page<Gruppo> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct gruppo from Gruppo gruppo left join fetch gruppo.doors")
    List<Gruppo> findAllWithEagerRelationships();

    @Query("select gruppo from Gruppo gruppo left join fetch gruppo.doors where gruppo.id =:id")
    Optional<Gruppo> findOneWithEagerRelationships(@Param("id") Long id);

}
