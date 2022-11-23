package br.dh.meli.integratorprojectfresh.dto.request;


import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {
    private Long sectionCode;

    private Long warehouseCode;

    public SectionDTO(Section section, Announcement announcement) {
        this.sectionCode = section.getSectionCode();
        this.warehouseCode = announcement.getBatchStock().get(0).getInboundOrder().getWarehouseCode();
    }
}
