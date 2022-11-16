package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockDTOResponseDueDate;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.service.BatchStockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;;


@ExtendWith(MockitoExtension.class)
class BatchStockServiceTest {

    @InjectMocks
    private BatchStockService service;

    @Mock
    private BatchStockRepository repo;

    BatchStockDTOResponseDueDate batchStockDTOResponseDueDate;
    BatchStockGetResponseDTO batchStockGetResponseDTO;
    BatchStock batchStock;
    InboundOrder inboundOrder;
    Warehouse warehouse;
    Section section;
    Announcement announcement;
    List<BatchStock> batchStockList;

    @BeforeEach
    void setUp() {
        LocalDate manufacturingDate = LocalDate.parse("2021-12-12");
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");
        LocalDate dueDate3 = LocalDate.parse("2021-11-11");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null );
        section = new Section(1L, "Fresh", 50.0f, 20.0f, null, null);

        inboundOrder = new InboundOrder(1L, manufacturingDate, 1L, 1L,warehouse, null, section );

        batchStock = new BatchStock(1L, 1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50), inboundOrder);
        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        section = new Section(1L, "Fresh", 50.0f, 20.0f, null, null);
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList, null, new ArrayList<>());
//        batchStockGetResponseDTO = new BatchStockGetResponseDTO(batchStockList2, "");
//        batchStockDTOResponseDueDate = new BatchStockDTOResponseDueDate(batchStock);

    }

    @Test
    void GetBatchStockDueDate_ReturnBatchStockFiltered_WhenParamsAreValid() {
        BDDMockito.when(repo.findAllByDueDateBetweenAndSectionType(LocalDate.now(), LocalDate.now().plusDays(45), "Fresh")).thenReturn(batchStockList);
        BatchStockGetResponseDTO batchStockGetResponseDTOList = service.getBatchStockByBatchStockId(45, "Fresh");
assertAll(
                () -> assertThat(batchStockGetResponseDTOList).isNotNull(),
                () -> assertThat(batchStockGetResponseDTOList.getBatchStock()).isNotNull()
        );
    }

    @Test
    void GetBatchStockDueDate_ThrowException_WhenBatchStockListIsEmpty() throws NotFoundException {

//        BDDMockito.given(repo.findAllByDueDateBetweenAndSectionType(LocalDate.now(), LocalDate.now().plusDays(45), "Fresh")).willThrow(new NotFoundException(Msg.BATCH_NOT_FOUND));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getBatchStockByBatchStockId(45, "Fresh"));
        assertAll(
                () -> Assertions.assertEquals(Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchFiltered_ReturnBatchStockFiltered_WhenParamsAreValid() {
        BDDMockito.when(repo.findAllByDueDateBetween(LocalDate.now(), LocalDate.now().plusDays(45))).thenReturn(batchStockList);

        BatchStockGetResponseDTO batchStockGetResponseDTOList = service.findBatchStockByBatchStockNumber(45, "FS");
        assertAll(
                () -> assertThat(batchStockGetResponseDTOList).isNotNull(),
                () -> assertThat(batchStockGetResponseDTOList.getBatchStock()).isNotNull()
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenBatchStockListIsEmpty() throws NotFoundException {

//        BDDMockito.given(repo.findAllByDueDateBetween(LocalDate.now(), LocalDate.now().plusDays(45))).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "FS"));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenBatchStockList() throws NotFoundException {

        BDDMockito.given(repo.findAllByDueDateBetween(LocalDate.now(), LocalDate.now().plusDays(45))).willThrow(new NotFoundException(Msg.CATEGORY_NOT_FOUND));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "XX"));
        assertAll(
                () -> Assertions.assertEquals(Msg.CATEGORY_NOT_FOUND, actualException.getMessage())
        );
    }



}