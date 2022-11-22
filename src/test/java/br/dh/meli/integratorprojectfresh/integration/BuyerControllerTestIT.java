package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BuyerControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepo;

    @Autowired
    PurchaseOrderItemsRepository purchaseOrderItemsRepo;

    @Test
    @DisplayName("Testa se o metodo retorna o OrderItemsResponseDTO em caso de sucesso")
    void getOrderWithItems_ReturnOrderItemsResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/buyer/orderItems")
                        .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andDo(print());

        response.andExpect(status().isOk());
    }


    @Test
    @DisplayName("Testa se o metodo retorna erro se o id não existe")
    void getOrderByStatus_ReturnOrderItemsResponseDTO_WhenIdNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/buyer/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "14214152")
                        .param("status", "Finalizado"))
                .andDo(print());

        response.andExpect(status().isNotFound())
               .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_ID_NOT_FOUND)));
    }

    @Test
    @DisplayName("Testa se o metodo retorna erro se o id não existe")
    void getOrderWithItems_ReturnOrderItemsResponseDTO_WhenIdNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/buyer/orderItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "14214152"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_ID_NOT_FOUND)));
    }
    @Test
    @DisplayName("Testa se o metodo retorna o OrderItemsResponseDTO em caso de sucesso")
    void getOrderByStatus_ReturnOrderItemsResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/buyer/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("status", "Finalizado"))
                .andDo(print());

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa se o metodo retorna erro se o status não existe")
    void getOrderWithItems_ReturnOrderItemsResponseDTO_WhenStatusNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/buyer/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("status", "fibal"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

}
