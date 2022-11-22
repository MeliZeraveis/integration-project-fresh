package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Warehouse repository.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
