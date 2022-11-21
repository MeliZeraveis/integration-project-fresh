package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Long sectionCode;

    public AnnouncementRequestDTO(Announcement announcement) {
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.price = announcement.getPrice();
        this.sectionCode = announcement.getSectionCode();
    }
}
