package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.OutboundOrderBatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Outbound order batches repository.
 */
@Repository
public interface OutboundOrderBatchesRepository extends JpaRepository<OutboundOrderBatches, Long> {
}