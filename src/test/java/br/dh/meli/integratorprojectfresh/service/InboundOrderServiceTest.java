package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.InvalidParamException;
import br.dh.meli.integratorprojectfresh.exception.LimitCapacitySectionException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;;

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

        batchStockList.get(0).setAnnouncementId(1L);
        batchStockList.get(1).setAnnouncementId(1L);

        User user = new User(5L, "Test", "1234", "test@email.com", "manager");
        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), user);
        sectionTest = new Section(1L, "Fresh", (float)100.0, (float)80.0, new ArrayList<>());
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), "Fresh", null, null, new ArrayList<>());
    }

    @Test
    @DisplayName("Sucesso ao criar novo pedido de entrada")
    void SaveMethod_ReturnNewInboundOrder_WhenParamsAreValid() {
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
    @DisplayName("Erro ao encontrar warehouse")
    void SaveMethod_ThrowsException_WhenWarehouseNotFound() throws NotFoundException {
        BDDMockito.given(warehouseRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    @Test
    @DisplayName("Erro ao encontrar anuncio")
    void SaveMethod_ThrowsException_WhenAnnouncementNotExists() throws NotFoundException {
        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));

        BDDMockito.given(announcementRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    @Test
    @DisplayName("Erro ao encontrar seção existente")
    void SaveMethod_ThrowsException_WhenSectionIsEmpty() throws NotFoundException {
        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(announcementRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.given(sectionRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    @Test
    @DisplayName("Exceção quando limite da seção é ultrapassado")
    void SaveMethod_ThrowsExeption_WhenSectionExceededLimit() throws LimitCapacitySectionException {

        batchStockList.get(0).setProductQuantity(3000);

        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(announcementRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));

        assertThrows(LimitCapacitySectionException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando warehouse não é encontrada")
    void IsValidWarehouseMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        final String expectedMessage = "Warehouse not found.";
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validIfWarehouseExist(1L));
            assertAll(
                    () -> Assertions.assertEquals(expectedMessage, Msg.WAREHOUSE_NOT_FOUND)
            );
    }
    @Test
    @DisplayName("Testa mensagem da exceção quando anuncio não é encontrada")
    void IsValidAnnouncementMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
    BDDMockito.when(announcementRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        final String expectedMessage = "Announcement not found.";
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validIfAnnouncementExist(batchStockList));
                    assertAll(
                            () -> Assertions.assertEquals(expectedMessage, Msg.ANNOUNCEMENT_NOT_FOUND)
                    );
    }
    @Test
    @DisplayName("Testa mensagem da exceção quando seção não é encontrada")
    void IsSectionMethodValid_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        final String expectedMessage = "Section not found.";
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validSection(1L, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(expectedMessage, Msg.SECTION_NOT_FOUND)
        );
    }

}
