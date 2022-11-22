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
public class SectionListFilterResponseDTO {
    private Long sectionCode;
    private String sectionType;
    private float availableCapatity;


    public SectionListFilterResponseDTO(Section section) {
        this.sectionCode = section.getSectionCode();
        this.sectionType = section.getType();
        this.availableCapatity = section.getMaxCapacity() - section.getUsedCapacity();
    }
}
