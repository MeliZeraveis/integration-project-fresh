package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.dto.request.OutboundOrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Outbound order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbound_order")
public class OutboundOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long orderNumber;

  @Column(name = "order_date", nullable = false)
  private LocalDate orderDate;

  @Column(name = "section_code", nullable = false)
  private Long sectionCode;

  @Column(name = "warehouse_code", nullable = false )
  private Long warehouseCode;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "warehouse_code", insertable = false, updatable = false)
  @JsonIgnoreProperties("outboundOrder")
  private Warehouse warehouse;
  
  @OneToMany(mappedBy = "outboundOrder")
  @JsonIgnoreProperties("outboundOrder")
  private List<OutboundOrderBatches> outboundOrderBatches;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "section_code", insertable = false, updatable = false)
  @JsonIgnoreProperties("outboundOrder")
  private Section section;

  /**
   * Instantiates a new Outbound order.
   *
   * @param outboundOrder the outbound order
   */
  public OutboundOrder(OutboundOrderDTO outboundOrder) {
    this.orderDate = outboundOrder.getOrderDate();
    this.sectionCode = outboundOrder.getSectionCode();
    this.warehouseCode = outboundOrder.getWarehouseCode();
  }

  /**
   * Instantiates a new Outbound order.
   *
   * @param outboundOrder the outbound order
   * @param orderNumber   the order number
   */
  public OutboundOrder(OutboundOrderDTO outboundOrder, Long orderNumber) {
    this.orderNumber = orderNumber;
    this.orderDate = outboundOrder.getOrderDate();
    this.sectionCode = outboundOrder.getSectionCode();
    this.warehouseCode = outboundOrder.getWarehouseCode();
  }

  /**
   * Instantiates a new Outbound order.
   *
   * @param orderNumber          the order number
   * @param orderDate            the order date
   * @param sectionCode          the section code
   * @param warehouseCode        the warehouse code
   * @param outboundOrderBatches the batch stock
   */
  public OutboundOrder(Long orderNumber, LocalDate orderDate, Long sectionCode, Long warehouseCode, List<OutboundOrderBatches> outboundOrderBatches) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.sectionCode = sectionCode;
    this.warehouseCode = warehouseCode;
    this.outboundOrderBatches = outboundOrderBatches;
  }
}
