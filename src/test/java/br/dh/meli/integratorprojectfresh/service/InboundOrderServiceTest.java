package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
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
    private InboundOrder inboundOrder;
    List<BatchStockDTO> batchStockList;

    @BeforeEach
    void setup() {
        LocalDate manufacturingDate = LocalDate.parse("2022-03-09");
        LocalDate dueDate = LocalDate.parse("2023-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStockDTO batchStock1 = new BatchStockDTO( 1L, (float) 1.05, 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate, BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock2 = new BatchStockDTO( 2L, (float) 2.05, 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate, BigDecimal.valueOf(20.5));

        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        InboundOrderDTO inboundOrderDTO = new InboundOrderDTO(1L, orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrderDTO);

        inboundOrderPostResponseDTO = new InboundOrderPostResponseDTO(batchStockList);

//        InboundOrder inboundOrder = new InboundOrder(inboundOrderDTO, 1L); - é para o PUT!?

//        List<BatchStock> batchStockList1 = batchStockList.stream()
//                .map(a -> new BatchStock(a, inboundOrderDTO.getOrderNumber()))
//                .collect(Collectors.toList());
    }

    @Test
    void insert_returnNewInboundOrder_whenBatchStockIsCorrect() {

        Mockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrderPostResponseDTO);

        InboundOrderPostResponseDTO inboundOrderPostTest = service.save(inboundOrderRequestDTO);

        assertThat(inboundOrderPostTest.getBatchStock());

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
