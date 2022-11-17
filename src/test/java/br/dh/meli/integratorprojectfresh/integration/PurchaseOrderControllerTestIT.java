package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
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
public class PurchaseOrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchaseOrderRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    PurchaseOrder purchaseOrder;

    PurchaseOrderResponseDTO purchaseOrderResponseDTO;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("Testa se o metodo retorna o anuncio correto em caso de sucesso")
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));

    }
}
