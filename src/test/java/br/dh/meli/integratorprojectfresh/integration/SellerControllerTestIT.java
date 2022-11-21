package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
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
public class SellerControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;


    @Test
    @DisplayName("Sucesso getSeller/GET")
    void getSeller_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/seller/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Sucesso getSeller/GET")
    void getSeller_ReturnNotFoundException_WhenSellerNotExist() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/seller/{id}", 800)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.SELLER_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }


    @Test
    @DisplayName("Sucesso getSeller/GET")
    void getAllSales_ReturnNotSalesSallerListDTO_Success() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/seller/{id}/details", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        response.andExpect(status().isOk());
    }


    @Test
    @DisplayName("Sucesso getSeller/GET")
    void getSalesByDate_ReturnNotSalesSallerListDTO_Success() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/seller/{id}/details", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date1", "2000-03-01" )
                        .param("date2", "2022-11-28" )
                )
                .andDo(print());

        response.andExpect(status().isOk());
    }
}
