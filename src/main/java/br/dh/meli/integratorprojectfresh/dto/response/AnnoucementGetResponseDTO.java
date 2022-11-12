package br.dh.meli.integratorprojectfresh.dto.response;


import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AnnoucementGetResponseDTO {

    private SectionDTO section;

    private Long productId;

    private List<BatchSotckAnnoucementDTO> batchStock;

    public AnnoucementGetResponseDTO(Announcement announcement) {
        this.section= new SectionDTO(announcement.getSection(), announcement);
        this.productId = announcement.getAnnouncementId();
        this.batchStock = announcement.getBatchStock().stream().map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
    }
}
