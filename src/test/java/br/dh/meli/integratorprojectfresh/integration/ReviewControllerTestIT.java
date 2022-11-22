package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.request.ReviewDTO;
import br.dh.meli.integratorprojectfresh.dto.request.ReviewRequestDTO;
import br.dh.meli.integratorprojectfresh.model.Review;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import br.dh.meli.integratorprojectfresh.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Review controller test it.
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc

public class ReviewControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReviewDTO reviewDTO;

    private ReviewRequestDTO reviewRequestDTO;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {

        jdbcTemplate.execute("ALTER TABLE review AUTO_INCREMENT = 1");

        reviewDTO = new ReviewDTO(4, "Muito bom", 2L, 1L);
        reviewRequestDTO = new ReviewRequestDTO(reviewDTO);
        Review review = new Review(reviewDTO);
        reviewRepository.save(review);
    }

    /**
     * Save.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 200 quando salvar uma review")
    void save() throws Exception{
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isCreated());
    }

    /**
     * Save invalid grade.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 400 quando salvar uma review com nota inválida")
    void saveInvalidGrade() throws Exception{
        reviewDTO.setGrade(6);
        reviewRequestDTO.setReview(reviewDTO);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isBadRequest());
    }


    /**
     * Update.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName(("Deve retornar 200 quando atualizar uma review"))
    void update() throws Exception{
        reviewDTO.setGrade(5);
        reviewRequestDTO.setReview(reviewDTO);
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/review/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isCreated())
                .andDo(print());
    }

    /**
     * Update invalid grade.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName(("Deve retornar 400 quando atualizar uma review com nota inválida"))
    void updateInvalidGrade() throws Exception{
        reviewDTO.setGrade(6);
        reviewRequestDTO.setReview(reviewDTO);
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/review/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * Update invalid id.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName(("Deve retornar 404 quando atualizar uma review inexistente"))
    void updateInvalidId() throws Exception{
        reviewDTO.setGrade(4);
        reviewRequestDTO.setReview(reviewDTO);
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/review/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    /**
     * Find by id.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 200 quando buscar uma review")
    void findById() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print());
        response.andExpect(status().isOk());
    }

    /**
     * Find by id invalid.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 404 quando buscar uma review inexistente")
    void findByIdInvalid() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "0"))
                .andDo(print());
        response.andExpect(status().isNotFound());
    }

    /**
     * Gets all reviews.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 200 quando buscar  uma lista de reviews por id do anúncio")
    void getAllReviews() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("announcementId", "2"))
                .andDo(print());
        response.andExpect(status().isOk());
    }

    /**
     * Gets all reviews invalid.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 404 quando buscar  uma lista de reviews por id do anúncio inexistente")
    void getAllReviewsInvalid() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("announcementId", "0"))
                .andDo(print());
        response.andExpect(status().isNotFound());
    }

    /**
     * Gets all reviews filter.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 200 quando buscar  uma lista de reviews por id do anúncio e filtro de nota")
    void getAllReviewsFilter() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review/list/grade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("announcementId", "2")
                        .param("reviewGrade", "4"))
                .andDo(print());
        response.andExpect(status().isOk());
    }

    /**
     * Gets all reviews filter invalid.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Deve retornar 404 quando buscar  uma lista de reviews por id do anúncio e filtro de nota inexistente")
    void getAllReviewsFilterInvalid() throws Exception{
        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-product/review/list/grade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("announcementId", "0")
                        .param("reviewGrade", "4"))
                .andDo(print());
        response.andExpect(status().isNotFound());
    }




}

