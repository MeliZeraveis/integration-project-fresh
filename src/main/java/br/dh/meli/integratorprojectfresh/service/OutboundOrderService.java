package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.OutboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.OutboundOrderBatches;
import br.dh.meli.integratorprojectfresh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Outbound orders service class that implements the IOutboundOrdersService interface.
 */
@Service
@RequiredArgsConstructor
public class OutboundOrderService implements IOutboundOrderService {
  private final AnnouncementRepository announcementRepo;
  private final OutboundOrderBatchesRepository batchRepo;
  private final OutboundOrderRepository orderRepo;
  private final SectionRepository sectionRepo;
  private final WarehouseRepository warehouseRepo;

  @Override
  public BatchStockGetResponseDTO read(Long id) {
    List<OutboundOrderBatches> outboundOrderBatches = batchRepo.findAllByOrderNumberId(id);
    if (outboundOrderBatches.isEmpty()) {
      throw new NotFoundException(Msg.OUTBOUND_ORDER_BATCHES_NOT_FOUND);
    }

    List<BatchStock> batches = outboundOrderBatches.stream()
            .map(OutboundOrderBatches::toBatchStock)
            .collect(Collectors.toList());
    return new BatchStockGetResponseDTO(batches);
  }

  @Override
  public OutboundOrderPostResponseDTO save(OutboundOrderRequestDTO outboundOrder) {
    return new OutboundOrderPostResponseDTO();
  }

  @Override
  public OutboundOrderPutResponseDTO update(OutboundOrderRequestDTO outboundOrder) {
    return new OutboundOrderPutResponseDTO();
  }
}

