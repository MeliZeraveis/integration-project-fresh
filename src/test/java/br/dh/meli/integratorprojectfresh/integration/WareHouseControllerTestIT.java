package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import org.hamcrest.CoreMatchers;
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
public class WareHouseControllerTestIT {
    private final String ROUTE = Routes.BASE_ROUTE + Routes.WAREHOUSE_QUERY_TYPE;
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("Testa se retorna uma exception ANNOUNCEMENT_NOT_FOUND quando o product_id não existe")
    void get_ReturnExceptionAnnouncementNotFound_AnnouncementIdNotExist() throws Exception{
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("product_id", "77"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ANNOUNCEMENT_NOT_FOUND)));
    }

    @Test
    void get_ReturnWarehouseProductQuantityGetResponseDTO_Success() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("product_id", "1"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));
    }
}