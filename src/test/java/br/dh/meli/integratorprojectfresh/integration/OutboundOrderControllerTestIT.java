package br.dh.meli.integratorprojectfresh.integration;

import br.dh.meli.integratorprojectfresh.dto.request.*;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.model.*;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.OutboundOrderBatchesRepository;
import br.dh.meli.integratorprojectfresh.repository.OutboundOrderRepository;
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
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OutboundOrderControllerTestIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private OutboundOrderRepository orderRepo;
  @Autowired
  private OutboundOrderBatchesRepository batchesRepo;
  @Autowired
  private BatchStockRepository batchStockRepo;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  OutboundOrderDTO outboundOrderDTO1, outboundOrderDTO2;
  OutboundOrder outboundOrder;
  OutboundOrderRequestDTO outboundOrderRequestDTO1, outboundOrderRequestDTO2;
  OutboundOrderResponseDTO outboundOrderResponseDTO;
  OutboundOrderBatches outboundOrderBatches;
  List<OutboundOrderBatches> outboundOrderBatchesList;
  List<BatchStockDTO> batchStockList;
  List<BatchStock> batchStockList2;

  @BeforeEach
  void setup() {
    batchesRepo.deleteAll();
    batchStockRepo.deleteAll();
    orderRepo.deleteAll();

    jdbcTemplate.execute("ALTER TABLE outbound_order AUTO_INCREMENT = 1");
    jdbcTemplate.execute("ALTER TABLE outbound_order_batches AUTO_INCREMENT = 1");

    LocalDate manufacturingDate = LocalDate.parse("2022-03-09");
    LocalDate dueDate1 = LocalDate.parse("2022-02-01");
    LocalDate dueDate2 = LocalDate.parse("2023-02-01");
    LocalDate orderDate = LocalDate.parse("2022-11-09");
    LocalDateTime manufacturingTime = LocalDateTime.parse("2020-03-09 17:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    BatchStockDTO batchStock1 = new BatchStockDTO(1L, "Fresh", 10, manufacturingDate, manufacturingTime, (float) 1.5, dueDate1, BigDecimal.valueOf(30.5));
    BatchStockDTO batchStock2 = new BatchStockDTO(1L, "Fresh", 11, manufacturingDate, manufacturingTime, (float) 1.3, dueDate1, BigDecimal.valueOf(20.5));
    BatchStockDTO batchStock3 = new BatchStockDTO(1L, "Fresh", 12, manufacturingDate, manufacturingTime, (float) 1.3, dueDate2, BigDecimal.valueOf(20.5));

    outboundOrderDTO1 = new OutboundOrderDTO(1L, orderDate, 2L, 1L, List.of(1L, 2L));
    outboundOrderRequestDTO1 = new OutboundOrderRequestDTO(outboundOrderDTO1);
    outboundOrderDTO2 = new OutboundOrderDTO(1L, orderDate, 2L, 1L, List.of(3L));
    outboundOrderRequestDTO2 = new OutboundOrderRequestDTO(outboundOrderDTO2);
    outboundOrder = new OutboundOrder(outboundOrderDTO1, 1L);

    batchStockList = new ArrayList<>();
    batchStockList.add(batchStock1);
    batchStockList.add(batchStock2);
    batchStockList.add(batchStock3);
    batchStockList2 = batchStockList.stream().map(a -> new BatchStock(a, outboundOrder.getOrderNumber())).collect(Collectors.toList());


    outboundOrderBatchesList = batchStockList2.stream().map(OutboundOrderBatches::new).collect(Collectors.toList());
    outboundOrderResponseDTO = new OutboundOrderResponseDTO(outboundOrder, outboundOrderBatchesList);

    orderRepo.save(outboundOrder);
    batchStockRepo.saveAll(batchStockList2);
    batchesRepo.saveAll(outboundOrderBatchesList);
  }

  @Test
  @DisplayName("Sucesso GET")
  void get_ReturnannoucementGetResponseDTO_Sucess() throws Exception {
    ResultActions response = mockMvc.perform(get(Routes.BASE_ROUTE + Routes.OUTBOUND_ORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .param("id", "1"))
            .andDo(print());

    response.andExpect(status().isOk());
  }

  @Test
  @DisplayName("Sucesso Save/Post")
  void SaveMethod_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {
    ResultActions response = mockMvc.perform(post(Routes.BASE_ROUTE + Routes.OUTBOUND_ORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(outboundOrderRequestDTO2)));

    response.andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Falha Save/Post quando o lote não está vencido")
  void SaveMethod_ThrowException_WhenBatchNotExpired() throws Exception {
    ResultActions response = mockMvc.perform(post(Routes.BASE_ROUTE + Routes.OUTBOUND_ORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(outboundOrderRequestDTO1)));

    response.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
            .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_NOT_FOUND)));
  }

  @Test
  @DisplayName("Sucesso Update/Put")
  void UpdateMethod_ReturnPurchaseOrderResponseDTO_WhenSucess() throws Exception {
    ResultActions response = mockMvc.perform(put(Routes.BASE_ROUTE + Routes.OUTBOUND_ORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(outboundOrderRequestDTO2)));

    response.andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Falha Updade/Put quando o lote não está vencido")
  void UpdateMethod_ThrowException_WhenBatchNotExpired() throws Exception {
    ResultActions response = mockMvc.perform(put(Routes.BASE_ROUTE + Routes.OUTBOUND_ORDER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(outboundOrderRequestDTO1)));

    response.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.name())))
            .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.BATCH_NOT_FOUND)));
  }
}
