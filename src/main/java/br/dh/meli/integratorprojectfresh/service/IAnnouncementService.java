package br.dh.meli.integratorprojectfresh.service;

//import br.dh.meli.integratorprojectfresh.dto.request.AnnouncementDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementListResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.AnnouncementGetResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IAnnouncementService {
    List<AnnouncementListResponseDTO> getAllAnnouncements();
    List<AnnouncementListResponseDTO> getAnnouncementsByCategory(String category);
    AnnouncementGetResponseDTO getAnnouncementByAnnouncementId(Long id);
    AnnouncementGetResponseDTO findAnnouncementByBatchStockNumber(Long id, Character sortBy);
    List<AnnouncementListResponseDTO> findAnnouncementByQueryString(String queryString);
    List<AnnouncementListResponseDTO> findAnnouncementByPrice(BigDecimal min, BigDecimal max);

}
