package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class BatchStockDTO {

    @NotNull(message = Msg.FIELD_REQUIRED)
    private Long announcementId;
    @NotNull(message = Msg.FIELD_REQUIRED)
    private Float currentTemperature;
    @NotNull(message = Msg.FIELD_REQUIRED)
    @Min(value = 0, message = Msg.FIELD_MIN_VALUE)
    private int productQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    @NotNull(message = Msg.FIELD_REQUIRED)
    @Min(value = 1, message = Msg.FIELD_MIN_VALUE)
    private Float volume;
    @NotNull(message = Msg.FIELD_REQUIRED)
    private LocalDate dueDate;
    @NotNull(message = Msg.FIELD_REQUIRED)
    private BigDecimal price;
    @NotNull(message = Msg.FIELD_REQUIRED)
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
