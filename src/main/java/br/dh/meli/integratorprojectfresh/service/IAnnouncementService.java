package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;

import java.util.List;
import java.util.Optional;

/**
 * The interface Announcement service.
 */
public interface IAnnouncementService {
    /**
     * Gets announcement by announcement id.
     *
     * @param id the id
     * @return the announcement by announcement id
     */
    AnnoucementGetResponseDTO getAnnouncementByAnnouncementId(Long id);

    /**
     * Find announcement by batch stock number annoucement get response dto.
     *
     * @param id    the id
     * @param letra the letra
     * @return the annoucement get response dto
     */
    AnnoucementGetResponseDTO  findAnnouncementByBatchStockNumber(Long id, String letra);

}
