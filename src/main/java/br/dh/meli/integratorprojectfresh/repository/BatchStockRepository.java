package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Batch stock repository.
 */
public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
}
