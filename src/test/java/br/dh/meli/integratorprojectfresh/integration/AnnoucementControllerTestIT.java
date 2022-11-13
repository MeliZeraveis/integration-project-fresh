package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.SectionDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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
    void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {

            ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/list?id=1")
                            .contentType(MediaType.APPLICATION_JSON));

//            Long teste = announcement.getSection().getInboundOrder().get(0).getWarehouseCode();
//            Long teste2 = announcement.getSection().getSectionCode();

            response.andExpect(status().isOk())
                    .andExpect(jsonPath("$.productId", CoreMatchers.is(1)));

    }


//    @GetMapping("/list")
//    ResponseEntity<AnnoucementGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long id) {
//        AnnoucementGetResponseDTO announcementList = service.getAnnouncementByAnnouncementId(id);
//        return new ResponseEntity<>(announcementList, HttpStatus.OK);
//    }
}
