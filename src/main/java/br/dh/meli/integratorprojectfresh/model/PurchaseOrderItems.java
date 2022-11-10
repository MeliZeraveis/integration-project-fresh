package br.dh.meli.integratorprojectfresh.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
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
  private BigDecimal productPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_order_id", insertable = false, updatable = false)
  private PurchaseOrder purchaseOrder;
}
