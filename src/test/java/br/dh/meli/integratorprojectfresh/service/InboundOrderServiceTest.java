package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceTest {

    @InjectMocks
    private InboundOrderService service;

    @Mock
    private InboundOrderRepository inboundOrderRepo;
    private BatchStockRepository batchStockRepo;
    private BatchStockDTO batchStockDTO;
    private InboundOrderPostResponseDTO inboundOrderPostResponseDTO;
    private InboundOrderPutResponseDTO inboundOrderPutResponseDTO;
    private InboundOrderRequestDTO inboundOrderRequestDTO;
    private List<BatchStock> batchStockList;
    private InboundOrder inboundOrder;

    @BeforeEach
    void setup() {
        LocalDate manufacturingDate = LocalDate.parse("2022-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStock batchStock1 = new BatchStock(1L, (float) 1.05, 10, manufacturingDate, manufacturingTime, (float) 1.5, manufacturingDate, BigDecimal.valueOf(30.5));
        BatchStock batchStock2 = new BatchStock(2L, (float) 2.05, 11, manufacturingDate, manufacturingTime, (float) 1.3, manufacturingDate, BigDecimal.valueOf(20.5));

        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        inboundOrder = new InboundOrder(1L, orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrder);

        List<BatchStockDTO> batchStockDTO = batchStockList.stream()
                .map(BatchStockDTO::new)
                .collect(Collectors.toList());
    }

    @Test
    void insert_returnNewInboundOrder_whenBatchStockIsCorrect() {

        Mockito.when(inboundOrderRepo.save(ArgumentMatchers.any(InboundOrder.class)))
                .thenReturn(inboundOrder);

        Mockito.when(batchStockRepo.saveAll(batchStockList))
                .thenReturn(batchStockList);

        InboundOrderPostResponseDTO inboundOrderPostTest = service.save(inboundOrderRequestDTO);

        assertThat(inboundOrderPostTest).isNotNull();



    }

    @Test
    void insert_returnInvalidParamException_whenAnnouncementNotValid() {

    }

    @Test
    void insert_returnInvalidParamException_whenWarehouseNotValid() {

    }

//    @Test
//    void insert_returnInvalidParamException_whenManagerNotBelongToCorrectWarehouse() {
//
//    }

    @Test
    void insert_returnInvalidParamException_whenSectionIsNotValid() {

    }

    @Test
    void insert_returnInvalidParamException_whenSectionNotBelongToCorrectProduct() {

    }

    @Test
    void insert_returnFilleInSectionException_whenFilledInSection() {

    }




}
