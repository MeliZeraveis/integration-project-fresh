package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockAnnouncementDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Announcement GET response DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementGetResponseDTO {
  private Long announcementId;
  private List<BatchStockAnnouncementDTO> batchStock;

  /**
   * Instantiates a new Announcement GET response DTO.
   * @param announcement the announcement
   */
  public AnnouncementGetResponseDTO(Announcement announcement) {
    this.announcementId = announcement.getAnnouncementId();
    this.batchStock = announcement.getBatchStock().stream()
            .map(BatchStockAnnouncementDTO::new)
            .collect(Collectors.toList());
  }

  /**
   * Instantiates a new Announcement get response DTO, sorting by L: batch number, P: product quantity, or V: due date.
   * @param announcement the announcement
   * @param sortBy       the criteria to sort the batches by
   */
  public AnnouncementGetResponseDTO(Announcement announcement, Character sortBy) {
    Map<Character, Comparator<BatchStock>> comparators = Map.of(
            'L', Comparator.comparing(BatchStock::getBatchNumber),
            'Q', Comparator.comparingInt(BatchStock::getProductQuantity),
            'V', Comparator.comparing(BatchStock::getDueDate)
    );
    this.announcementId = announcement.getAnnouncementId();
    this.batchStock = announcement.getBatchStock().stream()
            .sorted(comparators.get(sortBy))
            .map(BatchStockAnnouncementDTO::new)
            .collect(Collectors.toList());
  }
}
