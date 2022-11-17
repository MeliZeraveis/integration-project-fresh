package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private  AnnouncementRepository announcementRepo;

    @Mock
    private  PurchaseOrderRepository orderRepo;

    PurchaseOrderItemsResponseDTO purchaseOrderItemsResponse1;

    PurchaseOrderItems purchaseOrderItems1;

    List<PurchaseOrderItemsResponseDTO> itemsList;

    PurchaseOrder purchaseOrder;

    List<PurchaseOrderItems> purchaseOrderItemsList;

    @BeforeEach
    void setup() {
        purchaseOrderItems1 = new PurchaseOrderItems(1l, 2l, 10, BigDecimal.valueOf(20.00));
        purchaseOrderItemsResponse1 = new PurchaseOrderItemsResponseDTO(purchaseOrderItems1);
        PurchaseOrderItems purchaseOrderItems2 = new PurchaseOrderItems(1l, 1l, 20, BigDecimal.valueOf(100.00));

        PurchaseOrderItemsResponseDTO purchaseOrderItemsResponse2 = new PurchaseOrderItemsResponseDTO(purchaseOrderItems2);

       itemsList = new ArrayList<>();
       itemsList.add(purchaseOrderItemsResponse1);
       itemsList.add(purchaseOrderItemsResponse2);

        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        purchaseOrder = new PurchaseOrder(date, OrderStatus.PENDING, BigDecimal.valueOf(100.00), 1L);

        purchaseOrderItemsList = new ArrayList<>();
        purchaseOrderItemsList.add(purchaseOrderItems1);
        purchaseOrderItemsList.add(purchaseOrderItems2);
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
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.read(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND, actualException.getMessage())
        );

    }


}
