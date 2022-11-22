package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementPostRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementUpdateRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DOUBLE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    Section section;
    Warehouse warehouse;
    InboundOrder inboundOrder;
    BatchStock batchStock;

    User user;
    AnnouncementGetResponseDTO annoucementGetResponseDTO;
    AnnouncementPostRequestDTO announcementRequestDTO;
    AnnouncementUpdateRequestDTO announcementUpdateRequestDTO;


    @BeforeEach
    void setup() {
//        announcement = new Announcement(1L, "maça", "description")
//        annoucementGetResponseDTO = new AnnoucementGetResponseDTO(announcement);
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDate dueDate3 = LocalDate.parse("2021-11-11");
        LocalDate manufacturingDate = LocalDate.parse("2021-12-12");
        warehouse = new Warehouse(1L, "teste", "1234", "12", new ArrayList<>(), null, null );
        inboundOrder = new InboundOrder(1L, manufacturingDate, 1L, 1L,warehouse, null, section );
        section = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), List.of(inboundOrder), null );
        batchStock = new BatchStock(1L, 1L, "Fresh", 10, manufacturingDate, manufacturingTime, 10.0f, dueDate3, BigDecimal.valueOf(30.50), inboundOrder);
        List<BatchStock> batchStockList2 = new ArrayList<>();
        batchStockList2.add(batchStock);
        user = new User(3L, "usuarioTeste", "123456", "teste@email.com", "seller");
        announcement = new Announcement(1L, "Camisa", "Camisa branca", 3L, BigDecimal.valueOf(100.0), 1L, section, batchStockList2, user, new ArrayList<>());
        announcementRequestDTO = new AnnouncementPostRequestDTO(announcement);
        announcementUpdateRequestDTO = new AnnouncementUpdateRequestDTO(announcement);


    }

    @Test
    @DisplayName("Testa se o metodo retorna o anuncio por categoria em caso de sucesso")
    void get_ReturnAllAnnoucementGetResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        response.andExpect(status().isOk());
        //  .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Testa se o metodo retorna o anuncio por categoria em caso de sucesso")
    void get_ReturnannoucementCategoryGetResponseDTO_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("category", "FS"))
                .andDo(print());

        response.andExpect(status().isOk());
              //  .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Testa se o metodo retorna erro se a categoria não existe")
    void get_ReturnannoucementCategoryGetResponseDTO_WhenCategoryNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("category", "FA"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.CATEGORY_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));


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
                   .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));

    }

    @Test
    @DisplayName("Testa se o metodo retorna uma mensagem NOT FOUND quando é informado o ID de um anuncio que nao existe")
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
    @DisplayName("Testa se o metodo retorna o anuncio correto quando informado o ID e a categoria de ordenaçao em caso de sucesso")
    void get_ReturnannoucementGetResponseDTOByLetra_Sucess() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("sortBy", "V"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));

    }

    @Test
    @DisplayName("Testa se o metodo retorna uma mensagem NOT FOUND quando é informado o ID correto com uma categoria inexistente")
    void get_ReturnExceptionNotFound_WhenProductByLetraNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("sortBy", "a"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                    .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.LETTER_NOT_VALID)))
                    .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Testa se o metodo retorna uma mensagem de erro quando é informado um NAME inexistente no Banco de Dados")
    void FindAnnouncementByQueryString_ThrowException_WhenProductNameNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/product-name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productName", "Camiseta"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.QUERY_STRING_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Testa se o metodo retorna o anuncio correto quando informado um NAME existente no banco de dados")
    void FindAnnouncementByQueryString_ReturnAnnouncement_WhenProductNameExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/product-name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productName", "banana"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$[0].name", CoreMatchers.containsStringIgnoringCase("banana")));
    }

    @Test
    @DisplayName("Testa se passado valores inexistentes no Banco de Dados, retorna um erro.")
    void FindAnnouncementByPrice_ThrowException_WhenProductPriceNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("min", "0.1")
                        .param("max", "0.2"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.PRICE_MIN_MAX)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Testa passado valores minimos e maximos existentes no bd, retorna uma lista ordenada")
    void FindAnnouncementByPrice_ReturnAnnouncement_WhenPriceExists() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/list/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("min", "3.0")
                        .param( "max", "13.0"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$[0].price", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Testa passado valores minimos e maximos existentes no bd, retorna uma lista ordenada")
    void SaveProduct_ReturnNewAnnouncement_WhenParamsAreValid() throws Exception {

        ResultActions response = mockMvc
                .perform(post("/api/v1/fresh-products/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementRequestDTO)));

        response.andExpect(status().isOk())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Testa passado valores minimos e maximos existentes no bd, retorna uma lista ordenada")
    void UpdateProduct_ReturnNewAnnouncement_WhenParamsAreValid() throws Exception {

        ResultActions response = mockMvc
                .perform(put("/api/v1/fresh-products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementUpdateRequestDTO)));

        response.andExpect(status().isOk())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Testa passado valores minimos e maximos existentes no bd, retorna uma lista ordenada")
    void UpdateProduct_ThrowException_WhenUserIsNotTheSameSeller() throws Exception {
        announcementUpdateRequestDTO.setSellerId(4L);
        ResultActions response = mockMvc
                .perform(put("/api/v1/fresh-products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(announcementUpdateRequestDTO)));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_NOT_AUTHORIZED)));
    }

//    @Test
//    @DisplayName("Testa passado valores minimos e maximos existentes no bd, retorna uma lista ordenada")
//    void UpdateProduct_ThrowException_WhenParamsAreValid() throws Exception {
//
//        ResultActions response = mockMvc
//                .perform(put("/api/v1/fresh-products/update")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(announcementUpdateRequestDTO)));
//
//        response.andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_NOT_AUTHORIZED)));
//    }

}
