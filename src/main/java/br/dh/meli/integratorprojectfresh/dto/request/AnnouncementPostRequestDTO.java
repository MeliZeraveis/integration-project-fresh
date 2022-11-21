package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementPostRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Long sectionCode;
    private Long sellerId;

    public AnnouncementPostRequestDTO(Announcement announcement) {
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.price = announcement.getPrice();
        this.sectionCode = announcement.getSectionCode();
        this.sellerId = announcement.getSellerId();
    }
}
