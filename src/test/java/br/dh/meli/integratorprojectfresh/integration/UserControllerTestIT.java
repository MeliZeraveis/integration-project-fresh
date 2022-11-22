package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;
    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        List<User> userList = new ArrayList<>();
        userDTO = new UserDTO("Fulano1234", "123456", "emaildofulano@email.com", "manager");

        user = new User(userDTO);

    }


    @Test
    @DisplayName("Testa se o metodo post retorna uma excessao em caso de falha")
    void save_ReturnUserDTOWithEmailRepet_Fail() throws Exception {

        UserDTO userDTO1 = new UserDTO("Fulano1234", "123456", "fulano@email.com", "manager");

        ResultActions responseTest = mockMvc.perform(post("/api/v1/fresh-products/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print());

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_NAME_OR_EMAIL_ALREADY_REGISTERED)));

    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma excessao em caso de falha")
    void save_ReturnExceptionUserDTOWithUserNameRepet_Fail() throws Exception {

        UserDTO userDTO1 = new UserDTO("Fulano456789", "123456", "emaildofulano@email.com", "manager");

        ResultActions responseTest = mockMvc.perform(post("/api/v1/fresh-products/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print());

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_NAME_OR_EMAIL_ALREADY_REGISTERED)));

    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma excessao em caso de falha")
    void save_ReturnExceptionUserDTOWithEmailFormatInvalid_Fail() throws Exception {

        UserDTO userDTO1 = new UserDTO("Fulano456789", "123456", "email.com", "buyer");

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print());

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is("One or more fields are invalid.")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("email")))
               .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.EMAIL_INCORRET)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma excessao em caso de falha")
    void update_ReturnExceptionWhenEmailIsIncorret_Fail() throws Exception {

        UserDTO userDTO1 = new UserDTO("Fulano456789", "123456", "email.com", "buyer");

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-products/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print());

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is("One or more fields are invalid.")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("email")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.EMAIL_INCORRET)));

    }


    @Test
    @DisplayName("Testa se o metodo retorna erro se o role não existe")
    void getUser_ReturnException_WhenRoleNotExist() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/user/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("role", "toito"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ROLE_IS_NOT_EXIST)));
    }

        @Test
    @DisplayName("Testa se o metodo post armazena os dados corretamente em caso de sucesso")
    void getAll_ReturnUserDTO_Sucess() throws Exception {

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

            response.andExpect(status().isOk())
                .andDo(print());

        assertThat(repo.findAll().get(0)).isNotNull();

    }
//
//    @Test
//    @DisplayName("Testa se o metodo retorna erro se o id não existe")
//    void delete_ReturnException_WhenIdNotExist() throws Exception {
//
//        ResultActions response = mockMvc
//                .perform(delete("/api/v1/fresh-products/user/100")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        response.andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_ID_NOT_FOUND)));
//    }
//
//    @Test
//    @DisplayName("Testa se o metodo retorna erro se o id não existe")
//    void delete_ReturnVoid_Sucess() throws Exception {
//
//        ResultActions response = mockMvc
//                .perform(delete("/api/v1/fresh-products/user/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        response.andExpect(status().isOk())
//                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.USER_ID_NOT_FOUND)));
//    }
//    @Test
//    @DisplayName("Testa se o metodo post armazena os dados corretamente em caso de sucesso")
//    void save_ReturnUserDTO_Sucess() throws Exception {
//
//        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO)));
//
//        response.andExpect(status().isCreated())
//                .andDo(print());
//
//        assertThat(repo.findAll().size()).isNotEqualTo(0);
//        assertThat(repo.findAll().get(0)).isNotNull();
//
//    }



}
