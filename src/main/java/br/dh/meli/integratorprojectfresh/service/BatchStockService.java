package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Sections;
import br.dh.meli.integratorprojectfresh.exception.InvalidParamException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

/**
 * The Batch stock service.
 */
@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {
  private final BatchStockRepository batchRepo;
  private final SectionRepository sectionRepo;

  private BatchStockGetResponseDTO filterBatchList(List<BatchStock> batchStock, String category, String sortBy) {
    if (batchStock.isEmpty()) {
      throw new NotFoundException(Msg.BATCH_NOT_FOUND);
    }

    String section;
    try {
      section = Sections.getSectionByCode(category).getName();
    } catch (Exception e) {
      throw new NotFoundException(Msg.CATEGORY_NOT_FOUND);
    }

    final Map<String, Comparator<BatchStock>> comparators = Map.of(
            "asc", Comparator.comparing(BatchStock::getDueDate),
            "desc", Comparator.comparing(BatchStock::getDueDate).reversed()
    );
    try {
      return new BatchStockGetResponseDTO(batchStock.stream()
              .filter(b -> Objects.equals(b.getSectionType(), section))
              .sorted(comparators.get(sortBy.toLowerCase()))
              .collect(Collectors.toList()));
    } catch (ClassCastException | NullPointerException e) {
      throw new InvalidParamException(Msg.ORDER_BY_NOT_VALID);
    }
  }

  @Override
  public BatchStockGetResponseDTO getBatchStockByBatchStockId(Integer numberOfDays, Long sectionCode) {
    Section section = sectionRepo.findById(sectionCode).orElseThrow(() -> new NotFoundException(Msg.SECTION_NOT_FOUND));
    if (section.getInboundOrder().isEmpty()){
      throw new NotFoundException(Msg.BATCH_NOT_FOUND);
    }

    final List<BatchStock> batchStockList = new ArrayList<>();
    section.getInboundOrder().forEach(i -> batchStockList.addAll(i.getBatchStock()));

    List<BatchStock> filteredBatchStockList = batchStockList.stream()
            .filter(b -> b.getDueDate().isAfter(now()) && now().plusDays(numberOfDays).isAfter(b.getDueDate()))
            .collect(Collectors.toList());
    if (filteredBatchStockList.isEmpty()){
      throw new NotFoundException(Msg.BATCH_NOT_FOUND);
    }

    return new BatchStockGetResponseDTO(filteredBatchStockList);
  }

  @Override
  public BatchStockGetResponseDTO findBatchStockByBatchStockNumber(Integer numberOfDays, String category, String sortBy) {
    return filterBatchList(batchRepo.findAllByDueDateBetween(now(), now().plusDays(numberOfDays)), category, sortBy);
  }

  @Override
  public BatchStockGetResponseDTO findExpiredBatchStock(LocalDate date, String category, String sortBy) {
    return filterBatchList(batchRepo.findAllByDueDateBefore(date), category, sortBy);
  }
}
