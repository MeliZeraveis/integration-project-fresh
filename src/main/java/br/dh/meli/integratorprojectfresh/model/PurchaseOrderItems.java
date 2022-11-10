package br.dh.meli.integratorprojectfresh.model;

import javax.persistence.*;
import java.io.Serializable;

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
  private int productQuantity;

  @Column(name = "product_price", nullable = false)
  private double productPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", insertable = false, updatable = false)
  private PurchaseOrder purchaseOrder;
}
