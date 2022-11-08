package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "warehouse_code", nullable = false, insertable = false, updatable = false)
    private Long warehouseCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_code")
    @JsonIgnoreProperties("inboundOrder")
    private Warehouse warehouse;


    @OneToMany(mappedBy = "inboundOrder")
    private List<BatchStock> batchStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_code", insertable = false, updatable = false)
    @JsonIgnoreProperties("inboundOrder")
    private Section section;
}
