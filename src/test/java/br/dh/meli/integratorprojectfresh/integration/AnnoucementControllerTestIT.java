package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.dh.meli.integratorprojectfresh.model.Announcement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AnnoucementControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    Announcement announcement;

    AnnoucementGetResponseDTO annoucementGetResponseDTO;


    @BeforeEach
    void setup() {
//        announcement = new Announcement(1L, "maça", "description")
//        annoucementGetResponseDTO = new AnnoucementGetResponseDTO(announcement);

    }

    @Test
    @DisplayName("Testa se o metodo retorna o anuncio correto em caso de sucesso")
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {

            ResultActions response = mockMvc
                    .perform(get("/api/v1/fresh-products/list")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("id", "1"))
                    .andDo(print());

            response.andExpect(status().isOk())
                   .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));

    }

    @Test
    void get_ReturnExceptionNotFound_WhenProductNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "77"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ANNOUNCEMENT_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    void get_ReturnannoucementGetResponseDTOByLetra_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("letra", "v"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));

    }

    @Test
    void get_ReturnExceptionNotFound_WhenProductByLetraNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("letra", "a"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                    .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.LETTER_NOT_VALID)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

}
