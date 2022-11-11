package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.InboundOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    private InboundOrderDTO inboundOrderDTO;

    private List<BatchStockDTO> batchStockList;

    @BeforeEach
    void setup() {
        batchStockRepository.deleteAll();
        inboundOrderRepository.deleteAll();

        jdbcTemplate.execute("ALTER TABLE inbound_order AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE batch_stock AUTO_INCREMENT = 1");

        LocalDate manufacturingDate = LocalDate.parse("2022-02-01");
        LocalDate dueDate = LocalDate.parse("2023-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStockDTO batchStock = new BatchStockDTO( 1L, (float) 1.05, 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate,BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock1 = new BatchStockDTO( 2L, (float) 2.05, 11, manufacturingDate, manufacturingTime, (float) 1.3,dueDate , BigDecimal.valueOf(20.5));
        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock1);
        inboundOrderDTO = new InboundOrderDTO(1L, orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrderDTO);

        InboundOrder inboundOrder = new InboundOrder(inboundOrderDTO, 1L);

        List<BatchStock> batchStockList1 = batchStockList.stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
                .collect(Collectors.toList());


        inboundOrderRepository.save(inboundOrder);
        batchStockRepository.saveAll(batchStockList1);

    }

    @Test
    void save_ReturnInboundOrderPostResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-08-08");
        inboundOrderDTO.setOrderDate(orderDate2);
        inboundOrderDTO.setOrderDate(orderDate2);
        batchStockList.get(0).setOrderNumberId(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(0).setPrice(BigDecimal.valueOf(4000.00));
        batchStockList.get(0).setProductQuantity(400);
        batchStockList.get(1).setOrderNumberId(1L);
        batchStockList.get(1).setBatchNumber(1L);
        batchStockList.get(1).setPrice(BigDecimal.valueOf(2000.00));
        batchStockList.get(1).setProductQuantity(700);
        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isCreated());

        assertThat(inboundOrderRepository.findAll().size()).isEqualTo(2);
        assertThat(inboundOrderRepository.findAll().get(0)).isNotNull();
        assertThat(batchStockRepository.findAll().size()).isEqualTo(4);
        assertThat(batchStockRepository.findAll().get(0)).isNotNull();
    }

    @Test
    void update_ReturnInboundOrderPutResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-07-07");
        inboundOrderDTO.setOrderDate(orderDate2);
        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setOrderNumberId(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(0).setPrice(BigDecimal.valueOf(5000.00));
        batchStockList.get(0).setProductQuantity(200);
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

    @Test
    void save_ReturnExceptionFieldOrderDateNull_Fail() throws Exception {
        inboundOrderDTO.setOrderDate(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
               .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
               .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.orderDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionFieldOrderDateFuture_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2023-07-07");
        inboundOrderDTO.setOrderDate(orderDate2);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.orderDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_PAST_OR_PRESENT)));
    }

    @Test
    void save_ReturnExceptionManufacturingDateDateFuture_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2023-07-07");
        batchStockList.get(0).setManufacturingDate(orderDate2);


        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_PAST_OR_PRESENT)));
    }

    @Test
    void save_ReturnExceptionManufacturingDateNull_Fail() throws Exception {
        batchStockList.get(0).setManufacturingDate(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionDueDateDatePast_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2019-02-01");
        batchStockList.get(0).setDueDate(orderDate2);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].dueDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_FUTURE)));
    }

    @Test
    void save_ReturnExceptionDueDateNull_Fail() throws Exception {
        batchStockList.get(0).setDueDate(null);


        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].dueDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }
    @Test
    void save_ReturnExceptionSectionCodeNull_Fail() throws Exception {
        inboundOrderDTO.setSectionCode(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.sectionCode")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.SECTION_CODE_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionWarehouseCodeNull_Fail() throws Exception {
        inboundOrderDTO.setWarehouseCode(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.warehouseCode")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.WAREHOUSE_CODE_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionAnnouncementIdNull_Fail() throws Exception {
        batchStockList.get(0).setAnnouncementId(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].announcementId")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ANNOUNCEMENT_ID_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionCurrentTemperatureNull_Fail() throws Exception {
        batchStockList.get(0).setCurrentTemperature(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].currentTemperature")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TEMPERATURE_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionProductQuantitySmaller1_Fail() throws Exception {
        batchStockList.get(0).setProductQuantity(0);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].productQuantity")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.QUANTITY_MIN_VALUE)));
    }


    @Test
    void save_ReturnExceptionProductQuantityIsNull_Fail() throws Exception {
        batchStockList.get(0).setProductQuantity(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].productQuantity")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.QUANTITY_REQUIRED)));
    }
    @Test
    void save_ReturnExceptionManufacturingTimeDateFuture_Fail() throws Exception {
        LocalDateTime orderDate2 = LocalDateTime.parse("2024-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        batchStockList.get(0).setManufacturingTime(orderDate2);


        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingTime")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TIME_PAST_OR_PRESENT)));
    }

    @Test
    void save_ReturnExceptionManufacturingTimeNull_Fail() throws Exception {
        batchStockList.get(0).setManufacturingTime(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingTime")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TIME_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionVolumeNull_Fail() throws Exception {
        batchStockList.get(0).setVolume(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].volume")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.VOLUME_REQUIRED)));
    }

    @Test
    void save_ReturnExceptionVolumeZero_Fail() throws Exception {
        float num = 0;
        batchStockList.get(0).setVolume(num);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].volume")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.VOLUME_MIN_VALUE)));
    }

    @Test
    void save_ReturnExceptionPriceNegative_Fail() throws Exception {
        batchStockList.get(0).setPrice(BigDecimal.valueOf(-30.5));

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].price")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.FIELD_MIN_VALUE)));
    }

    @Test
    void save_ReturnExceptionPriceNull_Fail() throws Exception {
        batchStockList.get(0).setPrice(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].price")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.PRICE_REQUIRED)));
    }

  //PUT - UPDATE --------------------------------

    @Test
    void update_ReturnExceptionFieldOrderDateNull_Fail() throws Exception {
        inboundOrderDTO.setOrderDate(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.orderDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionFieldOrderDateFuture_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2023-07-07");
        inboundOrderDTO.setOrderDate(orderDate2);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.orderDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_PAST_OR_PRESENT)));
    }

    @Test
    void update_ReturnExceptionManufacturingDateDateFuture_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2023-07-07");
        batchStockList.get(0).setManufacturingDate(orderDate2);


        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_PAST_OR_PRESENT)));
    }

    @Test
    void update_ReturnExceptionManufacturingDateNull_Fail() throws Exception {
        batchStockList.get(0).setManufacturingDate(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionDueDateDatePast_Fail() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2019-02-01");
        batchStockList.get(0).setDueDate(orderDate2);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].dueDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_FUTURE)));
    }

    @Test
    void update_ReturnExceptionDueDateNull_Fail() throws Exception {
        batchStockList.get(0).setDueDate(null);


        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].dueDate")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DATE_REQUIRED)));
    }
    @Test
    void update_ReturnExceptionSectionCodeNull_Fail() throws Exception {
        inboundOrderDTO.setSectionCode(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.sectionCode")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.SECTION_CODE_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionWarehouseCodeNull_Fail() throws Exception {
        inboundOrderDTO.setWarehouseCode(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.warehouseCode")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.WAREHOUSE_CODE_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionAnnouncementIdNull_Fail() throws Exception {
        batchStockList.get(0).setAnnouncementId(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].announcementId")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ANNOUNCEMENT_ID_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionCurrentTemperatureNull_Fail() throws Exception {
        batchStockList.get(0).setCurrentTemperature(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].currentTemperature")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TEMPERATURE_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionProductQuantitySmaller1_Fail() throws Exception {
        batchStockList.get(0).setProductQuantity(0);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].productQuantity")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.QUANTITY_MIN_VALUE)));
    }

    @Test
    void update_ReturnExceptionProductQuantityIsNull_Fail() throws Exception {
        batchStockList.get(0).setProductQuantity(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].productQuantity")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.QUANTITY_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionManufacturingTimeDateFuture_Fail() throws Exception {
        LocalDateTime orderDate2 = LocalDateTime.parse("2024-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        batchStockList.get(0).setManufacturingTime(orderDate2);


        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingTime")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TIME_PAST_OR_PRESENT)));
    }

    @Test
    void update_ReturnExceptionManufacturingTimeNull_Fail() throws Exception {
        batchStockList.get(0).setManufacturingTime(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].manufacturingTime")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TIME_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionVolumeNull_Fail() throws Exception {
        batchStockList.get(0).setVolume(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].volume")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.VOLUME_REQUIRED)));
    }

    @Test
    void update_ReturnExceptionVolumeZero_Fail() throws Exception {
        float num = 0;
        batchStockList.get(0).setVolume(num);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].volume")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.VOLUME_MIN_VALUE)));
    }

    @Test
    void update_ReturnExceptionPriceNegative_Fail() throws Exception {
        batchStockList.get(0).setPrice(BigDecimal.valueOf(-30.5));

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].price")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.FIELD_MIN_VALUE)));
    }

    @Test
    void update_ReturnExceptionPriceNull_Fail() throws Exception {
        batchStockList.get(0).setPrice(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].price")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.PRICE_REQUIRED)));
    }

}
