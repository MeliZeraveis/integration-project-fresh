package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionListWarehouseResponseDTO {
    private Long warehouseCode;
    private String warahouseName;
    private List<SectionListFilterResponseDTO> sectionList;

    public SectionListWarehouseResponseDTO(Warehouse warehouse, String order) {
        this.warehouseCode = warehouse.getWarehouseCode();
        this.warahouseName = warehouse.getWarehouseName();
        this.sectionList = warehouse.getSection().stream()
                .map(SectionListFilterResponseDTO::new)
                .collect(Collectors.toList());
        if (Objects.equals(order, "asc")) {
            this.sectionList = sectionList.stream()
                    .sorted(Comparator.comparing(SectionListFilterResponseDTO::getAvailableCapatity))
                    .collect(Collectors.toList());
        }

    }
}
