package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockQuantityResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WarehouseService implements IWareshouseService {


    private final AnnouncementRepository announcementRepo;


    @Override
    public WarehouseProductQuantityGetResponseDTO getWarehouseProductsQuantityListByAnnoucementId(Long announcementId) {
        Optional<Announcement> announcement = announcementRepo.findById(announcementId);
        if (announcement.isEmpty()){
            throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
        }

        WarehouseProductQuantityGetResponseDTO warehouseProductQuantity = new WarehouseProductQuantityGetResponseDTO(announcement.get());

        Map<Long,Integer> mapResults = warehouseProductQuantity.warehouses.stream()
                .collect(Collectors.toMap(BatchStockQuantityResponseDTO::getWarehouseCode,
                        BatchStockQuantityResponseDTO::getTotalQuantity,
                        Integer::sum));

        List<Long> listLongs = new ArrayList<>(mapResults.keySet());
        List<Integer> listInteger = new ArrayList<>(mapResults.values());

        WarehouseProductQuantityGetResponseDTO warehouseProductQuantityFiltred = new WarehouseProductQuantityGetResponseDTO(announcement.get(), listLongs, listInteger);

        return warehouseProductQuantityFiltred;
    }
}
