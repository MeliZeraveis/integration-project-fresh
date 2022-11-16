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
@AllArgsConstructor
public class SectionDTO {
    private Long sectionCode;

    private Long warehouseCode;

    public SectionDTO(Section section, Announcement announcement) {
        this.sectionCode = section.getSectionCode();
        //this.warehouseCode = announcement.getBatchStock().stream().findFirst().get().getInboundOrder().getWarehouse().getWarehouseCode();
        this.warehouseCode = announcement.getBatchStock().get(0).getInboundOrder().getWarehouseCode();
    }
}
