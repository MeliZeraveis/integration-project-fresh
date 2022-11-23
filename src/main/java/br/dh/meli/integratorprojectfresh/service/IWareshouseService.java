package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;

/**
 * The interface Wareshouse service.
 */
public interface IWareshouseService {

    /**
     * Gets warehouse products quantity list by annoucement id.
     *
     * @param announcementId the announcement id
     * @return the warehouse products quantity list by annoucement id
     */
    WarehouseProductQuantityGetResponseDTO getWarehouseProductsQuantityListByAnnoucementId (Long announcementId);

}
