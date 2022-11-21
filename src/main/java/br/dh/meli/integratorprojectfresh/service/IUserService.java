package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO save(UserDTO user);
    UserDTO update(UserDTO user, Long id);
    List<UserDTO> findByRole(String role);

    List<UserDTO> getAll();
    void delete(Long id);
}
