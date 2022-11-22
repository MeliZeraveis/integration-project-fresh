package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementPostRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementUpdateRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.Sections;
import br.dh.meli.integratorprojectfresh.exception.ActionNotAllowedException;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.exception.UnauthorizedException;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.User;
import br.dh.meli.integratorprojectfresh.repository.AnnouncementRepository;
import br.dh.meli.integratorprojectfresh.repository.SectionRepository;
import br.dh.meli.integratorprojectfresh.repository.UserRepository;
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
  private final UserRepository userRepo;
  private final SectionRepository sectionRepo;

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
  public AnnouncementUpdateRequestDTO updateById(AnnouncementUpdateRequestDTO announcementRequestDTO) {
    Optional<Announcement> announcement = repo.findById(announcementRequestDTO.getId());
    if (announcement.isEmpty()) {
      throw new NotFoundException(Msg.ANNOUNCEMENT_NOT_FOUND);
    }
    if (!announcement.get().getSellerId().equals(announcementRequestDTO.getSellerId())) {
      throw new UnauthorizedException(Msg.USER_NOT_AUTHORIZED);
    }
    Optional<User> user = userRepo.findById(announcementRequestDTO.getSellerId());
    if (user.isEmpty()) {
      throw new NotFoundException(Msg.SELLER_NOT_FOUND);
    }
    if (!user.get().getRole().equals("seller")){
      throw new UnauthorizedException(Msg.USER_NOT_AUTHORIZED);
    }
    Optional<Section> section = sectionRepo.findBySectionCode(announcementRequestDTO.getSectionCode());
    if (section.isEmpty()) {
      throw new NotFoundException(Msg.SECTION_NOT_FOUND);
    }
    Announcement announcementUpdate = new Announcement(announcementRequestDTO);
    repo.save(announcementUpdate);
    return new AnnouncementUpdateRequestDTO(announcementUpdate);
  }

  @Override
  public AnnouncementPostRequestDTO save(AnnouncementPostRequestDTO announcementRequestDTO) {
    Announcement announcementUpdate = new Announcement(announcementRequestDTO);
    Optional<Section> section = sectionRepo.findBySectionCode(announcementRequestDTO.getSectionCode());
    Optional<User> user = userRepo.findById(announcementRequestDTO.getSellerId());
    if (user.isEmpty()) {
      throw new NotFoundException(Msg.SELLER_NOT_FOUND);
    }
    if (!user.get().getRole().equals("seller")){
      throw new UnauthorizedException(Msg.USER_NOT_AUTHORIZED);
    }
    if (section.isEmpty()) {
      throw new NotFoundException(Msg.SECTION_NOT_FOUND);
    }
    repo.save(announcementUpdate);
    return new AnnouncementPostRequestDTO(announcementUpdate);
  }
}
