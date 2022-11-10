package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private InboundOrderRequestDTO inboundOrderRequestDTO;

    private InboundOrder inboundOrder;

    private List<BatchStock> batchStockList;

    @BeforeEach
    void setup() {
        batchStockRepository.deleteAll();
        inboundOrderRepository.deleteAll();

        jdbcTemplate.execute("ALTER TABLE inbound_order AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE batch_stock AUTO_INCREMENT = 1");

        LocalDate manufacturingDate = LocalDate.parse("2022-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStock batchStock = new BatchStock(1L, (float) 1.05, 10, manufacturingDate, manufacturingTime, (float) 1.5, manufacturingDate, BigDecimal.valueOf(30.5));
        BatchStock batchStock1 = new BatchStock(2L, (float) 2.05, 11, manufacturingDate, manufacturingTime, (float) 1.3, manufacturingDate, BigDecimal.valueOf(20.5));
        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock1);
        inboundOrder = new InboundOrder(1L, orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrder);

        List<BatchStockDTO> batchStockDTO = batchStockList.stream()
                .map(BatchStockDTO::new)
                .collect(Collectors.toList());


        List<BatchStock> batchStockList1 = batchStockList.stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
                .collect(Collectors.toList());


        inboundOrderRepository.save(inboundOrder);
        batchStockRepository.saveAll(batchStockList1);

    }

    @Test
    void save_ReturnInboundOrderPostResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-08-08");
        inboundOrder.setOrderDate(orderDate2);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isCreated());

        assertThat(inboundOrderRepository.findAll().size()).isEqualTo(2);
        assertThat(inboundOrderRepository.findAll().get(0)).isNotNull();
        assertThat(batchStockRepository.findAll().size()).isEqualTo(2);
        assertThat(batchStockRepository.findAll().get(0)).isNotNull();
    }

//    @Test
//    void save_ReturnInboundOrderPostResponseDTO_NotSucess() throws Exception{
//        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

    //        response.andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.message)))
//                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
//                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomName")));

    //  .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_NAME_SIZE_NOT_VALID)))
//}
    @Test
    void update_ReturnInboundOrderPutResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-07-07");
        inboundOrder.setOrderDate(orderDate2);
        batchStockList.get(0).setOrderNumberId(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(0).setPrice(BigDecimal.valueOf(5000.00));
        batchStockList.get(0).setProductQuantity(400);
        batchStockList.get(1).setBatchNumber(2L);
        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isCreated())
                .andDo(print());

        assertThat(inboundOrderRepository.findAll().size()).isEqualTo(1);
        assertThat(inboundOrderRepository.findAll().get(0)).isNotNull();
        assertThat(batchStockRepository.findAll().size()).isEqualTo(2);
        assertThat(batchStockRepository.findAll().get(0)).isNotNull();

    }

//    @Test
//    void update_ReturnInboundOrderPutResponseDTO_NotSucess() throws Exception{
//        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));
// response.andExpect(status().isBadRequest())
    //        response.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.message)))
//                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
//                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomName")));
//.andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_NAME_SIZE_NOT_VALID)))
}
