package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {


    private final InboundOrderRepository repo;
    private  final BatchStockRepository batchStockRepo;
    @Override
    public InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrderResquest) {

        InboundOrder inboundOrder = new InboundOrder(inboundOrderResquest.getInboundOrder());
        repo.save(inboundOrder);
        List<BatchStock> batchStockList =  inboundOrderResquest.getInboundOrder()
                .getBatchStock().stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
                .collect(Collectors.toList());
        batchStockRepo.saveAll(batchStockList);

        return new InboundOrderPostResponseDTO(  batchStockRepo.saveAll(batchStockList).stream()
                .map(BatchStockDTO::new)
                .collect(Collectors.toList()));
    }

    @Override
    public InboundOrderPutResponseDTO update(InboundOrderRequestDTO inboundOrderResquest) {
        InboundOrder inboundOrder = new InboundOrder(inboundOrderResquest.getInboundOrder(),
                inboundOrderResquest.getInboundOrder().getOrderNumber());
        if (repo.findById(inboundOrder.getOrderNumber()).isEmpty()) {
            throw new NotFoundException(Msg.INBOUND_ORDER_NOT_FOUND);
        }
       for (BatchStockDTO batchStockDTO : inboundOrderResquest.getInboundOrder().getBatchStock()) {
           Optional<BatchStock> batchStock = batchStockRepo.findById(batchStockDTO.getBatchNumber());
           if (batchStock.isEmpty()) {
               throw new NotFoundException(Msg.BATCH_NOT_FOUND);
           }
       }
        InboundOrder inboundOrderUpdated = repo.save(inboundOrder);
        List<BatchStock> batchStockList = inboundOrderResquest.getInboundOrder()
                .getBatchStock().stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber(), a.getBatchNumber()))
                .collect(Collectors.toList());
         List<BatchStockDTO> batchStockListUpdated = batchStockRepo.saveAll(batchStockList).stream()
                .map(a ->new BatchStockDTO(a))
                .collect(Collectors.toList());
        return new InboundOrderPutResponseDTO(inboundOrderUpdated, batchStockListUpdated);
    }
}

