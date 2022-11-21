package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Sections;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public interface IBatchStockService {
    BatchStockGetResponseDTO getBatchStockByBatchStockId(Integer numberOfDays, Long sectionCode);

    BatchStockGetResponseDTO findBatchStockByBatchStockNumber(Integer numberOfDays, String category, String order);

    @Service
    @RequiredArgsConstructor
    class BatchStockService implements IBatchStockService {

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
            String section;
            try {
                section = Sections.getSectionByCode(category).getName();
            } catch (Exception e) {
                throw new NotFoundException(Msg.CATEGORY_NOT_FOUND);
            }

            List<BatchStock> batchStock = repo.findAllByDueDateBetween(LocalDate.now(),LocalDate.now().plusDays(numberOfDays));
            if(batchStock.isEmpty()) {
                throw new NotFoundException(Msg.BATCH_NOT_FOUND);
            }

            batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), section)).collect(Collectors.toList());

            if (order.toLowerCase().equals("desc")){
                batchStock = batchStock.stream().sorted(Comparator.comparing(BatchStock::getDueDate).reversed()).collect(Collectors.toList());
            } else {
                batchStock = batchStock.stream().sorted(Comparator.comparing(BatchStock::getDueDate)).collect(Collectors.toList());
            }
            return new BatchStockGetResponseDTO(batchStock);
        }
    }

    @Service
    @RequiredArgsConstructor
    class AnnouncementService implements IAnnouncementService {
      private final AnnouncementRepository repo;

      @Override
      public List<AnnouncementListResponseDTO> getAllAnnouncements() {
        List<Announcement> announcements = repo.findAll();
        if (announcements.isEmpty()) {
          throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
        }
        return announcements.stream()
                .map(AnnouncementListResponseDTO::new)
                .collect(Collectors.toList());
      }

      @Override
      public List<AnnouncementListResponseDTO> getAnnouncementsByCategory(String category) {
        String section;
        try {
          section = Sections.getSectionByCode(category).getName();
        } catch (Exception e) {
          throw new NotFoundException(Msg.CATEGORY_NOT_FOUND);
        }
        List<Announcement> announcements = repo.findBySectionType(section);
        if (announcements.isEmpty()) {
          throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
        }
        return announcements.stream()
                .map(AnnouncementListResponseDTO::new)
                .collect(Collectors.toList());
      }

      @Override
      public AnnouncementGetResponseDTO getAnnouncementByAnnouncementId(Long id) {
        Announcement announcement = repo.findById(id).orElseThrow(() -> new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND));
        return new AnnouncementGetResponseDTO(announcement);
      }

      @Override
      public AnnouncementGetResponseDTO findAnnouncementByBatchStockNumber(Long id, Character sortBy) {
        Optional<Announcement> announcement = repo.findById(id);

        if(announcement.isEmpty()) {
          throw new NotFoundException(Msg.ANNOUNCEMENT_IS_EMPTY);
        }

        if (sortBy.equals('L') || sortBy.equals('Q') || sortBy.equals('V')) {
          return new AnnouncementGetResponseDTO(announcement.get(), sortBy);
        } else {
          throw new NotFoundException(Msg.LETTER_NOT_VALID);
        }
      }
    }
}
