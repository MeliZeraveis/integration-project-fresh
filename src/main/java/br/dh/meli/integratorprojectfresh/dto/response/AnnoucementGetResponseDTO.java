package br.dh.meli.integratorprojectfresh.dto.response;


import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Annoucement get response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnoucementGetResponseDTO {

    private SectionDTO section;

    private Long productId;

    private List<BatchSotckAnnoucementDTO> batchStock;

    /**
     * Instantiates a new Annoucement get response dto.
     *
     * @param announcement the announcement
     */
    public AnnoucementGetResponseDTO(Announcement announcement) {
        this.section= new SectionDTO(announcement.getSection(), announcement);
        this.productId = announcement.getAnnouncementId();
        this.batchStock = announcement.getBatchStock().stream().map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
    }

    /**
     * Instantiates a new Annoucement get response dto.
     *
     * @param announcement the announcement
     * @param letra        the letra
     */
    public AnnoucementGetResponseDTO(Announcement announcement, String letra) {
        this.section= new SectionDTO(announcement.getSection(), announcement);
        this.productId = announcement.getAnnouncementId();
        System.out.println(letra);
        if(letra.equalsIgnoreCase("L")) {
            this.batchStock = announcement.getBatchStock().stream().sorted((p1, p2) -> p1.getBatchNumber().compareTo(p2.getBatchNumber())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(letra.equalsIgnoreCase("Q")) {
            this.batchStock = announcement.getBatchStock().stream().sorted(Comparator.comparingInt(BatchStock::getProductQuantity)).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(letra.equalsIgnoreCase("V")) {
            this.batchStock = announcement.getBatchStock().stream().sorted((p1, p2) -> p1.getDueDate().compareTo(p2.getDueDate())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
    }
}
