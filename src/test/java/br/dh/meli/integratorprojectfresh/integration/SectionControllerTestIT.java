package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.model.Warehouse;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import br.dh.meli.integratorprojectfresh.repository.WarehouseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SectionControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WarehouseRepository warehouseRepo;
    @Autowired
    private SectionRepository sectionRepo;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    User userTest;
    Warehouse warehouseTest;
    Section sectionTest;
    SectionRequestDTO sectionRequestTest;


    @BeforeEach
    void setup() {

        jdbcTemplate.execute("ALTER TABLE section AUTO_INCREMENT = 1");

        userTest = new User(1L,"test","123456","test@email.com", Roles.MANAGER);
        warehouseTest = new Warehouse(1L,"Teste","AddressTest","T",new ArrayList<>(),new ArrayList<>(),userTest);
        sectionTest = new Section(1L, "a", 50.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), warehouseTest);
        sectionRequestTest = new SectionRequestDTO(sectionTest);

        sectionRepo.save(sectionTest);
    }

    @Test
    @DisplayName("Erro ao enviar SectionType invalido Save/Post")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenSectionTypeIsInvalid() throws Exception {

        sectionTest.setType("a");

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/sections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sectionRequestTest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("type")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.SECTION_NOT_VALID)));
    }

}
