package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.ManagerNotValidException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.exception.SectionTypeException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Inbound order service.
 */
@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {
    private final InboundOrderRepository repo;
    private  final BatchStockRepository batchStockRepo;
    private final WarehouseRepository warehouseRepo;
    private final SectionRepository sectionRepo;
    private final AnnouncementRepository announcementRepo;

    /**
     * Valid if warehouse exist.
     *
     * @param warehouseCode the warehouse code
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Valid section.
     *
     * @param sectionCode    the section code
     * @param batchStockList the batch stock list
     * @param warehouseCode  the warehouse code
     */
    void validSection(long sectionCode, List<BatchStockDTO>batchStockList, Long warehouseCode) {
        Optional<Section> sectionOptional = sectionRepo.findById(sectionCode);
        if (sectionOptional.isEmpty()){
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }

        if(!Objects.equals(sectionOptional.get().getWarehouseCode(), warehouseCode)){
            throw new ActionNotAllowedException(Msg.SECTION_NOT_BELONG_WAREHOUSE);
        }
        float sectionMaxCapacity = sectionOptional.get().getMaxCapacity();
        float sectionCapacityUsed = sectionOptional.get().getUsedCapacity();
        for (BatchStockDTO b : batchStockList) {
            if(!Objects.equals(sectionOptional.get().getType(), b.getSectionType())){
                throw new ActionNotAllowedException(Msg.INSERT_BATCH_SECTION_INCORRET);
            }
            float totalSum = sectionCapacityUsed + b.getVolume();
            if (totalSum > sectionMaxCapacity) {
                throw new ActionNotAllowedException(Msg.LIMIT_CAPACITY_SECTION);
            }
            sectionOptional.get().setUsedCapacity(totalSum);
            sectionRepo.save(sectionOptional.get());
        }
    }

    /**
     * Valid section batch stock update.
     *
     * @param section        the section
     * @param batchStockList the batch stock list
     */
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
                throw new ActionNotAllowedException(Msg.LIMIT_CAPACITY_SECTION);
            }
            section.setUsedCapacity(totalSum);
            sectionRepo.save(section);
        }
    }

    /**
     * Valid section update.
     *
     * @param sectionCode    the section code
     * @param batchStockList the batch stock list
     */
    void validSectionUpdate(long sectionCode, List<BatchStockDTO>batchStockList) {
        Optional<Section> sectionOptional = sectionRepo.findById(sectionCode);

        if (sectionOptional.isEmpty()){
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }

        validSectionBatchStockUpdate(sectionOptional.get(), batchStockList);

    }

    /**
     * Valid batch due date.
     *
     * @param batchStockList the batch stock list
     */
    void validBatchDueDate(List<BatchStockDTO>batchStockList){
        for (BatchStockDTO b : batchStockList) {
            if( b.getDueDate().isBefore(LocalDate.now()) || LocalDate.now().plusWeeks(3).isAfter(b.getDueDate())){
                throw new ActionNotAllowedException(Msg.BATCH_EXPIRED);
            }
        }
    }

    @Override
    public InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrderResquest) {
        InboundOrder inboundOrder = new InboundOrder(inboundOrderResquest.getInboundOrder());
        validIfWarehouseExist(inboundOrder.getWarehouseCode());
        validAnnouncement(inboundOrderResquest.getInboundOrder().getBatchStock());
        validSection(inboundOrder.getSectionCode(), inboundOrderResquest.getInboundOrder().getBatchStock(), inboundOrder.getWarehouseCode());
        validBatchDueDate(inboundOrderResquest.getInboundOrder().getBatchStock());
        repo.save(inboundOrder);
        List<BatchStock> batchStockList =  inboundOrderResquest.getInboundOrder()
                .getBatchStock().stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
                .collect(Collectors.toList());
        return new InboundOrderPostResponseDTO( batchStockRepo.saveAll(batchStockList));
    }

    /**
     * Valid announcement.
     *
     * @param batchStockDTOList the batch stock dto list
     * @throws NotFoundException the not found exception
     */
    void validAnnouncement(List<BatchStockDTO> batchStockDTOList) throws NotFoundException {
        for (BatchStockDTO b : batchStockDTOList) {
            Optional<Announcement> announcementOptional = announcementRepo.findById(b.getAnnouncementId());
            if (announcementOptional.isEmpty()){
                throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
            }
            if(!Objects.equals(announcementOptional.get().getSection().getType(), b.getSectionType())){
                throw new ActionNotAllowedException(Msg.PRODUCT_BATCH_INCORRECT);
            }
        }
    }

    /**
     * Valid if inbound order exist.
     *
     * @param orderNumber the order number
     * @throws NotFoundException the not found exception
     */
    void validIfInboundOrderExist(Long orderNumber) throws NotFoundException {
      if (repo.findById(orderNumber).isEmpty()) {
        throw new NotFoundException(Msg.INBOUND_ORDER_NOT_FOUND);
      }
    }

    /**
     * Valid batch.
     *
     * @param batchStockDTOList the batch stock dto list
     * @throws NotFoundException the not found exception
     */
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

        validAnnouncement(inboundOrderResquest.getInboundOrder().getBatchStock());

        validSectionUpdate(inboundOrder.getSectionCode(), inboundOrderResquest.getInboundOrder().getBatchStock());

        validBatchDueDate(inboundOrderResquest.getInboundOrder().getBatchStock());

      InboundOrder inboundOrderUpdated = repo.save(inboundOrder);

      List<BatchStock> batchStockList = inboundOrderResquest.getInboundOrder()
              .getBatchStock().stream()
              .map(a -> new BatchStock(a, inboundOrder.getOrderNumber(), a.getBatchNumber()))
              .collect(Collectors.toList());
      List<BatchStock> batchStockListUpdated = batchStockRepo.saveAll(batchStockList);
      return new InboundOrderPutResponseDTO(inboundOrderUpdated, batchStockListUpdated);
    }
}

