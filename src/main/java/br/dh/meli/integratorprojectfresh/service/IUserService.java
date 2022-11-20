package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.SellerDTO;

public interface IUserService {
    SellerDTO getAllUserSales(Long id);
}
