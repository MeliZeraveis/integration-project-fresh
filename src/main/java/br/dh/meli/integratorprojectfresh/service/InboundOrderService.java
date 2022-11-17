package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.LimitCapacitySectionException;
import br.dh.meli.integratorprojectfresh.exception.ManagerNotValidException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.exception.SectionTypeException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {
    private final InboundOrderRepository repo;
    private final BatchStockRepository batchStockRepo;
    private final WarehouseRepository warehouseRepo;
    private final SectionRepository sectionRepo;
    private final AnnouncementRepository announcementRepo;

    void validIfWarehouseExist(Long warehouseCode) throws NotFoundException {
      Optional<Warehouse> warehouseOptional = warehouseRepo.findById(warehouseCode);
      if (warehouseOptional.isEmpty()){
        throw new NotFoundException(Msg.WAREHOUSE_NOT_FOUND);
      }

      if (warehouseOptional.get().getManager() == null
              || !Objects.equals(warehouseOptional.get().getManager().getRole(), "manager")){
        throw new ManagerNotValidException(Msg.MANAGER_NOT_VALID);
      }
    }
    void validSection(long sectionCode, List<BatchStockDTO>batchStockList) {
      Optional<Section> sectionOptional = sectionRepo.findById(sectionCode);

      if (sectionOptional.isEmpty()){
        throw new NotFoundException(Msg.SECTION_NOT_FOUND);
      }
      float sectionMaxCapacity = sectionOptional.get().getMaxCapacity();
      float sectionCapacityUsed = sectionOptional.get().getUsedCapacity();
      for (BatchStockDTO b : batchStockList) {
        if(!Objects.equals(sectionOptional.get().getType(), b.getSectionType())){
          throw new SectionTypeException(Msg.INSERT_BATCH_SECTION_INCORRET);
        }
        float totalSum = sectionCapacityUsed + b.getVolume();
        if (totalSum > sectionMaxCapacity) {
          throw new LimitCapacitySectionException(Msg.LIMIT_CAPACITY_SECTION);
        }
        sectionOptional.get().setUsedCapacity(totalSum);
        sectionRepo.save(sectionOptional.get());
      }
    }

    void validSectionBatchStockUpdate(Section section, List<BatchStockDTO>batchStockList){
        float sectionMaxCapacity = section.getMaxCapacity();
        float sectionCapacityUsed = section.getUsedCapacity();
        for (BatchStockDTO b : batchStockList) {
            if(!Objects.equals(section.getType(), b.getSectionType())){
                throw new SectionTypeException(Msg.INSERT_BATCH_SECTION_INCORRET);
            }
            BatchStock batchStock = batchStockRepo.findById(b.getBatchNumber()).get();
            sectionCapacityUsed = sectionCapacityUsed - batchStock.getVolume();
            float totalSum = sectionCapacityUsed + b.getVolume();
            if (totalSum > sectionMaxCapacity) {
                throw new LimitCapacitySectionException(Msg.LIMIT_CAPACITY_SECTION);
            }
            section.setUsedCapacity(totalSum);
            sectionRepo.save(section);
        }
    }
    void validSectionUpdate(long sectionCode, List<BatchStockDTO>batchStockList) {
        Optional<Section> sectionOptional = sectionRepo.findById(sectionCode);

        if (sectionOptional.isEmpty()){
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }

        validSectionBatchStockUpdate(sectionOptional.get(), batchStockList);

    }

    @Override
    public InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrderResquest) {
      InboundOrder inboundOrder = new InboundOrder(inboundOrderResquest.getInboundOrder());
      validIfWarehouseExist(inboundOrder.getWarehouseCode());
      validIfAnnouncementExist(inboundOrderResquest.getInboundOrder().getBatchStock());
      validSection(inboundOrder.getSectionCode(), inboundOrderResquest.getInboundOrder().getBatchStock());
      repo.save(inboundOrder);
      List<BatchStock> batchStockList =  inboundOrderResquest.getInboundOrder()
              .getBatchStock().stream()
              .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
              .collect(Collectors.toList());
      return new InboundOrderPostResponseDTO( batchStockRepo.saveAll(batchStockList));
    }

    void validIfAnnouncementExist(List<BatchStockDTO> batchStockDTOList) throws NotFoundException {
      for (BatchStockDTO batchStockDTO : batchStockDTOList) {
        Optional<Announcement> announcementOptional = announcementRepo.findById(batchStockDTO.getAnnouncementId());
        if (announcementOptional.isEmpty()){
          throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
        }
      }
    }

    void validIfInboundOrderExist(Long orderNumber) throws NotFoundException {
      if (repo.findById(orderNumber).isEmpty()) {
        throw new NotFoundException(Msg.INBOUND_ORDER_NOT_FOUND);
      }
    }

    void validBatch(List<BatchStockDTO> batchStockDTOList) throws NotFoundException {
      for (BatchStockDTO batchStockDTO : batchStockDTOList) {
        Optional<BatchStock> batchStock = batchStockRepo.findById(batchStockDTO.getBatchNumber());
        if (batchStock.isEmpty()) {
          throw new NotFoundException(Msg.BATCH_NOT_FOUND);
        }
      }
    }

    @Override
    public InboundOrderPutResponseDTO update(InboundOrderRequestDTO inboundOrderResquest) {
      InboundOrderDTO inboundOrderDTO = inboundOrderResquest.getInboundOrder();

      validIfInboundOrderExist(inboundOrderDTO.getOrderNumber());
      InboundOrder inboundOrder = new InboundOrder(inboundOrderDTO, inboundOrderDTO.getOrderNumber());

      validBatch(inboundOrderDTO.getBatchStock());

      validIfWarehouseExist(inboundOrder.getWarehouseCode());

      validIfAnnouncementExist(inboundOrderResquest.getInboundOrder().getBatchStock());

      validSectionUpdate(inboundOrder.getSectionCode(), inboundOrderResquest.getInboundOrder().getBatchStock());

      InboundOrder inboundOrderUpdated = repo.save(inboundOrder);

      List<BatchStock> batchStockList = inboundOrderResquest.getInboundOrder()
              .getBatchStock().stream()
              .map(a -> new BatchStock(a, inboundOrder.getOrderNumber(), a.getBatchNumber()))
              .collect(Collectors.toList());
      List<BatchStock> batchStockListUpdated = batchStockRepo.saveAll(batchStockList);
      return new InboundOrderPutResponseDTO(inboundOrderUpdated, batchStockListUpdated);
    }
}

