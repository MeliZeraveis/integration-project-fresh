package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementListResponseDTO {

    private String name;

    private String description;

    private Long sellerId;

    private BigDecimal price;


    public AnnouncementListResponseDTO(Announcement announcement) {
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.sellerId = announcement.getSellerId();
        this.price = announcement.getPrice();
    }
}
