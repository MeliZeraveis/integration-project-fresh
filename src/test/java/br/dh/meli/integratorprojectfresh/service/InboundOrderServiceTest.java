package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.LimitCapacitySectionException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
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
import static org.junit.Assert.assertThrows;;



@ExtendWith(MockitoExtension.class)
class InboundOrderServiceTest {

    @InjectMocks
    private InboundOrderService service;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private InboundOrderRepository inboundOrderRepo;
    @Mock
    private BatchStockRepository batchStockRepo;
    private BatchStockDTO batchStockDTO;
    InboundOrderPostResponseDTO inboundOrderPostResponseDTO;
    InboundOrderPutResponseDTO inboundOrderPutResponseDTO;
    InboundOrderRequestDTO inboundOrderRequestDTO;
    InboundOrder inboundOrder;
    List<BatchStockDTO> batchStockList;
    List<BatchStock> batchStockList2;
    InboundOrderDTO inboundOrderDTO;
    Warehouse warehouseTest;
    Section sectionTest;
    Announcement announcementTest;


    @BeforeEach
    void setup() {
        LocalDate manufacturingDate = LocalDate.parse("2022-03-09");
        LocalDate dueDate = LocalDate.parse("2023-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStockDTO batchStock1 = new BatchStockDTO("Fresh", 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate, BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock2 = new BatchStockDTO("Fresh", 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate, BigDecimal.valueOf(20.5));

        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        inboundOrderDTO = new InboundOrderDTO(orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrderDTO);

        inboundOrder = new InboundOrder(inboundOrderDTO, 1L);

        batchStockList2 = batchStockList.stream().map(a -> new BatchStock(a, inboundOrder.getOrderNumber())).collect(Collectors.toList());

        inboundOrderPostResponseDTO = new InboundOrderPostResponseDTO(batchStockList2);

        inboundOrderPutResponseDTO = new InboundOrderPutResponseDTO(inboundOrder, batchStockList2);


        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), null);
        sectionTest = new Section(1L, "Fresh", (float)100.0, (float)80.0, new ArrayList<>());
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), "Fresh", null, null, new ArrayList<>());
    }

    @Test
    void insert_returnNewInboundOrder_whenBatchStockIsCorrect() {

        BDDMockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrder);
        BDDMockito.when(batchStockRepo.saveAll(ArgumentMatchers.any()))
                .thenReturn(batchStockList2);
        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(announcementRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));

        InboundOrderPostResponseDTO inboundOrderPostTest = service.save(inboundOrderRequestDTO);

        assertThat(inboundOrderPostTest).isNotNull();
    }

    @Test
    void insert_returnException_whenWarehouseNotFound() throws NotFoundException {
        BDDMockito.given(warehouseRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });

//        InboundOrderPostResponseDTO inboundOrderPostTest = service.save(inboundOrderRequestDTO);

//        assertThat(inboundOrderPostTest).isNull();
    }

    @Test
    void insert_returnException_whenAnnouncementNotExists() throws NotFoundException {
//        BDDMockito.given(announcementRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    @Test
    void insert_returnException_whenSectionIsEmpty() throws NotFoundException {
//        BDDMockito.given(sectionRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

//    @Test
//    void insert_returnLimitCapacitySectionExeption_whenFilledInSection() throws LimitCapacitySectionException {
//
//        BDDMockito.when(warehouseRepository.findById(1L))
//                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
//        BDDMockito.when(sectionRepository.findById(1L))
//                .thenReturn(java.util.Optional.ofNullable(sectionTest));
//        BDDMockito.when(announcementRepository.findById(1L))
//                .thenReturn(java.util.Optional.ofNullable(announcementTest));
//
////        InboundOrderPostResponseDTO inboundOrderPostTest = service.save(inboundOrderRequestDTO);
////
////        assertThat(inboundOrderPostTest).isNotNull();
//
//        BDDMockito.given(sectionRepository.)
//                .willThrow(new LimitCapacitySectionException(Msg.LIMIT_CAPACITY_SECTION));
//        assertThrows(LimitCapacitySectionException.class, () -> {
//            service.save(inboundOrderRequestDTO);
//        });
//    }
//    @Test
//    void update_returnUpdatedInboundOrder_whenBatchStockIsCorrect() {
//
//        inboundOrderDTO.setOrderNumber(1L);
//        batchStockList.get(0).setBatchNumber(1L);
//        batchStockList.get(1).setBatchNumber(2L);
//
//        BDDMockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
//                .thenReturn(inboundOrder);
//        BDDMockito.when(batchStockRepo.saveAll(ArgumentMatchers.any()))
//                .thenReturn(batchStockList2);
//
//        InboundOrderPutResponseDTO inboundOrderPutResponseDTO = service.update(inboundOrderRequestDTO);
//
//        assertThat(inboundOrderPutResponseDTO).isNotNull();
//        assertThat(inboundOrderPutResponseDTO.getBatchStock()).isNotNull();
//        assertThat(inboundOrderPutResponseDTO.getOrderNumber()).isPositive();
//
//    }
//
//    @Test
//    void insert_returnInvalidParamException_whenAnnouncementNotValid() {
//
//    }
//
//    @Test
//    void insert_returnInvalidParamException_whenWarehouseNotValid() {
//
//    }
//
////    @Test
////    void insert_returnInvalidParamException_whenManagerNotBelongToCorrectWarehouse() {
////
////    }
//
//    @Test
//    void insert_returnInvalidParamException_whenSectionIsNotValid() {
//
//    }
//
//    @Test
//    void insert_returnInvalidParamException_whenSectionNotBelongToCorrectProduct() {
//
//    }
//

//
//
//
//
}
