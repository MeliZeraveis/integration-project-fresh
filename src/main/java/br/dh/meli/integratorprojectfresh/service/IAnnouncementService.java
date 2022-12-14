package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;

import java.util.List;

public interface IAnnouncementService {
    List<AnnouncementListResponseDTO> getAllAnnouncements();
    List<AnnouncementListResponseDTO> getAnnouncementsByCategory(String category);
    AnnouncementGetResponseDTO getAnnouncementByAnnouncementId(Long id);
    AnnouncementGetResponseDTO findAnnouncementByBatchStockNumber(Long id, Character sortBy);

}
