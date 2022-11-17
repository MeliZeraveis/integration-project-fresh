package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void setup() {
        itemsRepo.deleteAll();
        orderRepo.deleteAll();

        jdbcTemplate.execute("ALTER TABLE purchase_order AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE purchase_order_items AUTO_INCREMENT = 1");

        LocalDateTime date = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PurchaseOrder purchaseOrder = new PurchaseOrder(date, OrderStatus.PENDING, new BigDecimal("100.00"), 1L);
        PurchaseOrderItems purchaseOrderItems = new PurchaseOrderItems(1L,1L, 10, new BigDecimal("10.00"));
        List<PurchaseOrderItems> purchaseOrderItemsList = List.of(purchaseOrderItems);

        purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(date, 1L, OrderStatus.PENDING, purchaseOrderItemsList);
        orderRepo.save(purchaseOrder);
        itemsRepo.saveAll(purchaseOrderItemsList);
    }

    @Test
    @DisplayName("")
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));

    }

    @Test
    @DisplayName("depois vejo")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrderRequestDTO)));

        response.andExpect(status().isCreated());

//        assertThat(orderRepo.findAll().size()).isEqualTo(2);
//        assertThat(orderRepo.findAll().get(0)).isNotNull();
//        assertThat(orderRepo.findAll().size()).isEqualTo(4);
//        assertThat(orderRepo.findAll().get(0)).isNotNull();

    }

}
