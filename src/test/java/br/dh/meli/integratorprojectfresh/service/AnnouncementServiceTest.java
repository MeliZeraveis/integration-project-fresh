package br.dh.meli.integratorprojectfresh.service;
import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
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

    List<BatchSotckAnnoucementDTO> listBatchStock;

    Announcement announcement;

    BatchStock batchStock;

    SectionDTO sectionDTO;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "teste", "1234", "teste@email.com", "seller");
        Announcement announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, null, new ArrayList<>(), null, new ArrayList<>());
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");
        SectionDTO sectionDTO = new SectionDTO(1L, 1L);
        BatchSotckAnnoucementDTO batchStockDTO1 = new BatchSotckAnnoucementDTO(1L, 10, dueDate1);
        BatchSotckAnnoucementDTO batchStockDTO2 = new BatchSotckAnnoucementDTO(2L, 12, dueDate2);
        listBatchStock = List.of(batchStockDTO1, batchStockDTO2);
        AnnoucementGetResponseDTO responseDTO = new AnnoucementGetResponseDTO(sectionDTO, 1L, listBatchStock);

    }

    @Test
    void getAnnouncementByAnnouncementId() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        AnnoucementGetResponseDTO responseDTO = service.getAnnouncementByAnnouncementId(1L);
        assertThat(responseDTO).isNotNull();

    }

    @Test
    void findAnnouncementByBatchStockNumber() {
    }

    @Test
    void GetMethod_ThrowsException_WhenAnnoucementNotFound() {
       // BDDMockito.given(repository.findById(1L)).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));
        //assertThrows(NotFoundException.class, () -> service.getAnnouncementByAnnouncementId(1L));
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getAnnouncementByAnnouncementId(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }
}