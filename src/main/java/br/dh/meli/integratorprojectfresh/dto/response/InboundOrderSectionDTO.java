package br.dh.meli.integratorprojectfresh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Inbound order section dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderSectionDTO {
    /**
     * The Section code.
     */
    Long sectionCode;
    /**
     * The Warehouse code.
     */
    Long warehouseCode;
}
