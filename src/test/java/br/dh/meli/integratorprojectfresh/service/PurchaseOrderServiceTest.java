package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderItemsRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.exception.InvalidParamException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {
    @InjectMocks
    private PurchaseOrderService service;

    @Mock
    private PurchaseOrderItemsRepository itemsRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnnouncementRepository announcementRepo;

    @Mock
    private PurchaseOrderRepository orderRepo;

    Warehouse warehouseTest;
    Section sectionTest;
    Announcement announcementTest;

    User userTest;

    PurchaseOrder purchaseOrder;
    PurchaseOrderItems purchaseOrderItemsTest;
    List<PurchaseOrderItems> purchaseOrderItemsList;

    PurchaseOrderRequestDTO purchaseOrderRequestTest;
    PurchaseOrderResponseDTO purchaseOrderResponseTest;

    PurchaseOrderItemsResponseDTO purchaseOrderItemsResponseTest;
    List<PurchaseOrderItemsResponseDTO> purchaseOrderItemsResponseTestList;

    PurchaseOrderItemsRequestDTO purchaseOrderItemsRequestTest;
    List<PurchaseOrderItemsRequestDTO> purchaseOrderItemsRequestTestList;


    @BeforeEach
    void setup() {
        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userTest = new User(1L, "fulano", "123456", "fulano@email.com", Roles.BUYER);

        sectionTest = new Section(1L, "Fresh", (float) 100.0, (float) 80.0, 1L, new ArrayList<>(), new ArrayList<>(), null);
        List<Section> sectionList = new ArrayList<>();
        sectionList.add(sectionTest);
        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), sectionList, userTest);
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), 1L, sectionTest, new ArrayList<>(), null, new ArrayList<>());

        purchaseOrderItemsTest = new PurchaseOrderItems(1L, 1L, 10, BigDecimal.valueOf(20.00));
        purchaseOrderItemsResponseTest = new PurchaseOrderItemsResponseDTO(purchaseOrderItemsTest);

        purchaseOrderItemsResponseTestList = new ArrayList<>();
        purchaseOrderItemsResponseTestList.add(purchaseOrderItemsResponseTest);

        purchaseOrderItemsList = new ArrayList<>();
        purchaseOrderItemsList.add(purchaseOrderItemsTest);

        purchaseOrder = new PurchaseOrder(date, OrderStatus.APPROVED, BigDecimal.valueOf(100.00), 1L);

        purchaseOrderRequestTest = new PurchaseOrderRequestDTO(date, 1L, OrderStatus.APPROVED, purchaseOrderItemsList);

        purchaseOrderResponseTest = new PurchaseOrderResponseDTO(purchaseOrder, purchaseOrderItemsList);

    }

    @Test
    @DisplayName("Sucesso ao retornar uma lista de items")
    void GetPurchaseItemsListById_ReturnItemsList_WhenPurchaseOrderIdIsValid() {
        BDDMockito.when(itemsRepo.findByPurchaseOrderId(1L)).thenReturn(purchaseOrderItemsList);

        purchaseOrderItemsResponseTestList = service.read(1L);

        assertThat(purchaseOrderItemsResponseTestList).isNotNull();

    }

    @Test
    @DisplayName("Erro ao procurar uma lista de items")
    void GetPurchaseItemsListById_ThrowsException_WhenPurchaseOrderIdNotExists() throws NotFoundException {
        Long purchaseOrderInexistent = 99L;

        BDDMockito.given(itemsRepo.findByPurchaseOrderId(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            itemsRepo.findByPurchaseOrderId(purchaseOrderInexistent);
        });

    }
    @Test
    @DisplayName("Sucesso ao criar um novo pedido de compra")
    void SaveMethod_ReturnNewPurchaseOrderResponseDTO_WhenParamsAreValid() {
        BDDMockito.when(userRepository.findById(userTest.getUserId()))
                .thenReturn(java.util.Optional.ofNullable(userTest));
        BDDMockito.when(announcementRepo.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.when(orderRepo.save(ArgumentMatchers.any()))
                .thenReturn(purchaseOrder);
        BDDMockito.when(itemsRepo.saveAll(ArgumentMatchers.any()))
                .thenReturn(purchaseOrderItemsList);


        PurchaseOrderResponseDTO purchaseOrderResponseDTO = service.save(purchaseOrderRequestTest);

        assertThat(purchaseOrderResponseDTO).isNotNull();
    }

    @Test
    @DisplayName("Erro ao encontrar buyer")
    void SaveMethod_ThrowsException_WhenBuyerNotFound() throws NotFoundException {
        BDDMockito.given(userRepository.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.BUYER_ID_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.save(purchaseOrderRequestTest);
        });
    }

    @Test
    @DisplayName("Erro ao encontrar buyer")
    void UpdateMethod_ThrowsException_WhenOrderNotFound() throws NotFoundException {
        BDDMockito.given(orderRepo.findById(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.update(purchaseOrder.getId());
        });
    }

    @Test
    @DisplayName("Erro ao encontrar buyer")
    void UpdateMethod_ThrowsException_WhenOrderStatusIsPending() throws InvalidParamException {


        BDDMockito.given(orderRepo.findById(ArgumentMatchers.any())).willThrow(new InvalidParamException(Msg.PURCHASE_ORDER_ALREADY_APPROVED));

        assertThrows(InvalidParamException.class, () -> {
            service.update(purchaseOrder.getId());
        });
    }



}
