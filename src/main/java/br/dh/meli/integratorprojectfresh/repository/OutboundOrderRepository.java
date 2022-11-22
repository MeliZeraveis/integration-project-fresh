package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.OutboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Outbound order repository.
 */
@Repository
public interface OutboundOrderRepository extends JpaRepository<OutboundOrder, Long> {
}