package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Announcement list response dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementListResponseDTO {

    private String name;

    private String description;

    private Long sellerId;

    private BigDecimal price;


    /**
     * Instantiates a new Announcement list response dto.
     *
     * @param announcement the announcement
     */
    public AnnouncementListResponseDTO(Announcement announcement) {
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.sellerId = announcement.getSellerId();
        this.price = announcement.getPrice();
    }
}
