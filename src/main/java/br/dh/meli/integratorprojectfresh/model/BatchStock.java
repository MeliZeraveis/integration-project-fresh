package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

        @Column(name = "announcement_id", nullable = false)
        private Long announcementId;

        @Column(name = "current_temperature", nullable = false)
        private Float currentTemperature;

        @Column(name = "product_quantity", nullable = false)
        private int productQuantity;

        @Column(name = "manufacturing_date", nullable = false)
        private LocalDate manufacturingDate;

        @Column(name = "manufacturing_time", nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "announcement_id", insertable = false, updatable = false)
        @JsonIgnoreProperties("batchStock")
        private Announcement announcement;

        public BatchStock(BatchStock a, Long orderNumber) {
                this.announcementId = a.getAnnouncementId();
                this.currentTemperature = a.getCurrentTemperature();
                this.manufacturingDate = a.getManufacturingDate();
                this.dueDate = a.getDueDate();
                this.productQuantity = a.getProductQuantity();
                this.price = a.getPrice();
                this.volume = a.getVolume();
                this.orderNumberId = orderNumber;
                this.manufacturingTime = a.getManufacturingTime();
        }

        public BatchStock(BatchStock a, Long orderNumber, Long batchNumber) {
                this.batchNumber = batchNumber;
                this.announcementId = a.getAnnouncementId();
                this.currentTemperature = a.getCurrentTemperature();
                this.manufacturingDate = a.getManufacturingDate();
                this.dueDate = a.getDueDate();
                this.productQuantity = a.getProductQuantity();
                this.price = a.getPrice();
                this.volume = a.getVolume();
                this.orderNumberId = orderNumber;
                this.manufacturingTime = a.getManufacturingTime();
        }
}