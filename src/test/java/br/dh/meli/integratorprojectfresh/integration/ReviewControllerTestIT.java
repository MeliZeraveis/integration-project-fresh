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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeEach
    void setUp() {

        jdbcTemplate.execute("ALTER TABLE review AUTO_INCREMENT = 1");

        reviewDTO = new ReviewDTO(4, "Muito bom", 2L, 1L);
        reviewRequestDTO = new ReviewRequestDTO(reviewDTO);
        Review review = new Review(reviewDTO);
        reviewRepository.save(review);
    }

    @Test
    @DisplayName("Deve retornar 200 quando salvar uma review")
    void save() throws Exception{
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDTO)));
        response.andExpect(status().isCreated());

    }

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
    void update() {
    }

    @Test
    void getReviewByReviewId() {
    }

    @Test
    void getAllReviews() {
    }

}

//@DisplayName("Deve retornar 400 quando salvar uma review com nota inválida")