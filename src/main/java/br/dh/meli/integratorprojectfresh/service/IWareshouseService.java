package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;

public interface IWareshouseService {

    WarehouseProductQuantityGetResponseDTO getWarehouseProductsQuantityListByAnnoucementId (Long announcementId);

}
