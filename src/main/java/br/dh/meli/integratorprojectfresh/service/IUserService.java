package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;

import java.util.List;

/**
 * The interface User service.
 */
public interface IUserService {
    /**
     * Save user dto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDTO save(UserDTO user);

    /**
     * Update user dto.
     *
     * @param user the user
     * @param id   the id
     * @return the user dto
     */
    UserDTO update(UserDTO user, Long id);

    /**
     * Find by role list.
     *
     * @param role the role
     * @return the list
     */
    List<UserDTO> findByRole(String role);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<UserDTO> getAll();

}
