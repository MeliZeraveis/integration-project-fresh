package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @InjectMocks
    private WarehouseService service;
    @Mock
    private AnnouncementRepository announcementRepo;
    InboundOrder inboundOrder;
    Announcement announcement;
    BatchStock batchStock;
    Section section;
    Warehouse warehouse;
    WarehouseProductQuantityGetResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        LocalDate manufacturingDate = LocalDate.parse("2021-12-12");
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");
        LocalDate dueDate3 = LocalDate.parse("2021-11-11");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null, null );

        inboundOrder = new InboundOrder(1L, manufacturingDate, 1L, 1L,warehouse, null, section );

        batchStock = new BatchStock(1L, 1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50), inboundOrder);
        List<BatchStock> batchStockList2 = new ArrayList<>();
        batchStockList2.add(batchStock);

        section = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), List.of(inboundOrder), null );

        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, null, new ArrayList<>());

        responseDTO = new WarehouseProductQuantityGetResponseDTO(announcement);
    }

    @Test
    @DisplayName("teste-true")
    void GetWarehouseProductQuantityList_whenAnnouncementIdIsValid() {
        BDDMockito.when(announcementRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        assertThat(service.getWarehouseProductsQuantityListByAnnoucementId(1L)).isNotNull();
    }

    @Test
    @DisplayName("test-false")
    void GetAnnouncementByAnnouncementId_ThrowsException_WhenAnnoucementNotFound() {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.getWarehouseProductsQuantityListByAnnoucementId(99L));

        assertAll(() -> Assertions.assertEquals(Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage()));
    }
}