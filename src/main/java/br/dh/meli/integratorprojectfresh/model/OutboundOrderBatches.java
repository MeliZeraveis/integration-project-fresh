package br.dh.meli.integratorprojectfresh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Outbound order batches.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbound_order_batches")
public class OutboundOrderBatches {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long batchNumber;

  @Column(name = "announcement_id", nullable = false)
  private Long announcementId;

  @Column(name = "section_type", nullable = false)
  private String sectionType;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_number_id", insertable = false, updatable = false)
  @JsonIgnore
  @JsonIgnoreProperties(value = {"batchStock", "warehouse"})
  private OutboundOrder outboundOrder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "announcement_id", insertable = false, updatable = false)
  @JsonIgnoreProperties("batchStock")
  private Announcement announcement;

  /**
   * Instantiates a new Outbound order batch.
   *
   * @param batch the batch data
   */
  public OutboundOrderBatches(BatchStock batch) {
    this.announcementId = batch.getAnnouncementId();
    this.sectionType = batch.getSectionType();
    this.manufacturingDate = batch.getManufacturingDate();
    this.manufacturingTime =  batch.getManufacturingTime();
    this.productQuantity = batch.getProductQuantity();
    this.volume = batch.getVolume();
    this.dueDate = batch.getDueDate();
    this.price = batch.getPrice();
    this.orderNumberId = batch.getOrderNumberId();
  }

  /**
   * Convert OutboundOrderBatches to BatchStock.
   *
   * @return a new BatchStock object with the same values as the OutboundOrderBatches object
   */
  public BatchStock toBatchStock() {
    return new BatchStock(this.announcementId, this.sectionType, this.productQuantity, this.manufacturingDate,
            this.manufacturingTime, this.volume, this.dueDate, this.price);
  }
}
