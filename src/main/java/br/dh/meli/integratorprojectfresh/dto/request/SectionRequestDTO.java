package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.OneOf;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SectionRequestDTO {
    @NotNull
    private float maxCapacity;

    @NotNull
    private float usedCapacity;

    @NotNull
    private Long warehouseCode;

    @OneOf(value = {"Fresh", "Frozen", "Refrigerated"}, message = Msg.SECTION_NOT_VALID)
    private String type;


    public SectionRequestDTO(Section section) {
        this.maxCapacity = section.getMaxCapacity();
        this.usedCapacity = section.getUsedCapacity();
        this.warehouseCode = section.getWarehouseCode();
        this.type = section.getType();
    }




}
