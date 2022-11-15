package br.dh.meli.integratorprojectfresh.dto.response;


import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseProductQuantityListByAnnoucementIdGetResponseDTO {

    public Long productId;
    public List<BatchStockQuantityResponseDTO> warehouses;

    public WarehouseProductQuantityListByAnnoucementIdGetResponseDTO(Optional<Announcement> announcement, List<BatchStock> batchStockList) {
        this.warehouses = batchStockList.stream()
                .map(BatchStockQuantityResponseDTO::new)
                .collect(Collectors.toList());
        this.productId = announcement.get().getAnnouncementId();
    }
}
