package br.dh.meli.integratorprojectfresh.service;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Announcement service test.
 */
@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {
    @InjectMocks
    private IBatchStockService.AnnouncementService service;

    @Mock
    private AnnouncementRepository repository;

    /**
     * The Response dto.
     */
    AnnouncementGetResponseDTO responseDTO;
    /**
     * The Inbound order.
     */
    InboundOrder inboundOrder;
    /**
     * The Announcement.
     */
    Announcement announcement;
    /**
     * The Batch stock.
     */
    BatchStock batchStock;
    /**
     * The Section.
     */
    Section section;
    /**
     * The Warehouse.
     */
    Warehouse warehouse;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
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
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, null, new ArrayList<>(), new ArrayList<>());

        responseDTO = new AnnouncementGetResponseDTO(announcement);

    }

    /**
     * Get announcement by announcement id return new announcement when id is valid.
     */
    @Test
    @DisplayName("Sucesso ao retornar uma lista de anúncios")
    void GetAnnouncementByAnnouncementId_ReturnNewAnnouncement_WhenIdIsValid() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        responseDTO = service.getAnnouncementByAnnouncementId(1L);
        assertThat(responseDTO).isNotNull();

    }

    /**
     * Find announcement by batch stock number return batch stock number filter when id and letter are valid.
     */
    @Test
    @DisplayName("Sucesso ao retornar BatchStockNumber")
    void FindAnnouncementByBatchStockNumber_ReturnBatchStockNumberFilter_WhenIdAndLetterAreValid() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        responseDTO = service.findAnnouncementByBatchStockNumber(1L,'Q');
        assertThat(responseDTO).isNotNull();
    }

    /**
     * Get announcement by announcement id throws exception when annoucement not found.
     */
    @Test
    @DisplayName("Erro quando Announcement não existe")
    void GetAnnouncementByAnnouncementId_ThrowsException_WhenAnnoucementNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getAnnouncementByAnnouncementId(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Find announcement by batch stock number throws exception when annoucement not found.
     */
    @Test
    @DisplayName("Erro quando Announcement não existe")
    void FindAnnouncementByBatchStockNumber_ThrowsException_WhenAnnoucementNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByBatchStockNumber(1L,'Q'));
        assertAll(
                () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_IS_EMPTY, actualException.getMessage())
        );
    }

    /**
     * Find announcement by batch stock number throws exception when letra not found.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Erro quando filtro não existe")
    void FindAnnouncementByBatchStockNumber_ThrowsException_WhenLetraNotFound() throws NotFoundException {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByBatchStockNumber(1L,'A'));
        assertAll(
                () -> Assertions.assertEquals(Msg.LETTER_NOT_VALID, actualException.getMessage())
        );
    }

}