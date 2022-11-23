package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderItemsRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;

import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseOrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchaseOrderRepository orderRepo;
    @Autowired
    private PurchaseOrderItemsRepository itemsRepo;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    PurchaseOrder purchaseOrder;

    PurchaseOrderResponseDTO purchaseOrderResponseDTO;
    PurchaseOrderRequestDTO purchaseOrderRequestDTO;
    PurchaseOrderItemsRequestDTO purchaseOrderItems;

    @BeforeEach
    void setup() {
        itemsRepo.deleteAll();
        orderRepo.deleteAll();

        jdbcTemplate.execute("ALTER TABLE purchase_order AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE purchase_order_items AUTO_INCREMENT = 1");

        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PurchaseOrder purchaseOrder = new PurchaseOrder(date, "Aberto", new BigDecimal("100.00"), 1L);
        PurchaseOrder purchaseOrderFinalizado = new PurchaseOrder(date, "Finalizado", new BigDecimal("100.00"), 1L);
        PurchaseOrder purchaseOrderQuantityError = new PurchaseOrder(date, "Aberto", new BigDecimal("300.00"), 1L);


        purchaseOrderItems = new PurchaseOrderItemsRequestDTO(1L,1L, 10, BigDecimal.valueOf(10.00));
        List<PurchaseOrderItemsRequestDTO> purchaseOrderItemsList = List.of(purchaseOrderItems);

        purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(date, 1L, "Aberto", purchaseOrderItemsList, new BigDecimal("100.00"));

        PurchaseOrderItems purchaseOrderItems1 = new PurchaseOrderItems(1L, 1L, 1, BigDecimal.valueOf(10.00));
        PurchaseOrderItems purchaseOrderItems2 = new PurchaseOrderItems(2L, 2L, 1, BigDecimal.valueOf(10.00));
        PurchaseOrderItems purchaseOrderItems3 = new PurchaseOrderItems(3L, 1L, 30000, BigDecimal.valueOf(10.00));


        orderRepo.save(purchaseOrder);
        orderRepo.save(purchaseOrderFinalizado);
        orderRepo.save(purchaseOrderQuantityError);
        itemsRepo.save(purchaseOrderItems1);
        itemsRepo.save(purchaseOrderItems2);
        itemsRepo.save(purchaseOrderItems3);

    }

    @Test
    @DisplayName("Sucesso GET")
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print());

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Sucesso Save/Post")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Erro ao enviar data invalida - SAVE/POST")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenDateAreInvalid() throws Exception {
        purchaseOrderRequestDTO.setDate(null);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("date")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.PURCHASE_ORDER_DATE_NOT_NULL)));

    }

    @Test
    @DisplayName("Erro ao enviar buyer invalida - SAVE/POST")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenBuyerAreInvalid() throws Exception {
        purchaseOrderRequestDTO.setBuyerId(null);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("buyerId")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.BUYER_ID_NOT_NULL)));
    }

    @Test
    @DisplayName("Erro ao enviar oderStatus invalida - SAVE/POST")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenOrderStatusAreInvalid() throws Exception {
        purchaseOrderRequestDTO.setOrderStatus(null);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("orderStatus")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.PURCHASE_ORDER_STATUS_NOT_NULL)));
    }

    @Test
    @DisplayName("Erro ao enviar totalValue invalida - SAVE/POST")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenTotalValueAreInvalid() throws Exception {
        purchaseOrderRequestDTO.setProducts(null);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("products")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.PURCHASE_ORDER_ITEMS_NOT_EMPTY)));
    }


    @Test
    @DisplayName("BuyerId Not Found - Save/Post")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenBuyerNotFound() throws Exception {

        purchaseOrderRequestDTO.setBuyerId(80L);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BUYER_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("BuyerId Not Found - Save/Post")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenAnnouncementIsNotFound() throws Exception {

        purchaseOrderItems.setAnnouncementId(1000L);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ANNOUNCEMENT_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Exception quando compra quantidade de produto maior que o que tem no estoque - PUT")
    void PutMethod_ReturnPurchaseOrderResponseDTO_WhenAnnouncementIsNotFound() throws Exception {
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "3"))
                .andDo(print());

        response.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.BUSINESS_RULE_EXCEPTION.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_STOCK_INSUFFICIENT)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.UNPROCESSABLE_ENTITY.value())));
    }

    @Test
    @DisplayName("Sucesso Put")
    void PutMethod_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"));

        response.andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Not Found Exception - GET")
    void GetMethod_ReturnannoucementGetResponseDTO_WhenIdIsInvalid() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1000"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.PURCHASE_ORDER_ITEMS_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Exception Put")
    void PutMethod_ReturnPurchaseOrderResponseDTO_WhenIdIsInvalid() throws Exception {

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1000"));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(ExceptionType.PURCHASE_ORDER_NOT_FOUND.name())))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Exception Put")
    void PutMethod_ReturnPurchaseOrderResponseDTO_WhenStatusIsAproved() throws Exception {

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "2"));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.PURCHASE_ORDER_ALREADY_APPROVED)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.BAD_REQUEST.value())));

    }


}
