package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
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

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final BatchStockRepository repo;

    private final SectionRepository sectionRepo;
    @Override
    public BatchStockGetResponseDTO getBatchStockByBatchStockId(Integer numberOfDays, Long sectionCode) {
        Optional<Section> sectionOptional = sectionRepo.findById(sectionCode);
        if(sectionOptional.isEmpty()) {
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }
        if (sectionOptional.get().getInboundOrder().isEmpty()){
            throw new NotFoundException(Msg.BATCH_NOT_FOUND);
        }
        List<BatchStock> batchStockList = new ArrayList<>();
         sectionOptional.get().getInboundOrder()
                .stream().forEach(i -> batchStockList.addAll(i.getBatchStock()));

        List<BatchStock>batchStockListFiltered = batchStockList.stream()
                .filter(b -> b.getDueDate().isAfter(LocalDate.now())
                        && LocalDate.now().plusDays(numberOfDays).isAfter(b.getDueDate()))
                .collect(Collectors.toList());

        if (batchStockListFiltered.isEmpty()){
            throw new NotFoundException(Msg.BATCH_NOT_FOUND);
        }
        return new BatchStockGetResponseDTO(batchStockListFiltered);

    }

    @Override
    public BatchStockGetResponseDTO findBatchStockByBatchStockNumber(Integer numberOfDays, String category, String order) {
        List<BatchStock> batchStock = repo.findAllByDueDateBetween(LocalDate.now(),LocalDate.now().plusDays(numberOfDays));
        if(batchStock.isEmpty()) {
            throw new NotFoundException(Msg.BATCH_NOT_FOUND);
        }

        if(category.equalsIgnoreCase("FS")) {
            batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Fresh")).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("RF")) {
            batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Refrigerated")).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("FF")) {
            batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Frozen")).collect(Collectors.toList());
        }else {
            throw new NotFoundException(Msg.CATEGORY_NOT_FOUND);
        }

        if (order.toLowerCase().equals("desc")){
            System.out.println(order);
            batchStock = batchStock.stream().sorted(Comparator.comparing(BatchStock::getDueDate).reversed()).collect(Collectors.toList());
        }
        else {
            System.out.println("asc");
            batchStock = batchStock.stream().sorted(Comparator.comparing(BatchStock::getDueDate)).collect(Collectors.toList());
        }
        return new BatchStockGetResponseDTO(batchStock);
    }
}
