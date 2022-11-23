package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Purchase order repository.
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    /**
     * Find purchase order by buyer id list.
     *
     * @param id the id
     * @return the list
     */
    List<PurchaseOrder> findPurchaseOrderByBuyerId(Long id);
}
