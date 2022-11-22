package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.request.SectionRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionDetailResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionListFilterResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SectionListWarehouseResponseDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

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
    SectionResponseDTO sectionResponseTest;
    SectionRequestDTO sectionRequestTest;
    SectionDetailResponseDTO sectionDetailResponseTest;
    SectionListFilterResponseDTO sectionFilterResponseTest;
    List<SectionListFilterResponseDTO> sectionListFilterResponseTest;
    SectionListWarehouseResponseDTO sectionListWarehouseResponseTest;
    Section sectionTest;
    Warehouse warehouseTest;
    String orderBy;


    @BeforeEach
    void setup() {

        jdbcTemplate.execute("ALTER TABLE section AUTO_INCREMENT = 1");

        userTest = new User(1L,"test","123456","test@email.com", Roles.MANAGER);
        warehouseTest = new Warehouse(1L,"Teste","AddressTest","TR",new ArrayList<>(),new ArrayList<>(),userTest);
        sectionTest = new Section(1L, "Fresh", 50.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), warehouseTest);
        Section sectionTest2 = new Section(2L, "Refrigerated", 50.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), warehouseTest);
        sectionRequestTest = new SectionRequestDTO(sectionTest);
        sectionResponseTest = new SectionResponseDTO(sectionTest);
        sectionDetailResponseTest = new SectionDetailResponseDTO(sectionTest);
        sectionFilterResponseTest = new SectionListFilterResponseDTO(sectionTest);
        SectionListFilterResponseDTO sectionFilterResponseTest2 = new SectionListFilterResponseDTO(sectionTest2);

        sectionListFilterResponseTest = new ArrayList<>();
        sectionListFilterResponseTest.add(sectionFilterResponseTest);
        sectionListFilterResponseTest.add(sectionFilterResponseTest2);
        orderBy = "desc";
        sectionListWarehouseResponseTest = new SectionListWarehouseResponseDTO(warehouseTest, orderBy);

        sectionRepo.save(sectionTest);
    }

    @Test
    @DisplayName("Erro ao enviar SectionType invalido Save/Post")
    void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenSectionTypeIsInvalid() throws Exception {

        sectionRequestTest.setType("a");

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-products/sections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sectionRequestTest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("type")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.SECTION_NOT_VALID)));
    }

    @Test
    @DisplayName("Not Found Exception - GET")
    void GetSectionByIdMethod_ReturnSectionDetailResponseDTO_WhenIdIsInvalid() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/sections/{id}",99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.SECTION_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Not Found Exception - GET")
    void GetSectionListMethod_ReturnSectionDetailResponseDTO_WhenWarehouseIsInvalid() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("warehouseName", "id")
                        .param("orderBy","desc"))
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.WAREHOUSE_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    @DisplayName("Not Found Exception - GET")
    void GetSectionListMethod_ReturnSectionDetailResponseDTO_WhenOrderByIsInvalid() throws Exception {

        ResultActions response = mockMvc
                .perform(get("/api/v1/fresh-products/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("warehouseName", "MLBSP01")
                        .param("orderBy","lll"))
                .andDo(print());

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ORDER_TYPE_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.BAD_REQUEST.value())));
    }



}
