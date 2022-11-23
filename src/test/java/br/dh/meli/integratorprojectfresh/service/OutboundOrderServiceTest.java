package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.*;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OutboundOrderServiceTest {
  @InjectMocks
  private OutboundOrderService service;
  @Mock
  private WarehouseRepository warehouseRepository;
  @Mock
  private SectionRepository sectionRepository;
  @Mock
  private OutboundOrderRepository outboundOrderRepository;
  @Mock
  private OutboundOrderBatchesRepository outboundOrderBatchesRepo;
  @Mock
  private BatchStockRepository batchStockRepository;
  OutboundOrderResponseDTO outboundOrderResponseDTO;
  OutboundOrderRequestDTO outboundOrderRequestDTO;
  OutboundOrderDTO outboundOrderDTO;
  OutboundOrder outboundOrder;
  List<BatchStockDTO> batchStockList;
  List<BatchStock> batchStockList2;
  List<OutboundOrderBatches> outboundOrderBatchesList;
  Warehouse warehouseTest;
  Section sectionTest;

  @BeforeEach
  void setup() {
    LocalDate manufacturingDate = LocalDate.parse("2022-03-09");
    LocalDate dueDate1 = LocalDate.parse("2022-02-01");
    LocalDate dueDate2 = LocalDate.parse("2023-02-01");
    LocalDate orderDate = LocalDate.parse("2022-03-03");
    LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    BatchStockDTO batchStock1 = new BatchStockDTO(1L, "Fresh", 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate1, BigDecimal.valueOf(30.5));
    BatchStockDTO batchStock2 = new BatchStockDTO(1L, "Fresh", 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate1, BigDecimal.valueOf(20.5));
    BatchStockDTO batchStock3 = new BatchStockDTO(1L, "Fresh", 12, manufacturingDate, manufacturingTime, (float) 1.3, dueDate2, BigDecimal.valueOf(20.5));

    batchStockList = new ArrayList<>();
    batchStockList.add(batchStock1);
    batchStockList.add(batchStock2);
    batchStockList.add(batchStock3);

    outboundOrderDTO = new OutboundOrderDTO(1L, orderDate, 2L, 1L, List.of(1L, 2L));
    outboundOrderRequestDTO = new OutboundOrderRequestDTO(outboundOrderDTO);
    outboundOrder = new OutboundOrder(outboundOrderDTO, 1L);

    batchStockList2 = batchStockList.stream().map(a -> new BatchStock(a, outboundOrder.getOrderNumber())).collect(Collectors.toList());

    outboundOrderBatchesList = batchStockList2.stream().map(OutboundOrderBatches::new).collect(Collectors.toList());
    outboundOrderResponseDTO = new OutboundOrderResponseDTO(outboundOrder, outboundOrderBatchesList);

    batchStockList.get(0).setAnnouncementId(1L);
    batchStockList.get(1).setAnnouncementId(1L);
    sectionTest = new Section(1L, "Fresh", (float) 100.0, (float) 80.0,1L ,new ArrayList<>(), new ArrayList<>(), null);
    List<Section> sectionList = new ArrayList<>();
    sectionList.add(sectionTest);
    User user = new User(5L, "Test", "1234", "test@email.com", "manager");
    warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), sectionList, user);
  }

  @Test
  @DisplayName("Sucesso ao ler um pedido de saída")
  void ReadMethodSuccess() {
    BDDMockito.when(outboundOrderRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(java.util.Optional.ofNullable(outboundOrder));
    BDDMockito.when(outboundOrderBatchesRepo.findAllByOrderNumberId(ArgumentMatchers.anyLong()))
            .thenReturn(outboundOrderBatchesList);

    OutboundOrderResponseDTO outboundOrderResponseDTO = service.read(1L);
    assertAll(
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getOrderNumber(), outboundOrder.getOrderNumber()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getOrderDate(), outboundOrder.getOrderDate()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getWarehouseCode(), outboundOrder.getWarehouseCode()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getSectionCode(), outboundOrder.getSectionCode()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getBatchNumber(), outboundOrderBatchesList.get(0).getBatchNumber()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getManufacturingDate(), outboundOrderBatchesList.get(0).getManufacturingDate()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getManufacturingTime(), outboundOrderBatchesList.get(0).getManufacturingTime()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getDueDate(), outboundOrderBatchesList.get(0).getDueDate()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getPrice(), outboundOrderBatchesList.get(0).getPrice()),
            () -> Assertions.assertEquals(outboundOrderResponseDTO.getBatches().get(0).getOrderNumberId(), outboundOrderBatchesList.get(0).getOrderNumberId())
    );
  }

  @Test
  @DisplayName("Erro ao não encontrar lotes para o pedido de saída")
  void ReadMethod_ThrowsException_WhenBatchesNotFound() {
    BDDMockito.when(outboundOrderRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(java.util.Optional.ofNullable(outboundOrder));
    BDDMockito.given(outboundOrderBatchesRepo.findAllByOrderNumberId(ArgumentMatchers.anyLong()))
            .willThrow(new NotFoundException(Msg.OUTBOUND_ORDER_BATCH_NOT_FOUND));

    assertThrows(NotFoundException.class, () -> {
      service.read(1L);
    });
  }

  @Test
  @DisplayName("Erro ao não encontrar warehouse")
  void SaveMethod_ThrowsException_WhenWarehouseNotFound() throws NotFoundException {
    BDDMockito.given(warehouseRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.WAREHOUSE_NOT_FOUND));

    assertThrows(NotFoundException.class, () -> {
      service.save(outboundOrderRequestDTO);
    });
  }

  @Test
  @DisplayName("Erro ao encontrar seção existente")
  void SaveMethod_ThrowsException_WhenSectionIsEmpty() throws NotFoundException {
    BDDMockito.when(warehouseRepository.findById(1L))
            .thenReturn(java.util.Optional.ofNullable(warehouseTest));
    BDDMockito.given(sectionRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.SECTION_NOT_FOUND));

    assertThrows(NotFoundException.class, () -> {
      service.save(outboundOrderRequestDTO);
    });
  }

  @Test
  @DisplayName("Testa mensagem da exceção quando warehouse não é encontrada")
  void IsValidWarehouseMethod_ThrowsException_WhenIdIsInvalid() throws NotFoundException {
    final var actualException = assertThrows(
            NotFoundException.class,
            () -> service.save(outboundOrderRequestDTO));
    assertAll(
            () -> Assertions.assertEquals(Msg.WAREHOUSE_NOT_FOUND, actualException.getMessage())
    );
  }

  @Test
  @DisplayName("Exceção quando seção não pertence ao armazém")
  void SaveMethod_ThrowsExeption_WhenSectionDoesntBelongToWareHouse() throws ActionNotAllowedException {

    sectionTest.setWarehouseCode(3L);

    BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any()))
            .thenReturn(java.util.Optional.ofNullable(warehouseTest));
    BDDMockito.when(sectionRepository.findById(ArgumentMatchers.any()))
            .thenReturn(java.util.Optional.ofNullable(sectionTest));

    final var actualException = assertThrows(
            NotFoundException.class,
            () -> service.save(outboundOrderRequestDTO));
    assertAll(
            () -> Assertions.assertEquals(Msg.SECTION_NOT_BELONG_WAREHOUSE, actualException.getMessage()));
  }

  @Test
  @DisplayName("Exceção quando lote não está vencido")
  void SaveMethod_ThrowsExeption_WhenBatchNotExpired() throws ActionNotAllowedException {
    BDDMockito.when(warehouseRepository.findById(ArgumentMatchers.any()))
            .thenReturn(java.util.Optional.ofNullable(warehouseTest));
    BDDMockito.when(sectionRepository.findById(ArgumentMatchers.any()))
            .thenReturn(java.util.Optional.ofNullable(sectionTest));
    BDDMockito.when(batchStockRepository.findById(ArgumentMatchers.any()))
            .thenReturn(java.util.Optional.of(new BatchStock(batchStockList.get(2), 1L)));

    final var actualException = assertThrows(
            NotFoundException.class,
            () -> service.save(outboundOrderRequestDTO));
    assertAll(
            () -> Assertions.assertEquals(Msg.BATCH_NOT_EXPIRED, actualException.getMessage()));
  }

  @Test
  @DisplayName("Exceção quando ordem de saída não é encontrada")
  void UpdateMethod_ThrowsException_WhenOutboundOrderNotFound() throws NotFoundException {
    BDDMockito.given(outboundOrderRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.OUTBOUND_ORDER_NOT_FOUND));

    assertThrows(NotFoundException.class, () -> {
      service.update(outboundOrderRequestDTO);
    });
  }
}
