package br.dh.meli.integratorprojectfresh.dto.response;


import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.PropertySource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnoucementGetResponseDTO {

    private SectionDTO section;

    private Long productId;

    private List<BatchSotckAnnoucementDTO> batchStock;

    public AnnoucementGetResponseDTO(Announcement announcement) {
        this.section= new SectionDTO(announcement.getSection(), announcement);
        this.productId = announcement.getAnnouncementId();
        this.batchStock = announcement.getBatchStock().stream().map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
    }

    public AnnoucementGetResponseDTO(Announcement announcement, String letra) {
        this.section= new SectionDTO(announcement.getSection(), announcement);
        this.productId = announcement.getAnnouncementId();
        if(letra.equalsIgnoreCase("L")) {
            this.batchStock = announcement.getBatchStock().stream().sorted((p1, p2) -> p2.getBatchNumber().compareTo(p1.getBatchNumber())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(letra.equalsIgnoreCase("Q")) {
            this.batchStock = announcement.getBatchStock().stream().sorted(Comparator.comparingInt(BatchStock::getProductQuantity).reversed()).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(letra.equalsIgnoreCase("V")) {
            this.batchStock = announcement.getBatchStock().stream().sorted((p1, p2) -> p1.getDueDate().compareTo(p2.getDueDate())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
    }
}
