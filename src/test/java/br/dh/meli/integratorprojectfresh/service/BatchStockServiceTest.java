
package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockDTOResponseDueDate;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;;


@ExtendWith(MockitoExtension.class)
class BatchStockServiceTest {

    @InjectMocks
    private IBatchStockService.BatchStockService service;

    @Mock
    private BatchStockRepository repo;

    @Mock
    SectionRepository sectionRepo;

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
        LocalDate dueDate3 = LocalDate.parse("2022-12-30");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null, null);

        batchStock = new BatchStock(1L, 1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50), null);
        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        inboundOrder = new InboundOrder(1L, manufacturingDate, 1L, 1L,warehouse, batchStockList, section );
        section = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), List.of(inboundOrder), null );

        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList, null, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void GetBatchStockDueDate_ReturnBatchStockFiltered_WhenParamsAreValid() {
        BDDMockito.when(sectionRepo.findById(1L)).thenReturn(Optional.ofNullable(section));
        batchStockGetResponseDTO = service.getBatchStockByBatchStockId(1000, 1L);
assertAll(
                () -> assertThat(batchStockGetResponseDTO).isNotNull(),
                () -> assertThat(batchStockGetResponseDTO.getBatchStock()).isNotNull()
        );
    }

    @Test
    void GetBatchStockDueDate_ThrowException_WhenBatchStockListIsEmpty() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getBatchStockByBatchStockId(45, 1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void GetBatchStockDueDate_ThrowException_WhenSectionIsEmpty() throws NotFoundException {

        section.setInboundOrder(new ArrayList<>());
        BDDMockito.when(sectionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(section));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getBatchStockByBatchStockId(45, 1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void GetBatchStockDueDate_ThrowException_WhenBatchStockLisFilteredIsEmpty() throws NotFoundException {
        section.getInboundOrder().get(0).setBatchStock(new ArrayList<>());
        BDDMockito.when(sectionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(section));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getBatchStockByBatchStockId(45, 1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchFiltered_ReturnBatchStockFiltered_WhenCategoryFSIsValid() {
        BDDMockito.when(repo.findAllByDueDateBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(batchStockList);

        batchStockGetResponseDTO = service.findBatchStockByBatchStockNumber(45, "FS", "asc");
        assertAll(
                () -> assertThat(batchStockGetResponseDTO).isNotNull(),
                () -> assertThat(batchStockGetResponseDTO.getBatchStock()).isNotNull()
        );
    }

    @Test
    void FindBatchFiltered_ReturnBatchStockFiltered_WhenCategoryRFIsValid() {
        BDDMockito.when(repo.findAllByDueDateBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(batchStockList);

        batchStockGetResponseDTO = service.findBatchStockByBatchStockNumber(45, "RF", "asc");
        assertAll(
                () -> assertThat(batchStockGetResponseDTO).isNotNull(),
                () -> assertThat(batchStockGetResponseDTO.getBatchStock()).isNotNull()
        );
    }

    @Test
    void FindBatchFiltered_ReturnBatchStockFiltered_WhenCategoryFFIsValid() {
        BDDMockito.when(repo.findAllByDueDateBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(batchStockList);

        batchStockGetResponseDTO = service.findBatchStockByBatchStockNumber(45, "FF", "asc");
        assertAll(
                () -> assertThat(batchStockGetResponseDTO).isNotNull(),
                () -> assertThat(batchStockGetResponseDTO.getBatchStock()).isNotNull()
        );
    }

    @Test
    void FindBatchFiltered_ReturnBatchStockFiltered_WhenOrderDescIsValid() {
        BDDMockito.when(repo.findAllByDueDateBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(batchStockList);

        batchStockGetResponseDTO = service.findBatchStockByBatchStockNumber(45, "FF", "desc");
        assertAll(
                () -> assertThat(batchStockGetResponseDTO).isNotNull(),
                () -> assertThat(batchStockGetResponseDTO.getBatchStock()).isNotNull()
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenBatchStockListIsEmpty() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "FS", "asc"));
        assertAll(
                () -> Assertions.assertEquals(Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenCategoryIsValid() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "FS", "asc"));
        assertAll(
                () -> Assertions.assertEquals(Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenBatchStockList() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "XX", "asc"));
        assertAll(
                () -> Assertions.assertEquals(Msg.CATEGORY_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenOrderIsInvalid() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "XX", "asc"));
        assertAll(
                () -> Assertions.assertEquals(Msg.CATEGORY_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    void FindBatchStockFiltered_ThrowException_WhenOrderIsDesc() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findBatchStockByBatchStockNumber(45, "ssssssssss", "desc"));
        assertAll(
                () -> Assertions.assertEquals(Msg.CATEGORY_NOT_FOUND, actualException.getMessage())
        );
    }
}