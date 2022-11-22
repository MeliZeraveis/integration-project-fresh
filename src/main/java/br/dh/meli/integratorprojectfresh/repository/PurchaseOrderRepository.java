package br.dh.meli.integratorprojectfresh.repository;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Purchase order repository.
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    /**
     * Find by announcement id and buyer id list.
     *
     * @param announcementId the announcement id
     * @param buyerId        the buyer id
     * @return the list
     */
    @Query(value = "SELECT * FROM purchase_order po " +
            "INNER JOIN purchase_order_items poi ON po.id = poi.purchase_order_id " +
            "WHERE poi.announcement_id = ?1 AND po.buyer_id = ?2 AND po.status = 'Finalizado'", nativeQuery = true)
    List<PurchaseOrder> findByAnnouncementIdAndBuyerId(Long announcementId, Long buyerId);
}

