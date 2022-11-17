package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "date", nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "total", nullable = false)
  private BigDecimal total;

  @Column(name = "buyer_id", nullable = false)
  private Long buyerId;

  @ManyToOne
  @JsonIgnoreProperties("purchase_order")
  @JoinColumn(name = "buyer_id", insertable = false, updatable = false)
  private User buyer;

  @OneToMany(mappedBy = "purchaseOrder")
  @JsonIgnoreProperties("purchaseOrder")
  private List<PurchaseOrderItems> orderItem;

  public PurchaseOrder(LocalDateTime date, OrderStatus status, BigDecimal total, Long buyerId) {
    this.date = date;
    this.status = status.name();
    this.total = total;
    this.buyerId = buyerId;
  }

  /**
   * getStatus
   * Getter method - Get the status of the purchase order
   * @return OrderStatus - Status of the purchase order or null
   */
  public OrderStatus getStatus() {
    try {
      return OrderStatus.valueOf(this.status);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * setStatus
   * Setter method - Set the status of the purchase order
   * @param status - Status of the purchase order
   */
  public void setStatus(OrderStatus status) {
    this.status = status.name();
  }
}
