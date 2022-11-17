package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Inbound order repository.
 */
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {
}
