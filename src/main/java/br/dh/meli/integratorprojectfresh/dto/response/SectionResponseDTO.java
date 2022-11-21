package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectionResponseDTO {
    private Long sectionCode;
    private Long warehouseCode;

    public SectionResponseDTO(Section section) {
        this.sectionCode = section.getSectionCode();
        this.warehouseCode = section.getWarehouseCode();
    }

}
