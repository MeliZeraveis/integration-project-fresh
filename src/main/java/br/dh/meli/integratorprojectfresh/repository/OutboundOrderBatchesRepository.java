package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.OutboundOrderBatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Outbound order batches repository.
 */
@Repository
public interface OutboundOrderBatchesRepository extends JpaRepository<OutboundOrderBatches, Long> {
  /**
   * Find by order number id list.
   *
   * @param id the id
   * @return the list
   */
  List<OutboundOrderBatches> findAllByOrderNumberId(Long id);
}