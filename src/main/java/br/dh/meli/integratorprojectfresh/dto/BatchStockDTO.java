package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class BatchStockDTO {


    private Long announcementId;
    private Float currentTemperature;
    private int productQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private Float volume;
    private LocalDate dueDate;
    private BigDecimal price;
    private Long orderNumberId;


    public BatchStockDTO(BatchStock a) {
        this.announcementId = a.getAnnouncementId();
        this.currentTemperature = a.getCurrentTemperature();
        this.dueDate = a.getDueDate();
        this.productQuantity = a.getProductQuantity();
        this.manufacturingDate = a.getManufacturingDate();
        this.volume = a.getVolume();
        this.orderNumberId = a.getOrderNumberId();
        this.price = a.getPrice();
        this.manufacturingTime = a.getManufacturingTime();
    }
}
