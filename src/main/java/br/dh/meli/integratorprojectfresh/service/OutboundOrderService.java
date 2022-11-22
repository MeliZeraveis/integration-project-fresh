package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.OutboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.exception.ManagerNotValidException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Outbound orders service class that implements the IOutboundOrdersService interface.
 */
@Service
@RequiredArgsConstructor
public class OutboundOrderService implements IOutboundOrderService {
  private final BatchStockRepository batchStockRepo;
  private final OutboundOrderBatchesRepository batchRepo;
  private final OutboundOrderRepository orderRepo;
  private final SectionRepository sectionRepo;
  private final WarehouseRepository warehouseRepo;

  @Override
  public BatchStockGetResponseDTO read(Long id) {
    List<OutboundOrderBatches> outboundOrderBatches = batchRepo.findAllByOrderNumberId(id);
    if (outboundOrderBatches.isEmpty()) {
      throw new NotFoundException(Msg.OUTBOUND_ORDER_BATCH_NOT_FOUND);
    }

    List<BatchStock> batches = outboundOrderBatches.stream()
            .map(OutboundOrderBatches::toBatchStock)
            .collect(Collectors.toList());
    return new BatchStockGetResponseDTO(batches);
  }

  @Override
  public OutboundOrderResponseDTO save(OutboundOrderRequestDTO outboundOrder) {
    // Validate warehouse and manager
    Warehouse warehouse = warehouseRepo.findById(outboundOrder.getOutboundOrder().getWarehouseCode())
            .orElseThrow(() -> new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));
    if (warehouse.getManager() == null || !Objects.equals(warehouse.getManager().getRole(), Roles.MANAGER)){
      throw new ManagerNotValidException(Msg.MANAGER_NOT_VALID);
    }

    // Validate section
    Section section = sectionRepo.findById(outboundOrder.getOutboundOrder().getSectionCode())
            .orElseThrow(() -> new NotFoundException(Msg.SECTION_NOT_FOUND));
    if (section.getWarehouseCode() == null || !Objects.equals(section.getWarehouseCode(), warehouse.getWarehouseCode())) {
      throw new NotFoundException(Msg.SECTION_NOT_BELONG_WAREHOUSE);
    }

    // Create new outbound order and outbound order batches
    List<OutboundOrderBatches> batches = new ArrayList<>();
    outboundOrder.getOutboundOrder().getBatchIds().forEach(id -> {
      BatchStock batchStock = batchStockRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.BATCH_NOT_FOUND));
      if (batchStock.getDueDate().isAfter(LocalDate.now())) {
        throw new NotFoundException(Msg.BATCH_NOT_EXPIRED);
      }
      batches.add(new OutboundOrderBatches(batchStock));
    });

    // Save the outbound order to the database, and remove all expired batches from the batch stock table
    OutboundOrder order = orderRepo.save(new OutboundOrder(outboundOrder.getOutboundOrder()));
    batches.forEach(batch -> batch.setOrderNumberId(order.getOrderNumber()));
    batchStockRepo.deleteAllById(outboundOrder.getOutboundOrder().getBatchIds());
    batchRepo.saveAll(batches);

    return new OutboundOrderResponseDTO(order, batches);
  }

  @Override
  public OutboundOrderResponseDTO update(OutboundOrderRequestDTO outboundOrder) {
    OutboundOrder order = orderRepo.findById(outboundOrder.getOutboundOrder().getOrderNumber())
            .orElseThrow(() -> new NotFoundException(Msg.OUTBOUND_ORDER_NOT_FOUND));
    return save(outboundOrder);
  }
}

