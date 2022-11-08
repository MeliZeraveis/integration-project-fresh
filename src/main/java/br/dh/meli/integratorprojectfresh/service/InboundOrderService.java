package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {


    private final InboundOrderRepository repo;
    @Override
    public InboundOrderResponseDTO save(InboundOrderRequestDTO inboundOrder) {
        InboundOrder inboundOrderSaved = repo.save(new InboundOrder(inboundOrder.getInboundOrder()));
        return new InboundOrderResponseDTO(inboundOrderSaved.getBatchStock());
    }
}

