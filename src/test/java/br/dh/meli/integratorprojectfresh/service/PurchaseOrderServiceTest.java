package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import br.dh.meli.integratorprojectfresh.enums.Roles;
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

    PurchaseOrderItemsResponseDTO purchaseOrderItemsResponse1;

    PurchaseOrderItems purchaseOrderItems1;

    List<PurchaseOrderItemsResponseDTO> itemsList;

    PurchaseOrder purchaseOrder;

    List<PurchaseOrderItems> purchaseOrderItemsList;

    User userTest;

    PurchaseOrderRequestDTO purchaseOrderRequestDTO;

    Warehouse warehouseTest;
    Section sectionTest;
    Announcement announcementTest;


    @BeforeEach
    void setup() {
        userTest = new User(1l, "fulano", "123456", "fulano@email.com", Roles.BUYER);
        purchaseOrderItems1 = new PurchaseOrderItems(1l, 1l, 10, BigDecimal.valueOf(20.00));
        purchaseOrderItemsResponse1 = new PurchaseOrderItemsResponseDTO(purchaseOrderItems1);
        PurchaseOrderItems purchaseOrderItems2 = new PurchaseOrderItems(1l, 1l, 10, BigDecimal.valueOf(100.00));

        PurchaseOrderItemsResponseDTO purchaseOrderItemsResponse2 = new PurchaseOrderItemsResponseDTO(purchaseOrderItems2);

        itemsList = new ArrayList<>();
        itemsList.add(purchaseOrderItemsResponse1);
        itemsList.add(purchaseOrderItemsResponse2);


        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        purchaseOrderItemsList = new ArrayList<>();
        purchaseOrderItemsList.add(purchaseOrderItems1);
        purchaseOrderItemsList.add(purchaseOrderItems2);

        purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(date, 1l, OrderStatus.APPROVED, purchaseOrderItemsList);

        purchaseOrder = new PurchaseOrder(date, OrderStatus.APPROVED, BigDecimal.valueOf(100.00), 1L);

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(sectionTest);
        User user = new User(5L, "Test", "1234", "test@email.com", "manager");
        warehouseTest = new Warehouse(1L, "Test", "Address Test", "BR-Test", new ArrayList<>(), sectionList, user);
        announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), 1L, sectionTest, new ArrayList<>(), null, new ArrayList<>());


    }

    @Test
    @DisplayName("Sucesso ao retornar uma lista de items")
    void GetPurchaseItemsListById_ReturnItemsList_WhenPurchaseOrderIdIsValid() {
        BDDMockito.when(itemsRepo.findByPurchaseOrderId(1L)).thenReturn(purchaseOrderItemsList);
        itemsList = service.read(1L);
        assertThat(itemsList).isNotNull();

    }

    @Test
    @DisplayName("Erro ao procurar uma lista de items")
    void GetPurchaseItemsListById_ThrowsException_WhenPurchaseOrderIdNotExists() throws NotFoundException {
        Long purchaseOrderInexistent = 99l;

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
        BDDMockito.when(announcementRepo.findById(announcementTest.getAnnouncementId()))
                .thenReturn(java.util.Optional.ofNullable(announcementTest));
        BDDMockito.when(orderRepo.save(purchaseOrder))
                .thenReturn(purchaseOrder);
        BDDMockito.when(itemsRepo.saveAll(purchaseOrderItemsList))
                .thenReturn(purchaseOrderItemsList);


        PurchaseOrderResponseDTO purchaseOrderResponseDTO = service.save(purchaseOrderRequestDTO);

        assertThat(purchaseOrderResponseDTO).isNotNull();
    }


}
