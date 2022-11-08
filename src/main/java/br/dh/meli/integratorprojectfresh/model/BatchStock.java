package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "batch_stock")
public class BatchStock {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long batchNumber;

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(name = "current_temperature", nullable = false)
        private Float currentTemperature;

        @Column(name = "product_quantity", nullable = false)
        private int productQuantity;

        @Column(name = "manufacturing_date", nullable = false)
        private LocalDate manufacturingDate;

        @Column(name = "manufacturing_time", nullable = false)
        private LocalDateTime manufacturingTime;

        @Column(name = "volume", nullable = false)
        private Float volume;

        @Column(name = "dueDate", nullable = false)
        private LocalDate dueDate;

        @Column(name = "price", nullable = false)
        private BigDecimal price;

        @Column(name = "order_number_id", nullable = false)
        private Long orderNumberId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_number_id", insertable = false, updatable = false)
        @JsonIgnoreProperties("batchStock")
        private InboundOrder inboundOrder;
}