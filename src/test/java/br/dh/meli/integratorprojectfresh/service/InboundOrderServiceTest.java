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

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;;

/**
 * The type Inbound order service test.
 */
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
    /**
     * The Inbound order post response dto.
     */
    InboundOrderPostResponseDTO inboundOrderPostResponseDTO;
    /**
     * The Inbound order put response dto.
     */
    InboundOrderPutResponseDTO inboundOrderPutResponseDTO;
    /**
     * The Inbound order request dto.
     */
    InboundOrderRequestDTO inboundOrderRequestDTO;
    /**
     * The Inbound order.
     */
    InboundOrder inboundOrder;
    /**
     * The Batch stock list.
     */
    List<BatchStockDTO> batchStockList;
    /**
     * The Batch stock list 2.
     */
    List<BatchStock> batchStockList2;
    /**
     * The Inbound order dto.
     */
    InboundOrderDTO inboundOrderDTO;
    /**
     * The Warehouse test.
     */
    Warehouse warehouseTest;
    /**
     * The Section test.
     */
    Section sectionTest;
    /**
     * The Announcement test.
     */
    Announcement announcementTest;


    /**
     * Sets .
     */
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

        User user = new User(5L, "Test", "1234", "test@email.com", "manager");
        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), user);
        sectionTest = new Section(1L, "Fresh", (float) 100.0, (float) 80.0, new ArrayList<>());
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), 1L, null, new ArrayList<>(), null, new ArrayList<>());
    }

    /**
     * Save method return new inbound order when params are valid.
     */
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

    /**
     * Save method throws exception when warehouse not found.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Erro ao encontrar warehouse")
    void SaveMethod_ThrowsException_WhenWarehouseNotFound() throws NotFoundException {
        BDDMockito.given(warehouseRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(inboundOrderRequestDTO);
        });
    }

    /**
     * Save method throws exception when announcement not exists.
     *
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Save method throws exception when section is empty.
     *
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Save method throws exeption when section exceeded limit.
     *
     * @throws LimitCapacitySectionException the limit capacity section exception
     */
    @Test
    @DisplayName("Exceção quando limite da seção é ultrapassado")
    void SaveMethod_ThrowsExeption_WhenSectionExceededLimit() throws LimitCapacitySectionException {

        batchStockList.get(0).setVolume(3000f);
        batchStockList.get(1).setVolume(5000f);

        BDDMockito.when(sectionRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));

        assertThrows(LimitCapacitySectionException.class, () -> {
            service.validSection(1L, batchStockList);
        });
    }

    /**
     * Is valid warehouse method throws exception when id is invalid.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando warehouse não é encontrada")
    void IsValidWarehouseMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validIfWarehouseExist(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.WAREHOUSE_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Is valid announcement method throws exception when id is invalid.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando anuncio não é encontrada")
    void IsValidAnnouncementMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validIfAnnouncementExist(batchStockList));
        assertAll(
                () -> Assertions.assertEquals( Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Is section method valid throws exception when id is invalid.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando seção não é encontrada")
    void IsSectionMethodValid_ThrowsException_WhenIdIsInvalid() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validSection(1L, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Update method return updated inbound order when params are valid.
     */
    @Test
    @DisplayName("Sucesso ao atualizar inboundOrder")
    void UpdateMethod_ReturnUpdatedInboundOrder_WhenParamsAreValid() {
        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        BDDMockito.when(batchStockRepo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(batchStockList2.get(0)));
        BDDMockito.when(batchStockRepo.findById(2L))
                .thenReturn(java.util.Optional.ofNullable(batchStockList2.get(1)));
        BDDMockito.when(inboundOrderRepo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(inboundOrder));

        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));
        BDDMockito.when(sectionRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));
        BDDMockito.when(announcementRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrder);
        BDDMockito.when(batchStockRepo.saveAll(ArgumentMatchers.any()))
                .thenReturn(batchStockList2);

        InboundOrderPutResponseDTO inboundOrderPutResponseDTO = service.update(inboundOrderRequestDTO);

        assertThat(inboundOrderPutResponseDTO).isNotNull();
        assertThat(inboundOrderPutResponseDTO.getBatchStock()).isNotNull();
        assertThat(inboundOrderPutResponseDTO.getOrderNumber()).isPositive();

    }

    /**
     * Valid if inbound order exist method throw exception when id is invalid.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa mensagem de erro do metodo ifInboundOrderExist")
    void ValidIfInboundOrderExistMethod_ThrowException_WhenIdIsInvalid() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validIfInboundOrderExist(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.INBOUND_ORDER_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Valid batch method throw exception when id is invalid.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa metodo IsValidBatch quando id não é encontrado")
    void ValidBatchMethod_ThrowException_WhenIdIsInvalid() throws NotFoundException {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validBatch(batchStockList));
        assertAll(
                () -> Assertions.assertEquals( Msg.BATCH_NOT_FOUND, actualException.getMessage())
        );
    }

    /**
     * Is valid warehouse method throws exception when manager is invalid.
     *
     * @throws ManagerNotValidException the manager not valid exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando o representante/gerente não é valido")
    void IsValidWarehouseMethod_ThrowsException_WhenManagerIsInvalid() throws ManagerNotValidException {
        warehouseTest.setManager(null);

        BDDMockito.when(warehouseRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(warehouseTest));

        final var actualException = assertThrows(
                ManagerNotValidException.class,
                () -> service.validIfWarehouseExist(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.MANAGER_NOT_VALID, actualException.getMessage())
        );
    }

    /**
     * Is section method valid throws exception when section type is incorrect.
     *
     * @throws SectionTypeException the section type exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void IsSectionMethodValid_ThrowsException_WhenSectionTypeIsIncorrect() throws SectionTypeException {
        batchStockList.get(0).setSectionType("Refrigerated");

        BDDMockito.when(sectionRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(sectionTest));

        final var actualException = assertThrows(
                SectionTypeException.class,
                () -> service.validSection(1L, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(Msg.INSERT_BATCH_SECTION_INCORRET, actualException.getMessage())
        );
    }

    /**
     * Valid section batch stock update throws exception when section type is incorrect.
     *
     * @throws SectionTypeException the section type exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void ValidSectionBatchStockUpdate_ThrowsException_WhenSectionTypeIsIncorrect() throws SectionTypeException {
        batchStockList.get(0).setSectionType("Refrigerated");
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);


        final var actualException = assertThrows(
                SectionTypeException.class,
                () -> service.validSectionBatchStockUpdate(sectionTest, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(Msg.INSERT_BATCH_SECTION_INCORRET, actualException.getMessage())
        );
    }

    /**
     * Valid section batch stock update throws exception when volume limit exceeded.
     *
     * @throws LimitCapacitySectionException the limit capacity section exception
     */
    @Test
    @DisplayName("Testa mensagem da exceção quando seção é divergente")
    void ValidSectionBatchStockUpdate_ThrowsException_WhenVolumeLimitExceeded() throws LimitCapacitySectionException {

        batchStockList.get(0).setVolume(3000f);
        batchStockList.get(1).setVolume(5000f);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);


        BDDMockito.when(batchStockRepo.findById(1L))
                .thenReturn(Optional.ofNullable(batchStockList2.get(0)));

        final var actualException = assertThrows(
                LimitCapacitySectionException.class,
                () -> service.validSectionBatchStockUpdate(sectionTest, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(Msg.LIMIT_CAPACITY_SECTION, actualException.getMessage())
        );
    }

    /**
     * Valid section update throws exception when section not found.
     *
     * @throws NotFoundException the not found exception
     */
    @Test
    @DisplayName("Testa se a section existe")
    void ValidSectionUpdate_ThrowsException_WhenSectionNotFound() throws NotFoundException {

        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);



        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validSectionUpdate(1L, batchStockList));
        assertAll(
                () -> Assertions.assertEquals(Msg.SECTION_NOT_FOUND, actualException.getMessage())
        );
    }
}

