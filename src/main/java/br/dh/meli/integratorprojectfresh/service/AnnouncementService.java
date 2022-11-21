package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Sections;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService {
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

  @Override
  public List<AnnouncementListResponseDTO> findAnnouncementByQueryString(String queryString) {
    List<Announcement> announcement = repo.findByNameContainingIgnoreCase(queryString);
    if (announcement.isEmpty()) {
      throw new NotFoundException(Msg.QUERY_STRING_NOT_FOUND);
    }
    return announcement.stream()
            .map(AnnouncementListResponseDTO::new)
            .collect(Collectors.toList());
  }

  @Override
  public List<AnnouncementListResponseDTO> findAnnouncementByPrice(BigDecimal min, BigDecimal max) {
    List<Announcement> announcement = repo.findByPriceBetweenOrderByPrice(min, max);
    if (announcement.isEmpty()) {
      throw new NotFoundException(Msg.PRICE_MIN_MAX);
    }
    return announcement.stream()
            .map(AnnouncementListResponseDTO::new)
            .collect(Collectors.toList());
  }

  @Override
  public AnnouncementRequestDTO updateById(AnnouncementRequestDTO announcementRequestDTO, Long id) {
    Optional<Announcement> announcement = repo.findById(id);
    if (announcement.isEmpty()) {
      throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
    }
    Announcement announcementUpdate = announcement.get();

    announcementUpdate.setName(announcementRequestDTO.getName());
    announcementUpdate.setDescription(announcementRequestDTO.getDescription());
    announcementUpdate.setPrice(announcementRequestDTO.getPrice());
    announcementUpdate.setSectionCode(announcementRequestDTO.getSectionCode());
    repo.save(announcementUpdate);
    return new AnnouncementRequestDTO(announcementUpdate);
  }
}
