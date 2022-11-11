package br.dh.meli.integratorprojectfresh.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@IdClass(PurchaseOrderItems.class)
@Table(name = "purchase_order_items")
public class PurchaseOrderItems implements Serializable {
  @Id
  @Column(name = "purchase_order_id")
  private Long purchaseOrderId;

  @Id
  @Column(name = "announcement_id", nullable = false)
  private Long announcementId;

  @Column(name = "product_quantity", nullable = false)
  private Integer productQuantity;

  @Column(name = "product_price", nullable = false)
  private BigDecimal productPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", insertable = false, updatable = false)
  private PurchaseOrder purchaseOrder;

  public PurchaseOrderItems(Long purchaseOrderId, Long announcementId, Integer productQuantity, BigDecimal productPrice) {
    this.purchaseOrderId = purchaseOrderId;
    this.announcementId = announcementId;
    this.productQuantity = productQuantity;
    this.productPrice = productPrice;
  }
}
