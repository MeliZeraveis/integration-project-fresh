package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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
public class BatchStockControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BatchStockRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    BatchStock batchStock;

    BatchStockGetResponseDTO batchStockGetResponseDTO;

    @BeforeEach
    void setup() {
        //
    }

    @Test
    void get_ReturnBatchStockGetResponseDTO_WhenSuccessful() throws Exception {
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/due-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numberOfDays", "1000")
                        .param("sectionCode", "1"))
                .andDo(print());

        response.andExpect(status().isOk());

    }

    @Test
    void get_ReturnExceptionNotFound_WhenBatchStockByDueDateNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/due-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numberOfDays", "1")
                        .param("sectionCode", "1"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void get_ReturnBatchStockGetResponseDTOByAscOrder_WhenSuccessful() throws Exception {
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/due-date/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numberOfDays", "1000")
                        .param("category", "FS")
                        .param("order", "asc"))
                .andDo(print());

        response.andExpect(status().isOk());

    }

    @Test
    void get_ReturnExceptionNotFound_WhenBatchStockByAscOrderNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/due-date/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numberOfDays", "1")
                        .param("category", "FS")
                        .param("order", "asc"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    static class ReviewControllerTestIT {

        @BeforeEach
        void setUp() {
        }

        @Test
        void save() {
        }

        @Test
        void update() {
        }

        @Test
        void getReviewByReviewId() {
        }

        @Test
        void getAllReviews() {
        }
    }
}
