package br.dh.meli.integratorprojectfresh.model;

import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Inbound order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inbound_order")
public class InboundOrder {
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

    @ManyToOne(fetch = FetchType.EAGER) //
    @JoinColumn(name = "warehouse_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("inboundOrder")
    private Warehouse warehouse;


    @OneToMany(mappedBy = "inboundOrder")

    @JsonIgnoreProperties("inboundOrder")
    private List<BatchStock> batchStock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("inboundOrder")
    private Section section;

    /**
     * Instantiates a new Inbound order.
     *
     * @param inboundOrder the inbound order
     */
    public InboundOrder(InboundOrderDTO inboundOrder) {
        this.orderDate = inboundOrder.getOrderDate();
        this.sectionCode = inboundOrder.getSectionCode();
        this.warehouseCode = inboundOrder.getWarehouseCode();
    }

    /**
     * Instantiates a new Inbound order.
     *
     * @param inboundOrder the inbound order
     * @param orderNumber  the order number
     */
    public InboundOrder(InboundOrderDTO inboundOrder, Long orderNumber) {
        this.orderNumber = orderNumber;
        this.orderDate = inboundOrder.getOrderDate();
        this.sectionCode = inboundOrder.getSectionCode();
        this.warehouseCode = inboundOrder.getWarehouseCode();
    }

    /**
     * Instantiates a new Inbound order.
     *
     * @param orderNumber   the order number
     * @param orderDate     the order date
     * @param sectionCode   the section code
     * @param warehouseCode the warehouse code
     * @param batchStock    the batch stock
     */
    public InboundOrder(Long orderNumber, LocalDate orderDate, Long sectionCode, Long warehouseCode, List<BatchStock> batchStock) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.sectionCode = sectionCode;
        this.warehouseCode = warehouseCode;
        this.batchStock = batchStock;
    }
}
