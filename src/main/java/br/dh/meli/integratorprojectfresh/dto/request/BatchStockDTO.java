package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class BatchStockDTO {

    @NotNull(message = Msg.ANNOUNCEMENT_ID_REQUIRED)
    private Long announcementId;
    @NotNull(message = Msg.TEMPERATURE_REQUIRED)
    private Float currentTemperature;
    @NotNull(message = Msg.QUANTITY_REQUIRED)
    @Min(value = 1, message = Msg.QUANTITY_MIN_VALUE)
    private int productQuantity;
    @NotNull(message = Msg.DATE_REQUIRED)
    @PastOrPresent(message = Msg.DATE_PAST_OR_PRESENT)
    private LocalDate manufacturingDate;
    @NotNull(message = Msg.TIME_REQUIRED)
    @PastOrPresent(message = Msg.TIME_PAST_OR_PRESENT)
    private LocalDateTime manufacturingTime;
    @NotNull(message = Msg.VOLUME_REQUIRED)
    @Min(value = 1, message = Msg.VOLUME_MIN_VALUE)
    private Float volume;
    @NotNull(message = Msg.DATE_REQUIRED)
    @Future(message = Msg.DATE_FUTURE)
    private LocalDate dueDate;
    @NotNull(message = Msg.PRICE_REQUIRED)
    @Min(value = 0, message = Msg.FIELD_MIN_VALUE)
    private BigDecimal price;

    private Long orderNumberId;


    public BatchStockDTO(BatchStock a) {
        this.announcementId = a.getAnnouncementId();
        this.currentTemperature = a.getCurrentTemperature();
        this.dueDate = a.getDueDate();
        this.productQuantity = a.getProductQuantity();
        this.manufacturingDate = a.getManufacturingDate();
        this.volume = a.getVolume();
        if(a.getOrderNumberId() != null) {
            this.orderNumberId = a.getOrderNumberId();
        }
        this.price = a.getPrice();
        this.manufacturingTime = a.getManufacturingTime();
    }
}
