package br.dh.meli.integratorprojectfresh.service;


import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityListByAnnoucementIdGetResponseDTO;

public interface IWareshouseService {

    WarehouseProductQuantityListByAnnoucementIdGetResponseDTO getWarehouseProductsQuantityListByAnnoucementId (Long announcementId);

}
