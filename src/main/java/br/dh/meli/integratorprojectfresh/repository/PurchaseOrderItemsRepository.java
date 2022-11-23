package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Purchase order items repository.
 */
@Repository
public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, Long> {
    /**
     * Find by purchase order id list.
     *
     * @param id the id
     * @return the list
     */
    List<PurchaseOrderItems> findByPurchaseOrderId(Long id);

    /**
     * Find purchase order items by purchase order id list.
     *
     * @param id the id
     * @return the list
     */
    List<PurchaseOrderItems> findPurchaseOrderItemsByPurchaseOrderId(Long id);

    /**
     * Delete by purchase order id.
     *
     * @param id the id
     */
    void deleteByPurchaseOrderId(Long id);
}
