package br.dh.meli.integratorprojectfresh.service;
import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {
    @InjectMocks
    private AnnouncementService service;

    @Mock
    private AnnouncementRepository repository;

    AnnoucementGetResponseDTO responseDTO;

    List<BatchSotckAnnoucementDTO> batchStockList;

    Announcement announcement;

    BatchStock batchStock;

    SectionDTO sectionDTO;

    Section section;

    @BeforeEach
    void setUp() {
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");

        User user = new User(1L, "teste", "1234", "teste@email.com", "seller");
        section = new Section(1L, "Fresh", 50.0f, 20.0f, null, null);
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, new ArrayList<>(), null, new ArrayList<>());
        sectionDTO = new SectionDTO(1L, 1L);
        BatchSotckAnnoucementDTO batchStockDTO1 = new BatchSotckAnnoucementDTO(1L, 10, dueDate1);
        BatchSotckAnnoucementDTO batchStockDTO2 = new BatchSotckAnnoucementDTO(2L, 12, dueDate2);
        batchStockList = List.of(batchStockDTO1, batchStockDTO2);
        responseDTO = new AnnoucementGetResponseDTO(sectionDTO, 1L, batchStockList);

    }

    @Test
    void getAnnouncementByAnnouncementId() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        responseDTO = service.getAnnouncementByAnnouncementId(1L);
        assertThat(responseDTO).isNotNull();

    }

    @Test
    void findAnnouncementByBatchStockNumber() {
    }

    @Test
    void GetMethod_ThrowsException_WhenAnnoucementNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getAnnouncementByAnnouncementId(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }
}