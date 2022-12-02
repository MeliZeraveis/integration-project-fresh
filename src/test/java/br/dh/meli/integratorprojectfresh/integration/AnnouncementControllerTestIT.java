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
public class AnnouncementControllerTestIT {
    private final String ROUTE = Routes.BASE_ROUTE + Routes.PRODUCT_LIST;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testa se o método retorna o anúncio por categoria em caso de sucesso")
    void get_ReturnAllAnnoucementGetResponseDTO_Sucess() throws Exception {
        ResultActions response = mockMvc.perform(get(Routes.BASE_ROUTE)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa se o método retorna o anúncio por categoria em caso de sucesso")
    void get_ReturnannoucementCategoryGetResponseDTO_Sucess() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("category", "FS"))
                .andDo(print());

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testa se o método retorna erro se a categoria não existe")
    void get_ReturnannoucementCategoryGetResponseDTO_WhenCategoryNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("category", "FA"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.CATEGORY_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Testa se o método retorna o anúncio correto em caso de sucesso")
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Testa se o método retorna uma mensagem NOT FOUND quando é informado o ID de um anúncio que nao existe")
    void get_ReturnExceptionNotFound_WhenProductNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "77"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ANNOUNCEMENT_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Testa se o método retorna o anúncio correto quando informado o ID e a categoria de ordenação em caso de sucesso")
    void get_ReturnAnnouncementGetResponseDTOByLetra_Sucess() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("sortBy", "V"))
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.announcementId", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Testa se o método retorna uma mensagem NOT FOUND quando é informado o ID correto com uma categoria inexistente")
    void get_ReturnExceptionNotFound_WhenProductByLetraNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get(ROUTE)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("sortBy", "a"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.LETTER_NOT_VALID)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }
}