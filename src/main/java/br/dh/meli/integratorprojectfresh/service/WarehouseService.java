package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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

//        List<BatchStock> batchStockList = announcement.get().getBatchStock()
//                .stream()
//                .map((b,i) -> {
//                    if (announcement.get().getBatchStock().get().getInboundOrder().getWarehouseCode() == ) {
//
//                    };
//                } );

        return new WarehouseProductQuantityGetResponseDTO(announcement.get());

    }
}
