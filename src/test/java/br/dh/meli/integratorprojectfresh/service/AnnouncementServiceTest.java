package br.dh.meli.integratorprojectfresh.service;
import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    InboundOrderDTO inboundOrderDTO;
    InboundOrder inboundOrder;
    List<BatchStockDTO> batchStockListDTO;


    Announcement announcement;

    BatchStock batchStock;

    SectionDTO sectionDTO;

    Section section;

    Warehouse warehouse;

    @BeforeEach
    void setUp() {
        LocalDate manufacturingDate = LocalDate.parse("2021-12-12");
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");
        LocalDate dueDate3 = LocalDate.parse("2021-11-11");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //User user1 = new User(1L, "teste", "1234", "teste@email.com", "seller");
        //User user2 = new User(5L, "teste", "1234", "teste@email.com", "manager");

        BatchStockDTO batchStock1 = new BatchStockDTO(1L, "Fresh", 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate1, BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock2 = new BatchStockDTO(1L, "Fresh", 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate2, BigDecimal.valueOf(20.5));

        batchStockListDTO = new ArrayList<>();
        batchStockListDTO.add(batchStock1);
        batchStockListDTO.add(batchStock2);



        inboundOrderDTO = new InboundOrderDTO(orderDate, 1L, 1L, batchStockListDTO);
        inboundOrder = new InboundOrder(inboundOrderDTO, 1L);




        batchStock = new BatchStock(1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50));
        List<BatchStock> batchStockList2 = new ArrayList<>();
        batchStockList2.add(batchStock);
        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null );
        section = new Section(1L, "Fresh", 50.0f, 20.0f, null, null);
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, null, new ArrayList<>());
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