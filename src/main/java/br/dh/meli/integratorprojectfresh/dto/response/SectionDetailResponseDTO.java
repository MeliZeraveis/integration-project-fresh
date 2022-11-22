package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDetailResponseDTO {
    private Long sectionCode;
    private float maxCapacity;
    private float usedCapacity;
    private Long warehouseCode;
    private String type;

    public SectionDetailResponseDTO(Section section) {
        this.sectionCode = section.getSectionCode();
        this.maxCapacity = section.getMaxCapacity();
        this.usedCapacity = section.getUsedCapacity();
        this.warehouseCode = section.getWarehouseCode();
        this.type = section.getType();
    }
}
