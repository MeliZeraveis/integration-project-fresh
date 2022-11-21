package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.SalesSallerListDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.model.User;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @InjectMocks
    private SellerService service;

    @Mock
    private UserRepository repoUser;

    private User sellerTest;

    PurchaseOrder purchaseOrder;


    @BeforeEach
    void setup(){
        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        purchaseOrder = new PurchaseOrder(date, "Finalizado", BigDecimal.valueOf(100.00), 1L);
        PurchaseOrderItems purchaseOrderItemsTest2 = new PurchaseOrderItems(1L, 1L, 1L, 10, BigDecimal.valueOf(20.00),purchaseOrder,null);
        Announcement announcementTest = new Announcement(1L, "Alface Test", "description", 3L, BigDecimal.valueOf(1.80), 1L, null, new ArrayList<>(), null, new ArrayList<>());
        sellerTest = new User(1L, "fulano", "123456", "fulano@email.com", Roles.BUYER,null, List.of(announcementTest));
    }

    @Test
    @DisplayName("Erro quando vendedor não existe")
    void ValidateSeller_ReturnNotFoundException_WhenSellerNotExist() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validateSeller(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SELLER_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando user não é um vendedor")
    void ValidateSeller_ReturnNotFoundException_WhenUserNotIsSeller() {
        purchaseOrder.setId(1L);
        BDDMockito.when(repoUser.findById(1L)).thenReturn(Optional.ofNullable(sellerTest));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validateSeller(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SELLER_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando vendedor não tem anuncio")
    void ValidateSeller_ReturnNotFoundException_WhenSellerNotHasAD() {
        purchaseOrder.setId(1L);
        sellerTest.setRole("seller");
        sellerTest.setAnnouncement(new ArrayList<>());
        BDDMockito.when(repoUser.findById(1L)).thenReturn(Optional.ofNullable(sellerTest));

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.validateSeller(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.SELLER_NOT_HAS_AD, actualException.getMessage())
        );
    }


    @Test
    @DisplayName("Retorna vendedor com sucesso")
    void GetSeller_ReturnSellerDTO_Success() {
        purchaseOrder.setId(1L);
        sellerTest.setRole("seller");
        BDDMockito.when(repoUser.findById(1L)).thenReturn(Optional.ofNullable(sellerTest));
        SellerDTO getSellerResponse = service.getSeller(1L);
        assertThat(getSellerResponse).isNotNull();
    }

    @Test
    @DisplayName("Retorna todas as vendas do vendedor com sucesso")
    void GetAllUserSales_ReturnSalesSallerListDTO_Success() {

        purchaseOrder.setId(1L);
        sellerTest.setRole("seller");
        BDDMockito.when(repoUser.findById(1L)).thenReturn(Optional.ofNullable(sellerTest));
        SalesSallerListDTO getSalesResponse = service.getAllSallesSales(1L);

        assertThat(getSalesResponse).isNotNull();
    }

    @Test
    @DisplayName("Retorna todas as vendas do vendedor em periodo de tempo com sucesso")
    void GetSalesByDate_ReturnSalesSallerListDTO_Success() {
        LocalDate date1 = LocalDate.parse("2021-10-10");
        LocalDate date2 = LocalDate.parse("2021-11-11");

        purchaseOrder.setId(1L);
        sellerTest.setRole("seller");
        BDDMockito.when(repoUser.findById(1L)).thenReturn(Optional.ofNullable(sellerTest));
        SalesSallerListDTO getSalesResponse = service.getSalesByDate(1L, date1, date2);

        assertThat(getSalesResponse).isNotNull();
    }
}