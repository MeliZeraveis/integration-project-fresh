package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;

import java.util.List;

/**
 * The interface Announcement service.
 */
public interface IAnnouncementService {
    /**
     * Gets all announcements.
     *
     * @return the all announcements
     */
    List<AnnouncementListResponseDTO> getAllAnnouncements();

    /**
     * Gets announcements by category.
     *
     * @param category the category
     * @return the announcements by category
     */
    List<AnnouncementListResponseDTO> getAnnouncementsByCategory(String category);

    /**
     * Gets announcement by announcement id.
     *
     * @param id the id
     * @return the announcement by announcement id
     */
    AnnouncementGetResponseDTO getAnnouncementByAnnouncementId(Long id);

    /**
     * Find announcement by batch stock number announcement get response dto.
     *
     * @param id     the id
     * @param sortBy the sort by
     * @return the announcement get response dto
     */
    AnnouncementGetResponseDTO findAnnouncementByBatchStockNumber(Long id, Character sortBy);

}
