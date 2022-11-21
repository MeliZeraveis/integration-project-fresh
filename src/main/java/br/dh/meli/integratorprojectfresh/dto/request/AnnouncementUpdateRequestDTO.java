package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementUpdateRequestDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long sectionCode;
    private Long sellerId;

    public AnnouncementUpdateRequestDTO(Announcement announcement) {
        this.id = announcement.getAnnouncementId();
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.price = announcement.getPrice();
        this.sectionCode = announcement.getSectionCode();
        this.sellerId = announcement.getSellerId();
    }
}
