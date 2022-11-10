package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
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
  private LocalDateTime date;

  @Column(name = "status", nullable = false)
  private OrderStatus orderStatus;

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

  /**
   * getTotalPrice
   * Calculates the total price of the purchase order
   * @return BigDecimal
   */
  public BigDecimal getTotalPrice() {
    return orderItem.stream()
      .map((item) -> item.getProductPrice().multiply(BigDecimal.valueOf(item.getProductQuantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
