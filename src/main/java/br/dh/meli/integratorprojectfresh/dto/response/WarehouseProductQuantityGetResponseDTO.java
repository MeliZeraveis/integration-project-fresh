package br.dh.meli.integratorprojectfresh.dto.response;


import br.dh.meli.integratorprojectfresh.model.Announcement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseProductQuantityGetResponseDTO {

    public Long productId;
    public List<BatchStockQuantityResponseDTO> warehouses;

    public WarehouseProductQuantityGetResponseDTO(Announcement announcement) {
        this.warehouses = announcement.getBatchStock().stream()
                .map(BatchStockQuantityResponseDTO::new)
                .collect(Collectors.toList());
        this.productId = announcement.getAnnouncementId();
    }

    public WarehouseProductQuantityGetResponseDTO(Announcement announcement, List<Long> longs, List<Integer> integers) {
        this.productId = announcement.getAnnouncementId();
        List<BatchStockQuantityResponseDTO> warehouseList = new ArrayList<>();
        for (int i=0 ; i < integers.size() ; i++) {
            warehouseList.add(new BatchStockQuantityResponseDTO(longs.get(i), integers.get(i)));
            this.warehouses = warehouseList;
        }

    }


}
