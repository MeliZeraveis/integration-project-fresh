package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityListByAnnoucementIdGetResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseService implements IWareshouseService {


    private final AnnouncementRepository announcementRepo;
    private final BatchStockRepository batchStockRepo;


    @Override
    public WarehouseProductQuantityListByAnnoucementIdGetResponseDTO getWarehouseProductsQuantityListByAnnoucementId(Long announcementId) {
        Optional<Announcement> announcement = announcementRepo.findById(announcementId);
        List<BatchStock> batchStockList = batchStockRepo.findAllById(Collections.singleton(announcementId));

        return new WarehouseProductQuantityListByAnnoucementIdGetResponseDTO(announcement, batchStockList);

    }
}
