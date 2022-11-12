package br.dh.meli.integratorprojectfresh.dto.request;


import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SectionDTO {
    private Long sectionCode;

    private Long warehouseCode;

    public SectionDTO(Section section, Announcement announcement) {
        this.sectionCode = section.getSectionCode();
        this.warehouseCode = announcement.getBatchStock().stream().filter(p -> p.getInboundOrder().getWarehouseCode());
    }
}
