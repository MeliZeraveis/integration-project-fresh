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
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

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
}
