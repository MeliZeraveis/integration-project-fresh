package br.dh.meli.integratorprojectfresh.integration;


import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;

import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Roles;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.*;
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


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private SectionRepository sectionRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private InboundOrderRequestDTO inboundOrderRequestDTO;

    private InboundOrderDTO inboundOrderDTO;

     private List<BatchStockDTO> batchStockList;

    private  List<InboundOrder> inboundOrderList = new ArrayList<>();

    @BeforeEach
    void setup() {
        batchStockRepository.deleteAll();
        inboundOrderRepository.deleteAll();

        jdbcTemplate.execute("ALTER TABLE inbound_order AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE batch_stock AUTO_INCREMENT = 1");

        LocalDate manufacturingDate = LocalDate.parse("2022-03-09");
        LocalDate dueDate = LocalDate.parse("2023-02-01");
        LocalDate orderDate = LocalDate.parse("2022-03-03");
        LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        BatchStockDTO batchStock1 = new BatchStockDTO(1L, "Fresh", 10, manufacturingDate, manufacturingTime, (float) 20.5, dueDate, BigDecimal.valueOf(30.5));
        BatchStockDTO batchStock2 = new BatchStockDTO(2L, "Fresh", 11, manufacturingDate, manufacturingTime, (float) 20.3, dueDate, BigDecimal.valueOf(20.5));

        batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        inboundOrderDTO = new InboundOrderDTO(orderDate, 1L, 1L, batchStockList);

        inboundOrderRequestDTO = new InboundOrderRequestDTO(inboundOrderDTO);

        InboundOrder inboundOrder = new InboundOrder(inboundOrderDTO, 1L);

       inboundOrderList.add(inboundOrder);

        List<BatchStock> batchStockList1 = batchStockList.stream()
                .map(a -> new BatchStock(a, inboundOrder.getOrderNumber()))
                .collect(Collectors.toList());

        List<Announcement> announcementList = new ArrayList<>();

        inboundOrderRepository.save(inboundOrder);
        batchStockRepository.saveAll(batchStockList1);


    }

    @Test
    @DisplayName("Testa se o metodo post armazena os dados corretamente em caso de sucesso")
    void save_ReturnInboundOrderPostResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-08-08");
        inboundOrderDTO.setOrderDate(orderDate2);
        batchStockList.get(0).setOrderNumberId(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(0).setPrice(BigDecimal.valueOf(4000.00));
        batchStockList.get(0).setProductQuantity(100);
        batchStockList.get(1).setOrderNumberId(1L);
        batchStockList.get(1).setBatchNumber(1L);
        batchStockList.get(1).setPrice(BigDecimal.valueOf(2000.00));
        batchStockList.get(1).setVolume((float) 30);
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
    @DisplayName("Testa se o metodo put atualiza os dados corretamente em caso de sucesso")
    void update_ReturnInboundOrderPutResponseDTO_Sucess() throws Exception {
        LocalDate orderDate2 = LocalDate.parse("2022-07-07");
        inboundOrderDTO.setOrderDate(orderDate2);
        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setOrderNumberId(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(0).setPrice(BigDecimal.valueOf(5000.00));
        batchStockList.get(0).setProductQuantity(100);
        batchStockList.get(0).setProductQuantity(100);
        batchStockList.get(1).setBatchNumber(2L);
        batchStockList.get(1).setVolume((float) 30);

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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ORDERDATE é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ORDERDATE informado está incorreta")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando a data de fabricaçao está incorreta")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quandoa data de fabricaçao é nula")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o DUEDATE está incorreto")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o DUEDATE é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o SectionCode é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o WarehouseCode é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o AnnouncementID e é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o SectionType é nulo")
    void save_ReturnExceptionCurrentTemperatureNull_Fail() throws Exception {
        batchStockList.get(0).setSectionType(null);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].sectionType")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TEMPERATURE_REQUIRED)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ProductQuantity informado nao atinge o valor minimo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ProductQuantity é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ManufacturinTime recebe um time com formato invalido")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o ManufacturingTime é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o Volume é nulo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o Volume menor que 1")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o Price é negativo")
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
    @DisplayName("Testa se o metodo post retorna uma exceçao quando o PRICE é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ORDERDATE é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ORDERDATE recebe uma data invalida")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ManufacturingDate recebe uma data invalida")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ManufacturingDate é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o DueDate recebe uma data invalida")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o DueDate é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o SectionCode é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o WarehouseCode é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o AnnouncementId é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o SectionType é nulo")
    void update_ReturnExceptionCurrentTemperatureNull_Fail() throws Exception {
        batchStockList.get(0).setSectionType(null);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.FIELD_NOT_FOUND)))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("inboundOrder.batchStock[0].sectionType")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.TEMPERATURE_REQUIRED)));
    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ProductQuantity declarado nao atinge o valor minimo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ProductQuantity é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ManufacturingTime recebe um time num formato invalido")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o ManufacturingTime é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o Volume é nulo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o Volume é menor que 1")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o Price é negativo")
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
    @DisplayName("Testa se o metodo put retorna uma exceçao quando o Price é nulo")
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

    //Regras de negocio


    //save

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao WAREHOUSE_NOT_ FOUND quando o WarehouseCode é invalido")
    void SaveValidIfWarehouseExist_ReturnExceptionWarehouseNotExist_Fail() throws Exception {
        inboundOrderDTO.setWarehouseCode(-1l);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isNotFound())
              .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.WAREHOUSE_NOT_FOUND)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao MANAGER_NOT_VALID quando o Manager nao é valido")
    void SaveValidIfWarehouseExist_ReturnExceptionManagerNull_Fail() throws Exception {

              inboundOrderDTO.setWarehouseCode(4l);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Manager not valid")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.MANAGER_NOT_VALID)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao SECTION_NOT_FOUND quando o Section é invalido")
    void SavevalidSection_ReturnExceptionWarehouseNotExist_Fail() throws Exception {

        inboundOrderDTO.setSectionCode(500l);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.SECTION_NOT_FOUND)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao INSERT_BATCH_SECTION_INCORRET quando o SectionType é incorreto")
    void SaveValidSection_ReturnExceptionSectionTypeIncorret_Fail() throws Exception {


        inboundOrderDTO.setSectionCode(2L);

        inboundOrderDTO.getBatchStock().get(0).setSectionType("Fresh");

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.INSERT_BATCH_SECTION_INCORRET)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao LIMIT_CAPACITY_SECTION quando a capacidade maxima de estoque é atingida")
    void SaveValidSection_ReturnExceptionCapacityLimit_Fail() throws Exception {
          Section section = sectionRepository.findById(1l).get();

          float teste = section.getMaxCapacity() + section.getUsedCapacity();

        inboundOrderDTO.getBatchStock().get(0).setVolume(teste);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.LIMIT_CAPACITY_SECTION)));
    }

    @Test
    @DisplayName("Testa se o metodo post retorna uma exceçao SECTION_NOT_BELONG_WAREHOUSE o section nao pertence a wahehouse")
    void SaveValidSection_ReturnExceptionSectionNotBelongWarehouse_Fail() throws Exception {
        Section section = sectionRepository.findById(1l).get();

        inboundOrderDTO.setWarehouseCode(2l);
        section.setWarehouseCode(1l);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.SECTION_NOT_BELONG_WAREHOUSE)));
    }

    @Test
    @DisplayName("Testa o metodo SAVE se retorna uma EXCEPTION se o DUEDATE tiver menos de 3 semanas")
    void Save_validBatchDueDateReturnException_Fail() throws Exception {

        LocalDate orderDate2 = LocalDate.now().plusWeeks(2);
        batchStockList.get(0).setDueDate(orderDate2);

        ResultActions response = mockMvc.perform(post("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_EXPIRED)));
    }


    //update

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao INBOUND_ORDER_NOT_FOUND quando o OrderNumber é invalido")
    void Update_ReturnExceptionNotFound_whenInboundOrderNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(77L);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.INBOUND_ORDER_NOT_FOUND)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao BATCH_NOT_FOUND quando o BatchNumber é invalido")
    void Update_ReturnExceptionNotFound_whenBatchNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(77L);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_NOT_FOUND)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao WAREHOUSE_NOT_FOUND quando o WareHouseNumber é invalido")
    void Update_ReturnExceptionNotFound_whenWareHousehNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(-1l);


        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.WAREHOUSE_NOT_FOUND)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao MANAGER_NOT_FOUND quando o User nao é gerente")
    void Update_ReturnExceptionNotFound_whenManagerNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(4l);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Manager not valid")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.MANAGER_NOT_VALID)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao ANNOUNCCEMENT_NOT_FOUND quando o Announcement nao esta registrado")
    void Update_ReturnExceptionNotFound_whenAnnoucementNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(1l);
        batchStockList.get(0).setAnnouncementId(44L);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ANNOUNCEMENT_NOT_FOUND)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao SECTOPM_NOT_FOUND quando a Section nao esta registrado")
    void Update_ReturnExceptionNotFound_whenSectiontNotExist_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(1l);
        batchStockList.get(0).setAnnouncementId(1L);
        inboundOrderDTO.setSectionCode(44l);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.SECTION_NOT_FOUND)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao INSERT_BATCH_SECTION_INCORRET quando o Section tybe está incorreto")
    void Update_ReturnSectionTypeException_whenTypeSectionNotEquals_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(1l);
        batchStockList.get(0).setAnnouncementId(1L);
        inboundOrderDTO.setSectionCode(2L);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Section Type incorrect")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.INSERT_BATCH_SECTION_INCORRET)));

    }

    @Test
    @DisplayName("Testa se o metodo put retorna uma exceçao LIMIT_CAPACITY_SECTION quando o Volume ultrapassa o limite de capacidade")
    void Update_ReturnExceptionNotFound_whenNotEquals_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);
        inboundOrderDTO.setWarehouseCode(1l);
        batchStockList.get(0).setAnnouncementId(1L);
        inboundOrderDTO.setSectionCode(1L);
        batchStockList.get(0).setVolume((float)30000);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.LIMIT_CAPACITY_SECTION)));

    }

    @Test
    @DisplayName(("Testa o metodo UPDATE se retorna uma EXCEPTION se o DUEDATE tiver menos de 3 semanas"))
    void Update_validBatchDueDateReturnException_Fail() throws Exception {

        inboundOrderDTO.setOrderNumber(1L);
        batchStockList.get(0).setBatchNumber(1L);
        batchStockList.get(1).setBatchNumber(2L);

        LocalDate orderDate2 = LocalDate.now().plusWeeks(2);
        batchStockList.get(0).setDueDate(orderDate2);

        ResultActions response = mockMvc.perform(put("/api/v1/fresh-product/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inboundOrderRequestDTO)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Action not allowed")))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_EXPIRED)));

    }
}