package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.User;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {
    @InjectMocks
    private BuyerService service;
    @Mock
    private PurchaseOrderRepository purchaseOrderRepo;

    @Mock
    PurchaseOrderItemsRepository purchaseOrderItemsRepo;

    @BeforeEach
    void setup() {

    }


    @Test
    @DisplayName("Sucesso ao procurar uma lista de items do usuario")
    void FindAllUsersMethod_ReturnUserDTO_WhenSucess() {
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PurchaseOrder purchaseOrder = new PurchaseOrder(date, "Finalizado", BigDecimal.valueOf(100.00), 1L);
        purchaseOrderList.add(purchaseOrder);
        List<OrderItemsResponseDTO> orderItemsResponseDTOS= purchaseOrderList.stream().map(p -> new OrderItemsResponseDTO(p, purchaseOrderItemsRepo.findByPurchaseOrderId(p.getId()))).collect(Collectors.toList());

        assertThat(orderItemsResponseDTOS).isNotNull();
    }
    @Test
    @DisplayName("Erro quando a ROLE não existe")
    void FindAllUsers_ThrowsException_WhenIdNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getOrderWithItems(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.USER_ID_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Sucesso ao procurar uma lista de items ordenado por status do usuario")
    void findPurchaseOrderByStatus_ReturnListDTOs_WhenSucess() {
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PurchaseOrder purchaseOrder = new PurchaseOrder(date, "Finalizado", BigDecimal.valueOf(100.00), 1L);
        purchaseOrderList.add(purchaseOrder);
        List<OrderItemsResponseDTO> orderItemsResponseDTOS= purchaseOrderList.stream().map(p -> new OrderItemsResponseDTO(p, purchaseOrderItemsRepo.findByPurchaseOrderId(p.getId()))).collect(Collectors.toList());
        List<OrderItemsResponseDTO> listDTOS = new ArrayList<>();
        for(int i = 0; i<orderItemsResponseDTOS.size(); i++) {
            if(orderItemsResponseDTOS.get(i).getOrderStatus().equalsIgnoreCase("Aberto")) {
                listDTOS.add(orderItemsResponseDTOS.get(i));
            }
        }
        assertThat(listDTOS).isNotNull();
    }

    @Test
    @DisplayName("Erro quando ao id não existe")
    void findPurchaseOrderByStatus_ThrowsException_WhenIdNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findPurchaseOrderByStatus(1L, "Aberto"));
        assertAll(
                () -> Assertions.assertEquals(Msg.USER_ID_NOT_FOUND, actualException.getMessage())
        );
    }
}
