package br.dh.meli.integratorprojectfresh.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The type Purchase order items.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "purchase_order_id")
  private Long purchaseOrderId;

  @Column(name = "announcement_id", nullable = false)
  private Long announcementId;

  @Column(name = "product_quantity", nullable = false)
  private Integer productQuantity;

  @Column(name = "product_price", nullable = false)
  private BigDecimal productPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", insertable = false, updatable = false)
  private PurchaseOrder purchaseOrder;

  @ManyToOne
  @JoinColumn(name = "announcement_id", insertable = false, updatable = false)
  private Announcement announcement;

    /**
     * Instantiates a new Purchase order items.
     *
     * @param purchaseOrderId the purchase order id
     * @param announcementId  the announcement id
     * @param productQuantity the product quantity
     * @param productPrice    the product price
     */
    public PurchaseOrderItems(Long purchaseOrderId, Long announcementId, Integer productQuantity, BigDecimal productPrice) {
    this.purchaseOrderId = purchaseOrderId;
    this.announcementId = announcementId;
    this.productQuantity = productQuantity;
    this.productPrice = productPrice;
  }

}
