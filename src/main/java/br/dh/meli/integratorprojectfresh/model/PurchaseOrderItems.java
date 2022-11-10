package br.dh.meli.integratorprojectfresh.model;

import javax.persistence.*;

@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItems {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "purchase_order_id")
  private Long purchaseOrderId;

  @Column(name = "announcement_id", nullable = false)
  private Long announcementId;

  @Column(name = "product_quantity", nullable = false)
  private int productQuantity;

  @Column(name = "product_price", nullable = false)
  private double productPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", insertable = false, updatable = false)
  private PurchaseOrder purchaseOrder;

  @ManyToOne
  @JoinColumn(name = "announcement_id", insertable = false, updatable = false)
  private Announcement announcement;
}
