package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.*;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;;

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

        BatchStockDTO batchStock1 = new BatchStockDTO(1L, "Fresh", 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate, BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock2 = new BatchStockDTO(1L, "Fresh", 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate, BigDecimal.valueOf(20.5));

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
        sectionTest = new Section(1L, "Fresh", (float) 100.0, (float) 80.0,1L ,new ArrayList<>(), new ArrayList<>(), null);
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(sectionTest);
        User user = new User(5L, "Test", "1234", "test@email.com", "manager");
        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), sectionList, user);
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), 1L, sectionTest, new ArrayList<>(), null, new ArrayList<>());
    }

    @Test
    @DisplayName("Sucesso ao criar novo pedido de entrada")
    void SaveMethod_ReturnNewInboundOrder_WhenParamsAreValid() {
        BDDMockito.when(inboundOrderRepo.save(ArgumentMatchers.any())).thenReturn(inboundOrder);
        BDDMockito.when(batchStockRepo.saveAll(ArgumentMatchers.any())).thenReturn(batchStockList2);
        BDDMockito.when(warehouseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(announcementRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementTest));

        assertThat(service.save(inboundOrderRequestDTO)).isNotNull();
    }

    @Test
    @DisplayName("Erro ao encontrar warehouse")
    void SaveMethod_ThrowsException_WhenWarehouseNotFound() throws NotFoundException {
        BDDMockito.given(warehouseRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> { service.save(inboundOrderRequestDTO); });
    }

    @Test
    @DisplayName("Erro ao encontrar anúncio")
    void SaveMethod_ThrowsException_WhenAnnouncementNotExists() throws NotFoundException {
        BDDMockito.when(warehouseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.given(announcementRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> { service.save(inboundOrderRequestDTO); });
    }

    @Test
    @DisplayName("Erro ao encontrar seção existente")
    void SaveMethod_ThrowsException_WhenSectionIsEmpty() throws NotFoundException {
        BDDMockito.when(warehouseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(announcementRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.given(sectionRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> { service.save(inboundOrderRequestDTO); });
    }

    @Test
    @DisplayName("Exceção quando limite da seção é ultrapassado")
    void SaveMethod_ThrowsExeption_WhenSectionExceededLimit() throws ActionNotAllowedException {
        batchStockList.get(0).setVolume(3000f);
        batchStockList.get(1).setVolume(5000f);

        BDDMockito.when(sectionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(sectionTest));

        assertThrows(ActionNotAllowedException.class, () -> { service.validSection(1L, batchStockList, 1L); });
    }

    @Test
    @DisplayName("Exceção quando seção não pertence ao armazém")
    void SaveMethod_ThrowsExeption_WhenSectionDoesntBelongToWareHouse() throws ActionNotAllowedException {
        sectionTest.setWarehouseCode(2L);

        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.any())).thenReturn(java.util.Optional.ofNullable(sectionTest));

        final var actualException = assertThrows(ActionNotAllowedException.class,
                () -> service.validSection(8L, batchStockList, 1L));

        assertAll(() -> Assertions.assertEquals(Msg.SECTION_NOT_BELONG_WAREHOUSE, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando warehouse não é encontrada")
    void IsValidWarehouseMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validIfWarehouseExist(1L));

        assertAll(() -> Assertions.assertEquals(Msg.WAREHOUSE_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando anuncio não é encontrado")
    void IsValidAnnouncementMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validAnnouncement(batchStockList));

        assertAll(() -> Assertions.assertEquals( Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando seção não é encontrada")
    void IsSectionMethodValid_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validSection(1L, batchStockList, 1L));

        assertAll(() -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Sucesso ao atualizar inboundOrder")
    void UpdateMethod_ReturnUpdatedInboundOrder_WhenParamsAreValid() {
        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        BDDMockito.when(batchStockRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(batchStockList2.get(0)));
        BDDMockito.when(batchStockRepo.findById(2L)).thenReturn(java.util.Optional.ofNullable(batchStockList2.get(1)));
        BDDMockito.when(inboundOrderRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(inboundOrder));
        BDDMockito.when(warehouseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(announcementRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.when(inboundOrderRepo.save(ArgumentMatchers.any())).thenReturn(inboundOrder);
        BDDMockito.when(batchStockRepo.saveAll(ArgumentMatchers.any())).thenReturn(batchStockList2);

        InboundOrderPutResponseDTO inboundOrderPutResponseDTO = service.update(inboundOrderRequestDTO);

        assertThat(inboundOrderPutResponseDTO).isNotNull();
        assertThat(inboundOrderPutResponseDTO.getBatchStock()).isNotNull();
        assertThat(inboundOrderPutResponseDTO.getOrderNumber()).isPositive();
    }

    @Test
    @DisplayName("Testa mensagem de erro do método ifInboundOrderExist")
    void ValidIfInboundOrderExistMethod_ThrowException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validIfInboundOrderExist(1L));

        assertAll(() -> Assertions.assertEquals(Msg.INBOUND_ORDER_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa metodo IsValidBatch quando id não é encontrado")
    void ValidBatchMethod_ThrowException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validBatch(batchStockList));

        assertAll(() -> Assertions.assertEquals( Msg.BATCH_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando o representante/gerente não é válido")
    void IsValidWarehouseMethod_ThrowsException_WhenManagerIsInvalid() throws ManagerNotValidException {
        warehouseTest.setManager(null);

        BDDMockito.when(warehouseRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(warehouseTest));

        final var actualException = assertThrows(ManagerNotValidException.class,
                () -> service.validIfWarehouseExist(1L));

        assertAll(() -> Assertions.assertEquals(Msg.MANAGER_NOT_VALID, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void IsSectionMethodValid_ThrowsException_WhenSectionTypeIsIncorrect() throws SectionTypeException {
        batchStockList.get(0).setSectionType("Refrigerated");

        BDDMockito.when(sectionRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(sectionTest));

        final var actualException = assertThrows(ActionNotAllowedException.class,
                () -> service.validSection(1L, batchStockList, 1L));

        assertAll(() -> Assertions.assertEquals(Msg.INSERT_BATCH_SECTION_INCORRET, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void ValidSectionBatchStockUpdate_ThrowsException_WhenSectionTypeIsIncorrect() throws SectionTypeException {
        batchStockList.get(0).setSectionType("Refrigerated");
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        final var actualException = assertThrows(SectionTypeException.class,
                () -> service.validSectionBatchStockUpdate(sectionTest, batchStockList));

        assertAll(() -> Assertions.assertEquals(Msg.INSERT_BATCH_SECTION_INCORRET, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void ValidSectionBatchStockUpdate_ThrowsException_WhenVolumeLimitExceeded() throws ActionNotAllowedException {
        batchStockList.get(0).setVolume(3000f);
        batchStockList.get(1).setVolume(5000f);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        BDDMockito.when(batchStockRepo.findById(1L)).thenReturn(Optional.ofNullable(batchStockList2.get(0)));

        final var actualException = assertThrows(ActionNotAllowedException.class,
                () -> service.validSectionBatchStockUpdate(sectionTest, batchStockList));

        assertAll(() -> Assertions.assertEquals(Msg.LIMIT_CAPACITY_SECTION, actualException.getMessage()));
    }

    @Test
    @DisplayName("Testa se a section existe")
    void ValidSectionUpdate_ThrowsException_WhenSectionNotFound() throws NotFoundException {
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        final var actualException = assertThrows(NotFoundException.class,
                () -> service.validSectionUpdate(1L, batchStockList));

        assertAll(() -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage()));
    }

    @Test
    @DisplayName("Exceção quando seção não pertence ao armazém")
    void SaveMethod_ThrowsExeption_WhenValidBatchDueDateIsInvalid() throws ActionNotAllowedException {
        batchStockList.get(0).setDueDate(LocalDate.now().plusDays(1));

        final var actualException = assertThrows(ActionNotAllowedException.class,
                () -> service.validBatchDueDate(batchStockList));

        assertAll(() -> Assertions.assertEquals(Msg.BATCH_EXPIRED, actualException.getMessage()));
    }
}