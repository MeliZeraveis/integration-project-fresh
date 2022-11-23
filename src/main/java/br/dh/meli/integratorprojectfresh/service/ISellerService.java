package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.SalesSallerListDTO;
import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;

import java.time.LocalDate;

public interface ISellerService {

    SellerDTO getSeller(Long id);
    SalesSallerListDTO getAllSallesSales(Long id);

    SalesSallerListDTO getSalesByDate(Long id, LocalDate date1, LocalDate date2);
}
