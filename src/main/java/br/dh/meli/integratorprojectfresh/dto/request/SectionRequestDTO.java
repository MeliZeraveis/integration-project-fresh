package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.OneOf;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionRequestDTO {
    @NotNull(message = Msg.MAX_CAPACITY_NOT_NULL)
    @Positive(message = Msg.MAX_CAPACITY_POSITIVE)
    private float maxCapacity;

    @NotNull(message = Msg.USED_CAPACITY_NOT_NULL)
    @Positive(message = Msg.USED_CAPACITY_POSITIVE)
    private float usedCapacity;

    @NotNull(message = Msg.SECTION_CODE_NOT_EMPTY)
    @Positive(message = Msg.SECTION_NOT_VALID)
    private Long warehouseCode;

    @NotEmpty(message = Msg.SECTION_CODE_NOT_EMPTY)
    @OneOf(value = {"Fresh", "Frozen", "Refrigerated"}, message = Msg.SECTION_NOT_VALID)
    private String type;


    public SectionRequestDTO(Section section) {
        this.maxCapacity = section.getMaxCapacity();
        this.usedCapacity = section.getUsedCapacity();
        this.warehouseCode = section.getWarehouseCode();
        this.type = section.getType();
    }




}
