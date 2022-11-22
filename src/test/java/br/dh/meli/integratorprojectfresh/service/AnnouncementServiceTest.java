package br.dh.meli.integratorprojectfresh.service;
import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementUpdateRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.exception.UnauthorizedException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {
    @InjectMocks
    private AnnouncementService service;

    @Mock
    private AnnouncementRepository repository;
    @Mock
    private UserRepository userRepo;

    AnnouncementGetResponseDTO responseDTO;
    AnnouncementListResponseDTO listResponseDTO;
    AnnouncementUpdateRequestDTO updateRequestDTO;
    InboundOrder inboundOrder;
    Announcement announcement;
    Announcement announcementSeller;
    Announcement announcementList1;
    BatchStock batchStock;
    Section section;
    Warehouse warehouse;
    User user;

    @BeforeEach
    void setUp() {
        LocalDate manufacturingDate = LocalDate.parse("2021-12-12");
        LocalDate dueDate1 = LocalDate.parse("2021-10-10");
        LocalDate dueDate2 = LocalDate.parse("2021-11-11");
        LocalDate dueDate3 = LocalDate.parse("2021-11-11");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null, null );
        inboundOrder = new InboundOrder(1L, manufacturingDate, 1L, 1L,warehouse, null, section );

        batchStock = new BatchStock(1L, 1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50), inboundOrder);
        List<BatchStock> batchStockList2 = new ArrayList<>();
        batchStockList2.add(batchStock);

        section = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), List.of(inboundOrder), null );
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, null, new ArrayList<>());

        user = new User(3L, "usuarioTeste", "123456", "teste@email.com", "seller");
        announcementSeller = new Announcement(1L, "Camisa", "Camisa branca", 4L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, user, new ArrayList<>());


        //        announcementList1 = new Announcement(2L, "banana", "Banana amarela", 4L, BigDecimal.valueOf(10.0), 1L, section, batchStockList2, null, new ArrayList<>());
//        Announcement announcementList2 = new Announcement(3L, "Banana Prata", "Banana amarela", 3L, BigDecimal.valueOf(8.0), 1L, section, batchStockList2, null, new ArrayList<>());
//        Announcement announcementList3 = new Announcement(4L, "Banana Nanica", "Banana amarela", 4L, BigDecimal.valueOf(6.8), 1L, section, batchStockList2, null, new ArrayList<>());
////        List<Announcement> announcementList = new ArrayList<>();
////        announcementList.add(announcementList1);
////        announcementList.add(announcementList2);
////        announcementList.add(announcementList3);
//        listResponseDTO = new AnnouncementListResponseDTO(announcementList1);

        responseDTO = new AnnouncementGetResponseDTO(announcement);
        updateRequestDTO = new AnnouncementUpdateRequestDTO(1L, "sorvete", "sorvete de passas", BigDecimal.valueOf(10.0), 1L, 3L);


    }

    @Test
    @DisplayName("Sucesso ao retornar uma lista de anúncios")
    void GetAnnouncementByAnnouncementId_ReturnNewAnnouncement_WhenIdIsValid() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        responseDTO = service.getAnnouncementByAnnouncementId(1L);
        assertThat(responseDTO).isNotNull();

    }

    @Test
    @DisplayName("Sucesso ao retornar BatchStockNumber")
    void FindAnnouncementByBatchStockNumber_ReturnBatchStockNumberFilter_WhenIdAndLetterAreValid() {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        responseDTO = service.findAnnouncementByBatchStockNumber(1L,'Q');
        assertThat(responseDTO).isNotNull();
    }

    @Test
    @DisplayName("Erro quando Announcement não existe")
    void GetAnnouncementByAnnouncementId_ThrowsException_WhenAnnoucementNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.getAnnouncementByAnnouncementId(1L));
        assertAll(
                () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando Announcement não existe")
    void FindAnnouncementByBatchStockNumber_ThrowsException_WhenAnnoucementNotFound() {

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByBatchStockNumber(1L,'Q'));
        assertAll(
                () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_IS_EMPTY, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando filtro não existe")
    void FindAnnouncementByBatchStockNumber_ThrowsException_WhenLetraNotFound() throws NotFoundException {
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcement));
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByBatchStockNumber(1L,'A'));
        assertAll(
                () -> Assertions.assertEquals(Msg.LETTER_NOT_VALID, actualException.getMessage())
        );
    }

//    @Test
//    @DisplayName("Sucesso ao retornar uma lista de anúncios buscando por palavra chave - SEARCH")
//    void FindAnnouncementByQueryString_ReturnListOfAnnouncement_WhenQueryStringIsValid() {
//        BDDMockito.when(repository.findByNameContainingIgnoreCase("banana")).thenReturn(announcementList1);
//        listResponseDTO = service.findAnnouncementByQueryString("banana");
//        assertThat(responseDTO).isNotNull();
//
//    }

        @Test
    @DisplayName("Erro quando palavra chave não é encontrada no banco de dados")
    void FindAnnouncementByQueryString_ThrowError_WhenQueryStringIsNotFind() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByQueryString("banana"));
        assertAll(
                () -> Assertions.assertEquals(Msg.QUERY_STRING_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando preço não é encontrada no banco de dados")
    void FindAnnouncementByPrice_ThrowError_WhenPriceIsNotFind() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.findAnnouncementByPrice(BigDecimal.valueOf(3.0), BigDecimal.valueOf(13.0)));
        assertAll(
                () -> Assertions.assertEquals(Msg.PRICE_MIN_MAX, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando produto não existe")
    void UpdateById_ThrowError_WhenProductNotExist() throws NotFoundException {
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> service.updateById(updateRequestDTO));
        assertAll(
                () -> Assertions.assertEquals(Msg.ANNOUNCEMENT_NOT_FOUND, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando preço não é encontrada no banco de dados")
    void UpdateById_ThrowError_WhenUserIsNotASeller() throws UnauthorizedException {
        updateRequestDTO.setSellerId(1L);
        user.setRole("buyer");
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementSeller));
        final var actualException = assertThrows(
                UnauthorizedException.class,
                () -> service.updateById(updateRequestDTO));
        assertAll(
                () -> Assertions.assertEquals(Msg.USER_NOT_AUTHORIZED, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando user não existe")
    void UpdateById_ThrowError_WhenUserIsEmpty() throws UnauthorizedException {
        updateRequestDTO.setSellerId(1L);
        user.setUserId(30L);
//        user.setRole("buyer");
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementSeller));
        final var actualException = assertThrows(
                UnauthorizedException.class,
                () -> service.updateById(updateRequestDTO));
        assertAll(
                () -> Assertions.assertEquals(Msg.USER_NOT_AUTHORIZED, actualException.getMessage())
        );
    }

    @Test
    @DisplayName("Erro quando user não existe")
    void UpdateById_ThrowError_WhenSellerIsNotFound() throws UnauthorizedException {
        updateRequestDTO.setSellerId(1L);
        user.setUserId(1L);
        user.setRole("buyer");
//        user.setRole("buyer");
        BDDMockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(announcementSeller));
//        BDDMockito.when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        final var actualException = assertThrows(
                UnauthorizedException.class,
                () -> service.updateById(updateRequestDTO));
        assertAll(
                () -> Assertions.assertEquals(Msg.USER_NOT_AUTHORIZED, actualException.getMessage())
        );
    }



}