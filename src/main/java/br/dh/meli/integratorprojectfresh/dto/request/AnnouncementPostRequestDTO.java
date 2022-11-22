package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementPostRequestDTO {
    @NotBlank(message = Msg.NAME_NOT_BLANK)
    private String name;
    @NotBlank(message = Msg.DESCRIPTION_NOT_BLANK)
    private String description;
    @NotNull(message = Msg.PRICE_NOT_NULL)
    @Min(value = 1, message = Msg.FIELD_MIN_VALUE)
    private BigDecimal price;
    @NotNull(message = Msg.SECTION_CODE_NOT_EMPTY)
    private Long sectionCode;
    @NotNull(message = Msg.SELLER_NOT_NULL)
    private Long sellerId;

    public AnnouncementPostRequestDTO(Announcement announcement) {
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.price = announcement.getPrice();
        this.sectionCode = announcement.getSectionCode();
        this.sellerId = announcement.getSellerId();
    }
}
