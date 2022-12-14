package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.OneOf;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDTO {
    private Long batchNumber;

    @NotNull(message = Msg.ANNOUNCEMENT_ID_REQUIRED)
    private Long announcementId;

    @NotNull(message = Msg.TEMPERATURE_REQUIRED)
    @OneOf(value = {"Fresh", "Frozen", "Refrigerated"}, message = Msg.SECTION_NOT_VALID)
    private String sectionType;

    @NotNull(message = Msg.QUANTITY_REQUIRED)
    @Min(value = 1, message = Msg.QUANTITY_MIN_VALUE)
    private Integer productQuantity;

    @NotNull(message = Msg.DATE_REQUIRED)
    @PastOrPresent(message = Msg.DATE_PAST_OR_PRESENT)
    private LocalDate manufacturingDate;

    @NotNull(message = Msg.TIME_REQUIRED)
    @PastOrPresent(message = Msg.TIME_PAST_OR_PRESENT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        this.batchNumber = a.getBatchNumber();
        this.announcementId = a.getAnnouncementId();
        this.sectionType = a.getSectionType();
        this.manufacturingDate = a.getManufacturingDate();
        this.dueDate = a.getDueDate();
        this.productQuantity = a.getProductQuantity();
        this.price = a.getPrice();
        this.volume = a.getVolume();
        this.orderNumberId = a.getOrderNumberId();
        this.manufacturingTime = a.getManufacturingTime();
    }


    public BatchStockDTO(Long announcementId, String sectionType, int productQuantity, LocalDate manufacturingDate, LocalDateTime manufacturingTime, float volume, LocalDate dueDate, BigDecimal price) {
        this.announcementId = announcementId;
        this.sectionType = sectionType;
        this.productQuantity = productQuantity;
        this.manufacturingDate = manufacturingDate;
        this.manufacturingTime = manufacturingTime;
        this.volume = volume;
        this.dueDate = dueDate;
        this.price = price;
    }
}
