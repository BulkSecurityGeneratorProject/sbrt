package br.ibict.repository;

import br.ibict.domain.LegalEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LegalEntity entity.
 */
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {

    LegalEntity findFirstByOrderByIdAsc();
}
