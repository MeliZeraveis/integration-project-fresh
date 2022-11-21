package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;

import java.time.LocalDate;

public interface IUserService {
    SellerDTO getAllUserSales(Long id);

    SellerDTO getSalesByDate(Long id, LocalDate date1, LocalDate date2);
}
