package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Announcement controller.
 */
@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class AnnouncementController {
  @Autowired
  private IAnnouncementService service;

  /**
   * getAllAnnouncements
   * Gets all announcements.
   * @return the all announcements
   */
  @GetMapping
  public ResponseEntity<List<AnnouncementGetResponseDTO>> getAllAnnouncements() {
    return new ResponseEntity<>(service.getAllAnnouncements(), HttpStatus.OK);
  }

  /**
   * getAnnouncementsByCategory
   * Gets announcements by category.
   * @param category the category
   * @return the announcements by category
   */
  @GetMapping(Routes.PRODUCT_LIST)
  public ResponseEntity<List<AnnouncementGetResponseDTO>> getAnnouncementsByCategory(@RequestParam String category) {
    return new ResponseEntity<>(service.getAnnouncementsByCategory(category), HttpStatus.OK);
  }

  /**
   * getAnnouncementByAnnouncementId
   * Get an announcement by its id.
   * @param id the id
   * @return the announcement by announcement id
   */
  @GetMapping(Routes.PRODUCT_LIST)
  ResponseEntity<AnnouncementGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long id) {
    AnnouncementGetResponseDTO announcement = service.getAnnouncementByAnnouncementId(id);
    return new ResponseEntity<>(announcement, HttpStatus.OK);
  }

  /**
   * findAnnouncementByBatchStockNumber
   * Get all batches where an announcement is present, ordering by filter.
   * @param id    the announcement id
   * @param sortBy the criteria to order the batches by (L: by batch, Q: by quantity, V: by due date)
   * @return the response entity
   */
  @GetMapping(Routes.PRODUCT_LIST)
  ResponseEntity<AnnouncementGetResponseDTO> findAnnouncementByBatchStockNumber(@RequestParam Long id, @RequestParam Character sortBy) {
    AnnouncementGetResponseDTO announcement= service.findAnnouncementByBatchStockNumber(id, sortBy);
    return new ResponseEntity<>(announcement, HttpStatus.OK);
  }
}
